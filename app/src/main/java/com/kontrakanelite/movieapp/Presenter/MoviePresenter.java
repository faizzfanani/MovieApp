package com.kontrakanelite.movieapp.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.kontrakanelite.movieapp.Activity.DetailFilmActivity;
import com.kontrakanelite.movieapp.Activity.ListFilmActivity;
import com.kontrakanelite.movieapp.Adapter.MovieAdapter;
import com.kontrakanelite.movieapp.Model.MovieModel;
import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.View.MovieView;

import java.util.ArrayList;

public class MoviePresenter extends AppCompatActivity implements MovieView{
    private MovieView movieView;
    private ArrayList<MovieModel>movies;
    private MovieAdapter adapter;

    private String[]dataTitle;
    private String[]dataDescription;
    private String[]dataReleaseDate;
    private TypedArray dataPhoto;

    public MoviePresenter(MovieView movieView) {
        this.movieView = movieView;
    }

    @Override
    public void showMovie(MovieModel movieModel, int i) {
        movieModel.setTitle(dataTitle[i]);
        movieModel.setDescription(dataDescription[i]);
        movieModel.setDate(dataReleaseDate[i]);
        movieModel.setImage(dataPhoto.getResourceId(i, 1));
        Intent moveWithObjectIntent = new Intent(getApplicationContext(), DetailFilmActivity.class);
        moveWithObjectIntent.putExtra(DetailFilmActivity.MOVIE, movieModel);
        startActivity(moveWithObjectIntent);
    }

    @Override
    public void prepare(Context context) {
        dataTitle = getResources().getStringArray(R.array.data_title);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataReleaseDate = getResources().getStringArray(R.array.data_release_date);
        dataPhoto = getResources().obtainTypedArray(R.array.data_poster);
    }

    @Override
    public void addItem() {
        movies = new ArrayList<>();
        adapter = new MovieAdapter(getApplicationContext());
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
}
