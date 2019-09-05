package com.kontrakanelite.movieapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.adapter.MovieFavoriteAdapter;
import com.kontrakanelite.movieapp.database.MovieHelper;
import com.kontrakanelite.movieapp.model.MovieModel;

import java.util.ArrayList;

public class FavoriteMovieActivity extends AppCompatActivity {
    MovieHelper movieHelper;
    MovieFavoriteAdapter adapter;
    ArrayList<MovieModel>movieModel;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        movieHelper = new MovieHelper(getApplicationContext());
        adapter = new MovieFavoriteAdapter(getApplicationContext());
        recyclerView = findViewById(R.id.movie_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        getAllData();
    }
    private void getAllData() {
        movieHelper.open();
        movieModel = movieHelper.getAllData();
        movieHelper.close();
        adapter.addItem(movieModel);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
