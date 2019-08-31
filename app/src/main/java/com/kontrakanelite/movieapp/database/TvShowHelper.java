package com.kontrakanelite.movieapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kontrakanelite.movieapp.model.MovieModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TABLE_TV_SHOW;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_ID;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_TITLE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_DESCRIPTION;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_VOTE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_DATE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_POSTER;

public class TvShowHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public TvShowHelper(Context context) {
        this.context = context;
    }

    public TvShowHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<MovieModel> getAllData() {
        Cursor cursor = database.query(TABLE_TV_SHOW, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<MovieModel> arrayList = new ArrayList<>();
        MovieModel movieModel;
        if (cursor.getCount() > 0) {
            do {
                movieModel = new MovieModel();
                movieModel.setID(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(TV_ID)));
                movieModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TV_TITLE)));
                movieModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TV_DESCRIPTION)));
                movieModel.setVote(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE)));
                movieModel.setDate(cursor.getString(cursor.getColumnIndexOrThrow(TV_DATE)));
                movieModel.setImage(cursor.getString(cursor.getColumnIndexOrThrow(TV_POSTER)));

                arrayList.add(movieModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieModel movieModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TV_ID, movieModel.getId());
        initialValues.put(TV_TITLE, movieModel.getTitle());
        initialValues.put(TV_DESCRIPTION, movieModel.getDescription());
        initialValues.put(TV_VOTE, movieModel.getVote());
        initialValues.put(TV_DATE, movieModel.getDate());
        initialValues.put(TV_POSTER, movieModel.getImage());

        return database.insert(TABLE_TV_SHOW, null, initialValues);
    }

    public int update(MovieModel movieModel) {
        ContentValues args = new ContentValues();
        args.put(TV_ID, movieModel.getId());
        args.put(TV_TITLE, movieModel.getTitle());
        args.put(TV_DESCRIPTION, movieModel.getDescription());
        args.put(TV_VOTE, movieModel.getVote());
        args.put(TV_DATE, movieModel.getDate());
        args.put(TV_POSTER, movieModel.getImage());

        return database.update(TABLE_TV_SHOW, args, _ID + "= '" + movieModel.getID() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_TV_SHOW, _ID + " = '" + id + "'", null);
    }
}
