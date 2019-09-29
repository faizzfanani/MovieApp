package com.kontrakanelite.movieapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.kontrakanelite.movieapp.database.MovieHelper;

import static com.kontrakanelite.movieapp.database.DatabaseContract.AUTHORITY;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TABLE_MOVIE;

public class MovieProvider extends ContentProvider
{
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, NOTE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        movieHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = movieHelper.queryProvider();
                break;
            case NOTE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        movieHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = movieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        //getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        movieHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        //getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        movieHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        //getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return deleted;
    }
}
