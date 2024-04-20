package com.example.audiobook_app.Domain;

public class Chapter {

    private int number;
    private String title;
    private String audioAddress;

    public Chapter(int number, String title, String audioAddress) {
        this.number = number;
        this.title = title;
        this.audioAddress = audioAddress;
    }


    public String getTitle() {
        return title;
    }

    public String getAudioAddress() {
        return audioAddress;
    }

    public int getNumber() {
        return number;
    }
}
