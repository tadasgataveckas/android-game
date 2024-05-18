package com.example.audiobook_app.Domain;

import java.util.concurrent.TimeUnit;

public class TimeFormatter {

    public TimeFormatter() {
    }

    public static String formatTime(long time) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) % 60);
    }
}
