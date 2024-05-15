package com.example.audiobook_app.Domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class BookWithChapters {

    @Embedded
    public Book user;
    @Relation(
            parentColumn = "id",
            entityColumn = "book_id"
    )
    public List<Chapter> chapters;
}
