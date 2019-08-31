package com.kontrakanelite.movieapp.database;

import android.provider.BaseColumns;
public class DatabaseContract {
    static String TABLE_MOVIE = "table_movie";
    static String TABLE_TV_SHOW = "table_tvshow";

    static final class MovieColumns implements BaseColumns {

        static String MOVIE_ID = "id";
        static String MOVIE_TITLE = "title";
        static String MOVIE_DESCRIPTION = "description";
        static String MOVIE_VOTE = "vote";
        static String MOVIE_DATE = "date";
        static String MOVIE_POSTER = "poster";
    }

    static final class TvShowColumns implements BaseColumns {

        static String TV_ID = "id";
        static String TV_TITLE = "title";
        static String TV_DESCRIPTION = "description";
        static String TV_VOTE = "vote";
        static String TV_DATE = "date";
        static String TV_POSTER = "poster";
    }
}
