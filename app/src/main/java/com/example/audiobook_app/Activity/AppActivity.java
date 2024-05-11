package com.example.audiobook_app.Activity;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.audiobook_app.Domain.AppDatabase;

public class AppActivity extends Application {
    static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my_app_db")
                .allowMainThreadQueries().build();
    }

    public static AppDatabase getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}

