package com.kontrakanelite.movieapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    private int image;
    private String title;
    private String description;
    private String date;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.image);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
    }

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.image = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
