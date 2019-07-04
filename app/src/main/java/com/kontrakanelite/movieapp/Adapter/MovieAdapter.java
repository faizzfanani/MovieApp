package com.kontrakanelite.movieapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kontrakanelite.movieapp.Model.MovieModel;
import com.kontrakanelite.movieapp.R;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MovieModel>movies;

    public void setMovies(ArrayList<MovieModel> movies) {
        this.movies = movies;
    }

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        MovieModel movie = (MovieModel) getItem(position);
        viewHolder.bind(movie);
        return convertView;
    }
    private class ViewHolder {
        private TextView title;
        private TextView description;
        private TextView releaseDate;
        private ImageView poster;
        ViewHolder(View view) {
            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);
            releaseDate = view.findViewById(R.id.tv_releaseDate);
            poster = view.findViewById(R.id.iv_poster);
        }
        void bind(MovieModel movie) {
            title.setText(movie.getTitle());
            description.setText(movie.getDescription());
            releaseDate.setText(movie.getDate());
            Glide.with(context).load(movie.getImage()).override(500,500).into(poster);
        }
    }
}
