package com.example.audiobook_app.Domain;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, Chapter.class}, version =2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDAO bookDAO();
    public abstract ChapterDAO chapterDAO();
}