package com.example.audiobook_app.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Chapter implements Parcelable{

    @NonNull
    @ColumnInfo(name = "number")
    private String number;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "audioAddress")
    private String audioAddress;

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



    protected Chapter(Parcel in) {
        number = in.readString();
        title = in.readString();
        audioAddress = in.readString();
    }

    public static final Parcelable.Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(title);
        dest.writeString(audioAddress);
    }
}
