package com.example.audiobook_app.Domain;

import android.content.Context;
import android.content.res.Resources;
import android.util.JsonReader;

import com.example.audiobook_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BookGenerator {

    List<Book> books;

    public List<Book> getBooks(Context context) {
        return generateBooksFromJSON(context);
    }

    //TODO Read books from a json file and return a list of books
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
