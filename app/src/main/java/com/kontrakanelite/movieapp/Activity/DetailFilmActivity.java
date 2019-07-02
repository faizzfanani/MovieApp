package com.kontrakanelite.movieapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kontrakanelite.movieapp.Model.MovieModel;
import com.kontrakanelite.movieapp.R;

public class DetailFilmActivity extends AppCompatActivity {
    TextView title, description, releaseDate;
    ImageView poster;
    public static final String MOVIE = "pilem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        title = findViewById(R.id.detail_title);
        description = findViewById(R.id.detail_description);
        releaseDate = findViewById(R.id.detail_release_date);
        poster = findViewById(R.id.detail_poster);

        MovieModel movie = getIntent().getParcelableExtra(MOVIE);
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        releaseDate.setText(movie.getDate());
        poster.setImageResource(movie.getImage());

    }
}

