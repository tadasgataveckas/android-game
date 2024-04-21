package com.example.audiobook_app.Domain;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChapterDAO{
    @Insert
    void insert(Chapter chapter);

    @Query("Delete from Chapter")
    void deleteAll();

    @Query("Select * from Chapter Order by number ASC")
    List<Chapter> getAllChapters();
}
