package com.example.audiobook_app.Domain;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ReadingProgressDAO {
    @Insert
    long insert(ReadingProgress readingProgress);

    @Insert
    void insertAll(List<ReadingProgress> ReadingProgresses);

    @Query("Delete from ReadingProgress")
    void deleteAll();

    @Transaction
    @Query("Select * from ReadingProgress Order by id ASC")
    List<ReadingProgress> getAllHistory();

//    @Transaction
//    @Query("SELECT * FROM ReadingProgress WHERE book_id = :book_id")
//    public ReadingProgress getReadingProgress(int book_id);

    @Transaction
    @Query("SELECT * FROM ReadingProgress WHERE id = :book_id")
    public ReadingProgressWithChapters getReadingProgress(int book_id);
}
