package com.kontrakanelite.movieapp.activity;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.Objects;

public class DetailFilmActivity extends AppCompatActivity {
    TextView title, description, releaseDate;
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
        poster = findViewById(R.id.detail_poster);
        scrollView = findViewById(R.id.detail_scrollview);

        MovieModel movie = getIntent().getParcelableExtra(MOVIE);
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        releaseDate.setText(movie.getDate());
        //poster.setImageResource(movie.getImage());

        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageView actionBack = view.findViewById(R.id.btn_back);

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

