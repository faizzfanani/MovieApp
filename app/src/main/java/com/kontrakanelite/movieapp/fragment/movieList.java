package com.kontrakanelite.movieapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kontrakanelite.movieapp.adapter.ListAdapter;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.ArrayList;
import java.util.Objects;

import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class movieList extends Fragment {
    private static final String URL_DATA = "https://api.themoviedb.org/3/discover/movie?api_key=bda489bfab0d87f4b3c4af88e206e0a4&language=en-US";

    private ArrayList<MovieModel> movieModels;
    private RecyclerView recyclerView;
    public ListAdapter movieAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        recyclerView = rootView.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieModels = new ArrayList<>();
        movieAdapter = new ListAdapter(Objects.requireNonNull(getContext()), movieModels);

        loadProducts();
        //addItem();

//        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
//                Toast.makeText(getContext(), movies.get(i).getTitle(), Toast.LENGTH_SHORT).show();
//                MovieModel movie = new MovieModel();
//                movie.setTitle(dataTitle[i]);
//                movie.setDescription(dataDescription[i]);
//                movie.setDate(dataReleaseDate[i]);
//                movie.setImage(dataPhoto.getResourceId(i, 1));
//                Intent moveWithObjectIntent = new Intent(getContext(), DetailFilmActivity.class);
//                moveWithObjectIntent.putExtra(DetailFilmActivity.MOVIE, movie);
//                startActivity(moveWithObjectIntent);
//            }
//        });
        
        return rootView;
    }
    private void loadProducts() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data..");
        progressDialog.show();
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            //converting the string to json array object
                            //JSONObject jsonObject;
                            //jsonObject = new JSONObject(response);
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject movie = array.getJSONObject(i);

                                //adding the product to product list
                                movieModels.add(new MovieModel(
                                        movie.getString("title"),
                                        movie.getString("overview"),
                                        movie.getString("release_date"),
                                        movie.getString("poster_path")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
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

        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(stringRequest);

    }
}
