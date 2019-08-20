package com.kontrakanelite.movieapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

public class DetailFilmActivity extends AppCompatActivity {
    String id;
    TextView title, description, vote, releaseDate;
    ImageView poster;
    ScrollView scrollView;
    public static final String MOVIE = "pilem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        title = findViewById(R.id.detail_title);
        description = findViewById(R.id.detail_description);
        releaseDate = findViewById(R.id.detail_release_date);
        vote = findViewById(R.id.detail_vote);
        poster = findViewById(R.id.detail_poster);
        scrollView = findViewById(R.id.detail_scrollview);

        MovieModel movie = getIntent().getParcelableExtra(MOVIE);
        id = movie.getId();
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        vote.setText(movie.getVote());
        releaseDate.setText(movie.getDate());
        Glide.with(getApplicationContext()).load(movie.getImage()).override(780,780).into(poster);

        ImageView actionBack = findViewById(R.id.detail_back);

        actionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

