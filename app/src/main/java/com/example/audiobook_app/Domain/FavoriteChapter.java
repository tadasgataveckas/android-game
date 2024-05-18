package com.example.audiobook_app.Domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteChapter {

    //TODO siusti Timestampa, dabartini chapter, chpateriai booko list, currentTrack
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "chapter_id")
    public int chapterId;
    @ColumnInfo(name = "timestamp")
    public int timestamp;


    public FavoriteChapter(int chapterId, int timestamp) {
        this.chapterId = chapterId;
        this.timestamp = timestamp;
    }
}
