package com.example.audiobook_app.Domain;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChapterProgressDAO {

    @Insert
    long insert(ChapterProgress chapterProgress);

    @Insert
    void insertAll(List<ChapterProgress> ChapterProgresses);

    @Query("Delete from ChapterProgress")
    void deleteAll();

    @Query("Select * from ChapterProgress Order by id ASC")
    List<ChapterProgress> getAllChapterHistory();

    @Query("UPDATE ChapterProgress SET lastReadTimestamp = :newTimestamp WHERE id = :chapterProgressesID")
    void update(int chapterProgressesID, int newTimestamp);

}
