package com.kontrakanelite.movieapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.adapter.TvFavoriteAdapter;
import com.kontrakanelite.movieapp.database.TvShowHelper;
import com.kontrakanelite.movieapp.model.MovieModel;

import java.util.ArrayList;

public class TvShowFavoriteActivity extends AppCompatActivity {
    TvShowHelper tvHelper;
    TvFavoriteAdapter adapter;
    ArrayList<MovieModel> movieModel;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_favorite);

        tvHelper = new TvShowHelper(getApplicationContext());
        adapter = new TvFavoriteAdapter(getApplicationContext());
        recyclerView = findViewById(R.id.tv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        
        getAllData();
    }
    private void getAllData() {
        tvHelper.open();
        movieModel = tvHelper.getAllData();
        tvHelper.close();
        adapter.addItem(movieModel);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
