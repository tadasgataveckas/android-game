package com.example.audiobook_app.Domain;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Carousel book data
 */
@Entity
public class Book{

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @NonNull
    @ColumnInfo(name = "author")
    private String author;
    @NonNull
    @ColumnInfo(name = "picAddress")
    private String picAddress;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "bookURL")
    private String bookURL;



//    @Relation(parentColumn = "id", entityColumn = "book_id", entity = Chapter.class)
    @Ignore
    private List<Chapter> chapters;


//    public Book(@NonNull String title,
//                @NonNull String author,
//                @NonNull String picAddress) {
//        this.title = title;
//        this.author = author;
//        this.picAddress = picAddress;
//    }
    public Book()
    {
        this.title = "Title";
        this.author = "author";
        this.picAddress = "picAddress";
        this.description = "description";
        this.bookURL = "bookURL";
        this.chapters = new ArrayList<>();
    }

    public Book(@NonNull String title,
                @NonNull String author,
                @NonNull String description,
                @NonNull String picAddress)
    {
        this.title = title;
        this.author = author;
        this.picAddress = picAddress;
        this.description = description;
        this.chapters = new ArrayList<>();
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }

    @NonNull
    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(@NonNull String picAddress) {
        this.picAddress = picAddress;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public ArrayList getChapters() {
        return (ArrayList) chapters;
    }



    public void setChapters(@NonNull List<Chapter> chapters) {
        this.chapters = chapters;
    }


    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getBookURL() {
        return bookURL;
    }

    public void setBookURL(@NonNull String bookURL) {
        this.bookURL = bookURL;
    }

    public void addChapter(Chapter currentChapter) {
        chapters.add(currentChapter);
    }
}
