package com.kontrakanelite.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.database.MovieHelper;
import com.kontrakanelite.movieapp.model.MovieModel;

import java.util.ArrayList;
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<MovieModel> movie;
    private Context context;
    private MovieHelper movieHelper;


    public FavoriteAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        movieHelper = new MovieHelper(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_list, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        final String id = movie.get(position).getId();
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

//        holder.btnupdate.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                nasabah.get(position).setName(holder.editNama.getText().toString());
//                nasabah.get(position).setNoRek(holder.editNoRek.getText().toString());
//                nasabah.get(position).setTabungan(Integer.valueOf(holder.editTabungan.getText().toString()));
//
//                movieHelper.open();
//                movieHelper.update(movie.get(position));
//                movieHelper.close();
//                Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
//                notifyDataSetChanged();
//            }
//        });
//        holder.btndelete.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                deleteitem(movie.get(position).getId());
//                movie.remove(position);
//                notifyDataSetChanged();
//
//            }
//        });
    }

    private void deleteitem(int id) {

        movieHelper.open();
        movieHelper.delete(id);
        movieHelper.close();

        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movie.size();
    }

    public void addItem(ArrayList<MovieModel> mData) {
        this.movie = mData;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView title;
        private TextView description;
        private TextView releaseDate;
        private TextView vote;
        private ImageView poster;

        public CustomViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            releaseDate = itemView.findViewById(R.id.tv_releaseDate);
            vote = itemView.findViewById(R.id.tv_vote);
            poster = itemView.findViewById(R.id.iv_poster);
        }

    }
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    nasabahHelper.open();
//                    nasabah = nasabahHelper.getAllData();
//                    nasabahHelper.close();
//                } else {
//                    ArrayList<NasabahModel> filteredList = new ArrayList<>();
//                    nasabahHelper.open();
//                    for (NasabahModel row : nasabahHelper.getAllData()) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getNoRek().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    nasabah = filteredList;
//                    nasabahHelper.close();
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = nasabah;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                nasabah = (ArrayList<NasabahModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
}
