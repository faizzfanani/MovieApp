package com.kontrakanelite.movieapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.database.MovieHelper;
import com.kontrakanelite.movieapp.model.MovieModel;

import java.util.ArrayList;

public class FavoriteMovieActivity extends AppCompatActivity {
    MovieHelper movieHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        movieHelper = new MovieHelper(getApplicationContext());

        getAllData();
    }
    private void getAllData() {
        movieHelper.open();
        ArrayList<MovieModel> movie = movieHelper.getAllData();
        movieHelper.close();
        //nasabahAdapter.addItem(movie);
        //recyclerView.setAdapter(nasabahAdapter);
    }
}
