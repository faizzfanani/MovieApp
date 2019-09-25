package com.kontrakanelite.movieapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.JsonObjectRequest;
import com.kontrakanelite.movieapp.ItemClickSupport;
import com.kontrakanelite.movieapp.activity.DetailFilmActivity;
import com.kontrakanelite.movieapp.activity.FavoriteMovieActivity;
import com.kontrakanelite.movieapp.activity.SearchResultActivity;
import com.kontrakanelite.movieapp.adapter.ListAdapter;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.ArrayList;
import java.util.Objects;

import android.app.ProgressDialog;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class movieList extends Fragment{
    private static final String URL_DATA = "https://api.themoviedb.org/3/discover/movie?api_key=bda489bfab0d87f4b3c4af88e206e0a4&language=en-US";

    private ArrayList<MovieModel> movieModels;
    Context context;
    Activity activity;
    private RecyclerView recyclerView;
    ListAdapter adapter;
    ProgressDialog progressDialog;
    String[] dataId, dataTitle, dataDescription, dataReleaseDate, dataVote, dataPhoto;
    FloatingActionButton btnFavorite;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle state) {

        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        context = getContext();
        activity = getActivity();
        recyclerView = rootView.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieModels = new ArrayList<>();
        btnFavorite = rootView.findViewById(R.id.link_favorite_movie);
        SearchView searchView = rootView.findViewById(R.id.sv_movie);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra("query", query);
                intent.putExtra("type","movie");
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        loadProducts();

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FavoriteMovieActivity.class);
                startActivity(intent);
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                Toast.makeText(getContext(), movieModels.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                MovieModel movie = new MovieModel();
                movie.setId(dataId[i]);
                movie.setTitle(dataTitle[i]);
                movie.setDescription(dataDescription[i]);
                movie.setVote(dataVote[i]);
                movie.setDate(dataReleaseDate[i]);
                movie.setImage(dataPhoto[i]);
                String type = "movie";
                Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);
                moveWithObjectIntent.putExtra(DetailFilmActivity.MOVIE, movie);
                moveWithObjectIntent.putExtra("type", type);
                startActivity(moveWithObjectIntent);
            }
        });
        
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        state.putParcelableArrayList("movieList", movieModels);
        super.onSaveInstanceState(state);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            movieModels = savedInstanceState.getParcelableArrayList("movieList");
            ListAdapter newAdapter = new ListAdapter(Objects.requireNonNull(getContext()), movieModels);
            recyclerView.setAdapter(newAdapter);
            progressDialog.dismiss();
        }
    }

    private void loadProducts() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading_indicator));
        progressDialog.show();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL_DATA, null, new Response.Listener
                <JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = response.optJSONArray("results");
                                dataId = new String[jsonArray.length()];
                                dataTitle = new String[jsonArray.length()];
                                dataDescription = new String[jsonArray.length()];
                                dataVote = new String[jsonArray.length()];
                                dataReleaseDate = new String[jsonArray.length()];
                                dataPhoto = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject movie = jsonArray.optJSONObject(i);
                                    dataId[i] = movie.getString("id");
                                    dataTitle[i] = movie.getString("title");
                                    dataDescription[i] = movie.getString("overview");
                                    dataVote[i] = movie.getString("vote_average");
                                    dataReleaseDate[i] = movie.getString("release_date");
                                    dataPhoto[i] = movie.getString("poster_path");
                                movieModels.add(new MovieModel(
                                        dataId[i],
                                        dataTitle[i],
                                        dataDescription[i],
                                        dataVote[i],
                                        dataReleaseDate[i],
                                        dataPhoto[i]
                                ));
                            }

                            adapter = new ListAdapter(Objects.requireNonNull(getContext()), movieModels);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(objectRequest);
    }
}
