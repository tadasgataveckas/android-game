package com.example.audiobook_app.Domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity
public class ReadingProgress {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int bookId;
    public int lastReadChapterId;
    public int userId;

    @Relation(parentColumn = "id", entityColumn = "readingProgressId")
    public List<ChapterProgress> chapterProgresses;
}


