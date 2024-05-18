package com.example.audiobook_app.Domain;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteChapterDAO {
    @Insert
    long insert(FavoriteChapter favoriteChapter);

    @Insert
    void insertAll(List<FavoriteChapter> favoriteChapters);

    @Query("Delete from FavoriteChapter")
    void deleteAll();

    @Query("Select * from FavoriteChapter Order by id ASC")
    List<FavoriteChapter> getAllFavorites();

    @Query("Select * from FavoriteChapter WHERE chapter_id = :chapterID")
    FavoriteChapter getFavorite(int chapterID);

    @Query("UPDATE FavoriteChapter SET timestamp = :newTimestamp WHERE chapter_id = :chapterID")
    void update(int chapterID, int newTimestamp);

    @Query("Delete from FavoriteChapter where chapter_id = :chapterID")
    void delete(int chapterID);
}
