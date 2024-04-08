package com.example.audiobook_app.Domain;

public class Chapter {

    private String title;
    private String audioAddress;

    public Chapter(String title, String audioAddress) {
        this.title = title;
        this.audioAddress = audioAddress;
    }

    public String getTitle() {
        return title;
    }

    public String getAudioAddress() {
        return audioAddress;
    }
}
