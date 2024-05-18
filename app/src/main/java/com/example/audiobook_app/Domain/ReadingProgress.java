package com.example.audiobook_app.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ReadingProgress {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "book_id")
    public int bookId;

    @ColumnInfo(name = "lastReadChapter_id")
    public int lastReadChapterId;

    @Ignore
    public List<ChapterProgress> chapterProgresses;

    public ReadingProgress() {
    }


    public ReadingProgress(int id, int bookId, int lastReadChapterId, List<ChapterProgress> chapterProgresses) {
        this.id = id;
        this.bookId = bookId;
        this.lastReadChapterId = lastReadChapterId;
        this.chapterProgresses = chapterProgresses;
    }



}


