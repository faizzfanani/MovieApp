package com.kontrakanelite.movieapp.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract {
    public static final String AUTHORITY = "com.kontrakanelite.movieapp";
    private static final String SCHEME = "content";
    public static String TABLE_MOVIE = "table_movie";
    public static String TABLE_TV_SHOW = "table_tvshow";

    public static final class MovieColumns implements BaseColumns {

        public static String MOVIE_ID = "id";
        static String MOVIE_TITLE = "title";
        static String MOVIE_DESCRIPTION = "description";
        static String MOVIE_VOTE = "vote";
        static String MOVIE_DATE = "date";
        static String MOVIE_POSTER = "poster";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static final class TvShowColumns implements BaseColumns {

        public static String TV_ID = "id";
        static String TV_TITLE = "title";
        static String TV_DESCRIPTION = "description";
        static String TV_VOTE = "vote";
        static String TV_DATE = "date";
        static String TV_POSTER = "poster";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV_SHOW)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
