package com.kontrakanelite.movieapp.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TABLE_MOVIE;
import static com.kontrakanelite.movieapp.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TABLE_TV_SHOW;
import static com.kontrakanelite.movieapp.database.DatabaseContract.TvShowColumns.TV_ID;

import com.bumptech.glide.Glide;
import com.kontrakanelite.movieapp.database.DatabaseHelper;
import com.kontrakanelite.movieapp.database.MovieHelper;
import com.kontrakanelite.movieapp.database.TvShowHelper;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

public class DetailFilmActivity extends AppCompatActivity {
    String id, posterSource, type, posterPath;
    TextView title, description, vote, releaseDate;
    ImageView poster;
    ScrollView scrollView;
    FloatingActionButton btnFavorite;
    public static final String MOVIE = "pilem";
    MovieHelper movieHelper;
    TvShowHelper tvShowHelper;
    SQLiteDatabase database;
    private DatabaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        movieHelper = new MovieHelper(getApplicationContext());
        tvShowHelper = new TvShowHelper(getApplicationContext());
        dataBaseHelper = new DatabaseHelper(getApplicationContext());

        title = findViewById(R.id.detail_title);
        description = findViewById(R.id.detail_description);
        releaseDate = findViewById(R.id.detail_release_date);
        vote = findViewById(R.id.detail_vote);
        poster = findViewById(R.id.detail_poster);
        scrollView = findViewById(R.id.detail_scrollview);
        btnFavorite = findViewById(R.id.detail_favorite);

        MovieModel movie = getIntent().getParcelableExtra(MOVIE);
        id = movie.getId();
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        vote.setText(movie.getVote());
        releaseDate.setText(movie.getDate());
        posterSource = movie.getImage();
        posterPath = movie.getPosterPath();
        Glide.with(getApplicationContext()).load(posterSource).override(780,780).into(poster);
        type = getIntent().getStringExtra("type");

        if(type.equals("movie"))
            movieExist(id);
        if(type.equals("tvshow"))
            tvExist(id);
        ImageView actionBack = findViewById(R.id.detail_back);

        actionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieExist(id) || tvExist(id)){
                    Toast.makeText(getApplicationContext(), R.string.data_already_exist, Toast.LENGTH_SHORT).show();
                }
                else if(type.equals("movie")){
                    if(!movieExist(id)){
                        favoriteMovie();
                        btnFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_fill));}
                }else if(type.equals("tvshow")){
                    if (!tvExist(id)){
                        favoriteTvShow();
                        btnFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_fill));}
                }
            }
        });
    }
    private void favoriteMovie() {
        movieHelper.open();
        MovieModel movie = new MovieModel(
                id,
                title.getText().toString(),
                description.getText().toString(),
                vote.getText().toString(),
                releaseDate.getText().toString(),
                posterPath);
        movieHelper.insert(movie);
        movieHelper.close();
    }

    private void favoriteTvShow() {
        tvShowHelper.open();
        MovieModel movie = new MovieModel(
                id,
                title.getText().toString(),
                description.getText().toString(),
                vote.getText().toString(),
                releaseDate.getText().toString(),
                posterPath);
        tvShowHelper.insert(movie);
        tvShowHelper.close();
    }
    public boolean movieExist(String id) {
        database = dataBaseHelper.getWritableDatabase();
        Cursor cursor;
        String sql ="SELECT * FROM "+TABLE_MOVIE+" WHERE "+ MOVIE_ID +"="+id;
        cursor= database.rawQuery(sql,null);

        if(cursor.getCount()>0){
            btnFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_fill));
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }
    public boolean tvExist(String id) {
        database = dataBaseHelper.getWritableDatabase();
        Cursor cursor;
        String sql ="SELECT * FROM "+TABLE_TV_SHOW+" WHERE "+ TV_ID +"="+id;
        cursor= database.rawQuery(sql,null);

        if(cursor.getCount()>0){
            btnFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_fill));
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

