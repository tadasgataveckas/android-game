package com.example.audiobook_app.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chapter{

    @ColumnInfo(name = "book_id")
    private long bookId;

    @PrimaryKey(autoGenerate = true)
    private int chapterId;
    @NonNull
    @ColumnInfo(name = "number")
    private String number;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "audioAddress")
    private String audioAddress;

    public Chapter() {
        this.number = "0";
        this.title = "title";
        this.audioAddress = "audioAddress";
    }

    public Chapter(@NonNull String number, @NonNull String title, @NonNull String audioAddress) {
        this.number = number;
        this.title = title;
        this.audioAddress = audioAddress;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getAudioAddress() {
        return audioAddress;
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setAudioAddress(@NonNull String audioAddress) {
        this.audioAddress = audioAddress;
    }


    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
