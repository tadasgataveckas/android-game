package com.example.audiobook_app.Domain;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, Chapter.class,
        ChapterProgress.class, FavoriteChapter.class, ReadingProgress.class},
        version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDAO bookDAO();
    public abstract ChapterDAO chapterDAO();

    public abstract ChapterProgressDAO chapterProgressDAO();
    public abstract FavoriteChapterDAO favoriteChapterDAO();
    public abstract ReadingProgressDAO readingProgressDAO();
}