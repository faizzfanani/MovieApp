package com.kontrakanelite.movieapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.model.MovieModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TABLE_MOVIE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_TITLE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_DESCRIPTION;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_VOTE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_DATE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_POSTER;

public class MovieHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private static MovieHelper INSTANCE;

    public MovieHelper(Context context) {
        this.context = context;
    }
    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<MovieModel> getAllData() {
        Cursor cursor = database.query(TABLE_MOVIE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<MovieModel> arrayList = new ArrayList<>();
        MovieModel movieModel;
        if (cursor.getCount() > 0) {
            do {
                movieModel = new MovieModel();
                movieModel.setID(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                movieModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                movieModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCRIPTION)));
                movieModel.setVote(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_VOTE)));
                movieModel.setDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE)));
                movieModel.setImage(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER)));

                arrayList.add(movieModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieModel movieModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MOVIE_ID, movieModel.getId());
        initialValues.put(MOVIE_TITLE, movieModel.getTitle());
        initialValues.put(MOVIE_DESCRIPTION, movieModel.getDescription());
        initialValues.put(MOVIE_VOTE, movieModel.getVote());
        initialValues.put(MOVIE_DATE, movieModel.getDate());
        initialValues.put(MOVIE_POSTER, movieModel.getImage());
        long result = database.insert(TABLE_MOVIE, null, initialValues);
        Toast.makeText(context, R.string.add_favorite_success, Toast.LENGTH_SHORT).show();
        return result;
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(TABLE_MOVIE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_MOVIE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_MOVIE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_MOVIE, _ID + " = ?", new String[]{id});
    }
}
