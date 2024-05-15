package com.example.audiobook_app.Domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChapterProgress {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int chapterId;
    public int readingProgressId;
    public boolean isCompleted;
    public int lastReadTimestamp;
}