package com.kontrakanelite.movieapp.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.adapter.ListAdapter;
import com.kontrakanelite.movieapp.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SearchResultActivity extends AppCompatActivity {
    private static final String MOVIE_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key=bda489bfab0d87f4b3c4af88e206e0a4&language=en-US&query=";
    private static final String TVSHOWS_SEARCH = "https://api.themoviedb.org/3/search/tv?api_key=bda489bfab0d87f4b3c4af88e206e0a4&language=en-US&query=";

    private ArrayList<MovieModel> movieModels;

    private RecyclerView recyclerView;
    ListAdapter adapter;
    ProgressDialog progressDialog;
    String[] dataId, dataTitle, dataDescription, dataReleaseDate, dataVote, dataPhoto;
    String type, query;
    ImageView btnBack;
    TextView queryResult;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        query = intent.getStringExtra("query");

        btnBack = findViewById(R.id.search_btn_back);
        queryResult = findViewById(R.id.search_textview);
        recyclerView = findViewById(R.id.rv_search_result);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        movieModels = new ArrayList<>();

        if(type.equals("movie")){
            loadMovie(query);
        }else if(type.equals("tvshow")){
            loadTvShow(query);
        }

        queryResult.setText(getString(R.string.query_result)+" '"+query+"'");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void loadMovie(String query) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_indicator));
        progressDialog.show();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, MOVIE_SEARCH+query, null, new Response.Listener
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

                    adapter = new ListAdapter(Objects.requireNonNull(getApplicationContext()), movieModels);
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

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        requestQueue.add(objectRequest);
    }
    private void loadTvShow(final String query) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_indicator));
        progressDialog.show();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, TVSHOWS_SEARCH+query, null, new Response.Listener
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

                    adapter = new ListAdapter(Objects.requireNonNull(getApplicationContext()), movieModels);
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

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        requestQueue.add(objectRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
