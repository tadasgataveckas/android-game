package com.example.audiobook_app.Domain;

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
}
