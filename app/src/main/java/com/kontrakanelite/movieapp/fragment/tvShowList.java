package com.kontrakanelite.movieapp.fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kontrakanelite.movieapp.ItemClickSupport;
import com.kontrakanelite.movieapp.activity.DetailFilmActivity;
import com.kontrakanelite.movieapp.adapter.ListAdapter;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.ArrayList;

public class tvShowList extends Fragment {
    private String[]dataTitle;
    private String[]dataDescription;
    private String[]dataReleaseDate;
    private TypedArray dataPhoto;

    private ListAdapter adapter;
    private ArrayList<MovieModel> movies;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new ListAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_tv_show_list, container, false);
        RecyclerView recyclerView;
        recyclerView = rootView.findViewById(R.id.rv_tvshows);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        prepare();
        addItem();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                Toast.makeText(getContext(), movies.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                MovieModel movie = new MovieModel();
                movie.setTitle(dataTitle[i]);
                movie.setDescription(dataDescription[i]);
                movie.setDate(dataReleaseDate[i]);
                //movie.setImage(dataPhoto.getResourceId(i, 1));
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
            //movie.setImage(dataPhoto.getResourceId(i, -1));
            movie.setTitle(dataTitle[i]);
            movie.setDescription(dataDescription[i]);
            movie.setDate(dataReleaseDate[i]);
            movies.add(movie);
        }
        adapter.setMovies(movies);
    }

    private void prepare() {
        dataTitle = getResources().getStringArray(R.array.tvshow_title);
        dataDescription = getResources().getStringArray(R.array.tvshow_description);
        dataReleaseDate = getResources().getStringArray(R.array.tvshow_release_date);
        dataPhoto = getResources().obtainTypedArray(R.array.tvshow_poster);
    }
}
