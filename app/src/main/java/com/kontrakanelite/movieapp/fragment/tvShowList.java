package com.kontrakanelite.movieapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kontrakanelite.movieapp.ItemClickSupport;
import com.kontrakanelite.movieapp.activity.DetailFilmActivity;
import com.kontrakanelite.movieapp.adapter.ListAdapter;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class tvShowList extends Fragment {
    private static final String URL_DATA = "https://api.themoviedb.org/3/discover/tv?api_key=bda489bfab0d87f4b3c4af88e206e0a4&language=en-US";
    String[] dataId, dataTitle, dataDescription, dataReleaseDate, dataVote, dataPhoto;
    RecyclerView recyclerView;

    //private ListAdapter movieAdapter;
    private ArrayList<MovieModel> movieModels;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv_show_list, container, false);

        recyclerView = rootView.findViewById(R.id.rv_tvshows);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieModels = new ArrayList<>();
        //movieAdapter = new ListAdapter(Objects.requireNonNull(getContext()), movieModels);

        loadShow();

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
                Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);
                moveWithObjectIntent.putExtra(DetailFilmActivity.MOVIE, movie);
                startActivity(moveWithObjectIntent);
            }
        });

        return rootView;
    }
    private void loadShow() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data..");
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
                        dataTitle[i] = movie.getString("name");
                        dataDescription[i] = movie.getString("overview");
                        dataVote[i] = movie.getString("vote_average");
                        dataReleaseDate[i] = movie.getString("first_air_date");
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

                    ListAdapter adapter = new ListAdapter(Objects.requireNonNull(getContext()), movieModels);
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
