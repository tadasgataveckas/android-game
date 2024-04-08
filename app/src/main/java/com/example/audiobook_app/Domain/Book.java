package com.example.audiobook_app.Domain;

import java.util.List;

/**
 * Carousel book data
 */
public class Book {
    private String title;
    private String author;
    private String picAddress;

    private List<Chapter> chapters;


    public Book(String title, String author, String picAddress) {
        this.title = title;
        this.author = author;
        this.picAddress = picAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }
}
