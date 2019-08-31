package com.kontrakanelite.movieapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

import static com.kontrakanelite.movieapp.database.DatabaseContract.TABLE_MOVIE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_TITLE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_DESCRIPTION;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_VOTE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_DATE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_POSTER;

import static com.kontrakanelite.movieapp.database.DatabaseContract.TABLE_TV_SHOW;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_ID;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_TITLE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_DESCRIPTION;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_VOTE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_DATE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_POSTER;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static String CREATE_TABLE_MOVIE = "create table " + TABLE_MOVIE +
            " (" + _ID + " integer primary key autoincrement, " +
            MOVIE_ID + " text not null, " +
            MOVIE_TITLE + " text not null, " +
            MOVIE_DESCRIPTION + " text not null, "+
            MOVIE_VOTE + " text not null, " +
            MOVIE_DATE + " text not null, "+
            MOVIE_POSTER + " text not null );";
    public static String CREATE_TABLE_TV_SHOW = "create table " + TABLE_TV_SHOW +
            " (" + _ID + " integer primary key autoincrement, " +
            TV_ID + " text not null, " +
            TV_TITLE + " text not null, " +
            TV_DESCRIPTION + " text not null, "+
            TV_VOTE + " text not null, " +
            TV_DATE + " text not null, "+
            TV_POSTER + " text not null );";

    private static String DATABASE_NAME = "favorite";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_TV_SHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV_SHOW);
        onCreate(db);
    }


}
