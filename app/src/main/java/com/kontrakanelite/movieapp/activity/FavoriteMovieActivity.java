package com.kontrakanelite.movieapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    ImageView btnBack;
    TextView titlebar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        movieHelper = new MovieHelper(getApplicationContext());
        adapter = new MovieFavoriteAdapter(getApplicationContext());
        recyclerView = findViewById(R.id.movie_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        titlebar = findViewById(R.id.actionbar_title);
        titlebar.setText(R.string.favorite_movie);
        btnBack = findViewById(R.id.actionbar_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
