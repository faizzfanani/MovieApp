package com.kontrakanelite.movieapp.Activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kontrakanelite.movieapp.Adapter.MovieAdapter;
import com.kontrakanelite.movieapp.Model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.ArrayList;

public class ListFilmActivity extends AppCompatActivity {
    private String[]dataTitle;
    private String[]dataDescription;
    private String[]dataReleaseDate;
    private TypedArray dataPhoto;

    private MovieAdapter adapter;
    private ArrayList<MovieModel>movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_film);

        adapter = new MovieAdapter(getApplicationContext());
        ListView listView;
        listView = findViewById(R.id.lv_movie_list);
        listView.setAdapter(adapter);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListFilmActivity.this, movies.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                MovieModel movie = new MovieModel();
                movie.setTitle(dataTitle[i]);
                movie.setDescription(dataDescription[i]);
                movie.setDate(dataReleaseDate[i]);
                movie.setImage(dataPhoto.getResourceId(i, 1));
                Intent moveWithObjectIntent = new Intent(ListFilmActivity.this, DetailFilmActivity.class);
                moveWithObjectIntent.putExtra(DetailFilmActivity.MOVIE, movie);
                startActivity(moveWithObjectIntent);
            }
        });
    }

    private void addItem() {
        movies = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            MovieModel movie = new MovieModel();
            movie.setImage(dataPhoto.getResourceId(i, -1));
            movie.setTitle(dataTitle[i]);
            movie.setDescription(dataDescription[i]);
            movie.setDate(dataReleaseDate[i]);
            movies.add(movie);
        }
        adapter.setMovies(movies);
    }

    private void prepare() {
        dataTitle = getResources().getStringArray(R.array.data_title);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataReleaseDate = getResources().getStringArray(R.array.data_release_date);
        dataPhoto = getResources().obtainTypedArray(R.array.data_poster);
    }
}
