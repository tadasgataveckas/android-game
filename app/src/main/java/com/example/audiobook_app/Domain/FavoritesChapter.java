package com.example.audiobook_app.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class FavoritesChapter {

    //TODO siusti Timestampa, dabartini chapter, chpateriai booko list, currentTrack
    int timestamp;
    Chapter currentChapter;
    List<Chapter> chapters;

    int currentTrack;

    public FavoritesChapter(int timestamp, Chapter currentChapter, List<Chapter> chapters, int currentTrack) {
        this.timestamp = timestamp;
        this.currentChapter = currentChapter;
        this.chapters = chapters;
        this.currentTrack = currentTrack;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter currentChapter) {
        this.currentChapter = currentChapter;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(int currentTrack) {
        this.currentTrack = currentTrack;
    }


//    protected Chapter(Parcel in) {
//        timestamp = in.readInt();
//        currentChapter = in.readValue();
//        chapters = in.readArrayList();
//        currentTrack = in.readInt();
//    }
//
//    public static final Parcelable.Creator<Chapter> CREATOR = new Parcelable.Creator<Chapter>() {
//        @Override
//        public Chapter createFromParcel(Parcel in) {
//            return new Chapter(in);
//        }
//
//        @Override
//        public Chapter[] newArray(int size) {
//            return new Chapter[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//
//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeInt(timestamp);
//        dest.writeValue(currentChapter);
//        dest.writeArray(chapters);
//        dest.writeInt(currentTrack);
//    }
}
