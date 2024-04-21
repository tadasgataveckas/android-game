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
    public List<Book> generateBooksFromJSON(Context context) {
        List<Book> books = new ArrayList<>();
        try {
            // Get the resources of the application
            Resources res = context.getResources();

            // Open the books.json file from the raw folder
            InputStream inputStream = res.openRawResource(R.raw.books);

            // Create a JsonReader from the input stream
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            // Parse the JSON file
            JSONArray jsonArray = new JSONArray(reader);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract data for each book
                String title = jsonObject.getString("title");
                String author = jsonObject.getString("author");
                String description = jsonObject.getString("description");
                String coverImageUrl = jsonObject.getString("cover_image_url");

                // Create a new Book object and add it to the list
                Book book = new Book(title, author, description, coverImageUrl);
                book.setChapters(generateChapters(book));
                books.add(book);
            }
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
                String[] parts = filename.split("_");
                if (parts.length >= 3) {
                    String chapterNumber = parts[1];
                    String chapterTitle = parts[2];
                    Chapter chapter = new Chapter(chapterNumber, chapterTitle, filename);
                    chapters.add(chapter);
                }
            }
        }

        return chapters;
    }
}
