package com.example.audiobook_app.Domain;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
/**
 * Carousel book data
 */
@Entity
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;
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
    @ColumnInfo(name = "chapters")
    private List<Chapter> chapters;


    public Book(@NonNull String title,
                @NonNull String author,
                @NonNull String picAddress) {
        this.title = title;
        this.author = author;
        this.picAddress = picAddress;
    }

    public Book(@NonNull String title,
                @NonNull String author,
                @NonNull String description,
                @NonNull String picAddress) {
        this.title = title;
        this.author = author;
        this.picAddress = picAddress;
        this.description = description;
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
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(@NonNull List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
