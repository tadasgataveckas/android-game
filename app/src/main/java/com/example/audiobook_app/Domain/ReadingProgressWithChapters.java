package com.example.audiobook_app.Domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ReadingProgressWithChapters {

    @Embedded
    public ReadingProgress readingProgress;
    @Relation(
            parentColumn = "id",
            entityColumn = "readingProgress_id"
    )
    public List<ChapterProgress> chapters;
}
