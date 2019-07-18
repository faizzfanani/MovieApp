package com.kontrakanelite.movieapp.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kontrakanelite.movieapp.adapter.MovieAdapter;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.ArrayList;

public class movieList extends Fragment {
    private String[]dataTitle;
    private String[]dataDescription;
    private String[]dataReleaseDate;
    private TypedArray dataPhoto;

    private MovieAdapter adapter;
    private ArrayList<MovieModel> movies;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new MovieAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ListView listView;
        listView = rootView.findViewById(R.id.lv_movie);
        listView.setAdapter(adapter);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), movies.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                MovieModel movie = new MovieModel();
                movie.setTitle(dataTitle[i]);
                movie.setDescription(dataDescription[i]);
                movie.setDate(dataReleaseDate[i]);
                movie.setImage(dataPhoto.getResourceId(i, 1));
                Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);
                moveWithObjectIntent.putExtra(DetailFilmActivity.MOVIE, movie);
                startActivity(moveWithObjectIntent);
            }
        });
        return rootView;
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
