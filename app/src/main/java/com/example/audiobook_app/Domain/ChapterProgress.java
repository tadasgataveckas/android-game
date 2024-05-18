package com.example.audiobook_app.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChapterProgress {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "chapter_id")
    public long chapterId;

    @ColumnInfo(name = "readingProgress_id") //parent class id
    public long readingProgressId;

    @ColumnInfo(name = "isCompleted")
    public boolean isCompleted;

    @ColumnInfo(name = "lastReadTimestamp")
    public int lastReadTimestamp;


    public ChapterProgress(long chapterId, long readingProgressId){ //constructor for creating new chapterProgress
        this.chapterId = chapterId;
        this.readingProgressId = readingProgressId;

        this.isCompleted = false;
        this.lastReadTimestamp = 0;
    }
}