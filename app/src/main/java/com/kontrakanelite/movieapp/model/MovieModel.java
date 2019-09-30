package com.kontrakanelite.movieapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.kontrakanelite.movieapp.database.DatabaseContract;

import static com.kontrakanelite.movieapp.database.DatabaseContract.getColumnInt;
import static com.kontrakanelite.movieapp.database.DatabaseContract.getColumnString;

public class MovieModel implements Parcelable {
    private String image;
    private String id;
    private String title;
    private String description;
    private String vote;
    private String date;
    private int ID;

    public MovieModel(String id, String title, String description, String vote, String date, String image) {
        this.image = image;
        this.id = id;
        this.title = title;
        this.description = description;
        this.vote = vote;
        this.date = date;
    }
    public MovieModel(int ID, String id, String title, String description, String vote, String date, String image) {
        this.ID = ID;
        this.image = image;
        this.id = id;
        this.title = title;
        this.description = description;
        this.vote = vote;
        this.date = date;
    }
    MovieModel(Cursor cursor){
        this.ID = getColumnInt(cursor, BaseColumns._ID);
        this.id = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_ID);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_TITLE);
        this.description = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_DESCRIPTION);
        this.date = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_DATE);
        this.vote = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_VOTE);
        this.image = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_POSTER);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return "https://image.tmdb.org/t/p/w780/"+image;
    }

    public String getPosterPath(){return image;}

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public void setImage(String image) {
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
        dest.writeString(this.id);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.vote);
    }

    public MovieModel() {
    }

    private MovieModel(Parcel in) {
        this.id = in.readString();
        this.image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.vote = in.readString();
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
