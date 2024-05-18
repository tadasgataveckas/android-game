package com.example.audiobook_app.Domain;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.util.Xml;

import com.example.audiobook_app.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.xmlpull.v1.XmlPullParserException;

public class BookGenerator {

    List<Book> books;

    public List<Book> getBooks(Context context) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Book>> future = executor.submit(() -> generateBooksFromLibra(context));

        try {
            books = future.get();  // This will block until the task is complete
        } catch (InterruptedException | ExecutionException e) {
            Log.e("BookGenerator", "Error getting books from Libra", e);
        }

        executor.shutdown();

        if (books != null) {
            return books;
        } else {
            return generateBooksFromJSON(context);
        }
    }



    private List<Book> generateBooksFromLibra(Context context) {
        List<Book> books = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            Log.d("BookGenerator", "Attempting to establish connection...");
//            URL url = new URL("https://librivox.org/api/feed/audiobooks/?limit=4");
            URL url = new URL("https://librivox.org/api/feed/audiobooks/?id=20469");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try {
                connection.connect();
            } catch (IOException e) {
                Log.e("BookGenerator", "Error establishing connection", e);
                return null;
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                Book currentBook = null;
                String authorFirstName = "";
                String authorLastName = "";
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equals("book")) {
                                currentBook = new Book();
                            } else if (currentBook != null) {
                                if (name.equals("title")) {
                                    currentBook.setTitle(parser.nextText());
                                } else if (name.equals("description")) {
                                    currentBook.setDescription(parser.nextText());
                                } else if (name.equals("url_rss")) {
                                    currentBook.setBookURL(parser.nextText());
                                    currentBook.setPicAddress(fetchImageFromRss(currentBook.getBookURL(), currentBook));
                                } else if (name.equals("first_name")) {
                                    authorFirstName = parser.nextText();
                                } else if (name.equals("last_name")) {
                                    authorLastName = parser.nextText();
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase("author") && currentBook != null) {
                                currentBook.setAuthor(authorFirstName + " " + authorLastName);
                            } else if (name.equalsIgnoreCase("book") && currentBook != null) {
                                books.add(currentBook);
                            }
                    }
                    eventType = parser.next();
                }
            }
        } catch (XmlPullParserException e) {
            Log.e("BookGenerator", "Error parsing XML", e);
        } catch (IOException e) {
            Log.e("BookGenerator", "Error connecting to the server", e);
            return books;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return books;
    }

    private String fetchImageFromRss(String rssUrl, Book currentBook) {
        HttpURLConnection connection = null;
        String imageUrl = "";
        try {
            URL url = new URL(rssUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                Chapter currentChapter = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            Log.d("BookGenerator", "Encountered tag: " + name);
                            if (name.equals("image")) {
                                String href = parser.getAttributeValue(null, "href");
                                if (href != null) {
                                    imageUrl = href;
                                }
                            } else if (name.equals("item")) {
                                currentChapter = new Chapter();
                            } else if (currentChapter != null) {
                                if (name.equals("title")) {
                                    currentChapter.setTitle(parser.nextText());
                                } else if (name.equals("enclosure")) {
                                    currentChapter.setAudioAddress(parser.getAttributeValue(null, "url"));
                                } else if (name.equals("episode")) {
                                    currentChapter.setNumber(parser.nextText());
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase("item") && currentChapter != null) {
                                currentBook.addChapter(currentChapter);
                                currentChapter = null;
                            }
                    }
                    eventType = parser.next();
                }
            } else {
                Log.e("BookGenerator", "HTTP error code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            Log.e("BookGenerator", "Error fetching RSS feed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return imageUrl;
    }



    private List<Book> generateBooksFromJSON(Context context) {

        List<Book> books = new ArrayList<>();
        try {
            // Get the resources of the application
            Resources res = context.getResources();

            // Open the books.json file from the raw folder
            InputStream inputStream = res.openRawResource(R.raw.books);

            // Create a JsonReader from the input stream
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            // Start reading array
            reader.beginArray();

            while (reader.hasNext()) {
                // Start reading object
                reader.beginObject();

                String title = "";
                String author = "";
                String description = "";
                String coverImageUrl = "";

                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("title")) {
                        title = reader.nextString();
                    } else if (name.equals("author")) {
                        author = reader.nextString();
                    } else if (name.equals("description")) {
                        description = reader.nextString();
                    } else if (name.equals("cover_image_url")) {
                        coverImageUrl = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                }

                // End reading object
                reader.endObject();

                // Create a new Book object and add it to the list
                Book book = new Book(title, author, description, coverImageUrl);
                book.setChapters(generateChapters(book));
                books.add(book);
            }

            // End reading array
            reader.endArray();

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    //TODO Read chapters of a book based on the book json data
    public List<Chapter> generateChapters(Book book) {
        List<Chapter> chapters = new ArrayList<>();
        String bookTitle = book.getTitle();

        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            String filename = field.getName();
            if (filename.startsWith(bookTitle)) {
                String noTitle = filename.replaceFirst(bookTitle + "_", "");
                String[] parts = noTitle.split("_");
                if (parts.length >= 2) {
                    String chapterNumber = parts[0];
                    String chapterTitle = parts[1];
                    String noExtension = filename.replaceFirst(".mp3", "");
                    Chapter chapter = new Chapter(chapterNumber, chapterTitle, noExtension);
                    chapters.add(chapter);
                }
            }
        }

        return chapters;
    }
}
