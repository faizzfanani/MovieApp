package com.kontrakanelite.movieapp.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.kontrakanelite.movieapp.R;
import com.kontrakanelite.movieapp.database.MovieHelper;
import com.kontrakanelite.movieapp.database.TvShowHelper;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.widget.FavoriteWidget;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> bitmaps = new ArrayList<>();
    private final Context mContext;
    private MovieHelper movieHelper;
    private TvShowHelper tvHelper;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
        movieHelper = new MovieHelper(context);
        tvHelper = new TvShowHelper(context);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        bitmaps.clear();
        movieHelper.open();
        tvHelper.open();
        for (MovieModel movie: movieHelper.getAllData()){

            try {
                URL url = new URL(movie.getPosterPath());
                bitmaps.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                Log.i("teswidget",movie.getPosterPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (MovieModel tvShow: tvHelper.getAllData()){

            try {
                URL url = new URL(tvShow.getPosterPath());
                bitmaps.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                Log.i("teswidget",tvShow.getPosterPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        movieHelper.close();
        tvHelper.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, bitmaps.get(position));
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
