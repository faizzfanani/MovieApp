package com.kontrakanelite.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.database.MovieHelper;
import com.kontrakanelite.movieapp.model.MovieModel;

import java.util.ArrayList;
public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<MovieModel> movie;
    private Context context;
    private MovieHelper movieHelper;


    public MovieFavoriteAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        movieHelper = new MovieHelper(context);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_favorite, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final String title = movie.get(position).getTitle();
        final String description = movie.get(position).getDescription();
        final String vote = movie.get(position).getVote();
        final String date = movie.get(position).getDate();
        final String poster = movie.get(position).getImage();

        holder.title.setText(title);
        holder.description.setText(description);
        holder.vote.setText(vote);
        holder.releaseDate.setText(date);
        Glide.with(context).load(poster).override(500,500).into(holder.poster);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteitem(movie.get(position).getID());
                movie.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void deleteitem(int id) {
        movieHelper.open();
        movieHelper.delete(id);
        movieHelper.close();
        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public void addItem(ArrayList<MovieModel> mData) {
        this.movie = mData;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView releaseDate;
        private TextView vote;
        private ImageView poster, btnDelete;

        public CustomViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            releaseDate = itemView.findViewById(R.id.tv_releaseDate);
            vote = itemView.findViewById(R.id.tv_vote);
            poster = itemView.findViewById(R.id.iv_poster);
            btnDelete = itemView.findViewById(R.id.delete_favorite);
        }

    }
}
