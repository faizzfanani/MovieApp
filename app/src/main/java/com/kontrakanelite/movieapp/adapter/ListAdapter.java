package com.kontrakanelite.movieapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieModel>movies;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, ArrayList<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public ListAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }
    public void setMovies(ArrayList<MovieModel> movies) {
        this.movies = movies;
    }
    private ArrayList<MovieModel> getShow() {
        return movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
//        return new ViewHolder(itemRow);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final MovieModel movie = getShow().get(i);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.description.setText(movie.getDescription());
        viewHolder.releaseDate.setText(movie.getDate());
        //Glide.with(context).load(movie.getImage()).override(500,500).into(viewHolder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView description;
        private TextView releaseDate;
        private ImageView poster;

        ViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);
            releaseDate = view.findViewById(R.id.tv_releaseDate);
            poster = view.findViewById(R.id.iv_poster);
        }
    }
}
