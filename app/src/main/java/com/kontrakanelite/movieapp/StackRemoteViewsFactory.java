package com.kontrakanelite.movieapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.kontrakanelite.movieapp.activity.MainActivity;
import com.kontrakanelite.movieapp.model.MovieModel;
import com.kontrakanelite.movieapp.widget.FavoriteWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static ArrayList<MovieModel> favorit = new ArrayList<MovieModel>();
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieModel movie = favorit.get(position);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext).asBitmap()
                    .load(movie.getImage())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.imageView,bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return remoteViews;
    }

//    public static void loadFavorite() {
//
//        try {
//            Cursor c = MainActivity.myDatabase.rawQuery("SELECT * FROM favorite.db", null);
//            if (c!= null){
//                //mengambil index kolum
//                int titleIndex = c.getColumnIndex("title");
//                int overviewIndex = c.getColumnIndex("overview");
//                int posterLinkIndex = c.getColumnIndex("posterLink");
//                int releaseDateIndex = c.getColumnIndex("releaseDate");
//                int idIndex = c.getColumnIndex("id");
//                int isMovieIndex = c.getColumnIndex("isMovie");
//
//                c.moveToFirst();
//                do {
//                    String title = c.getString(titleIndex);
//                    String overview = c.getString(overviewIndex);
//                    String posterLink = c.getString(posterLinkIndex);
//                    String releaseDate = c.getString(releaseDateIndex);
//                    long id = c.getDouble(idIndex);
//                    Boolean isMovie;
//                    if (c.getString(isMovieIndex).equals("1")){
//                        isMovie = true;
//                    }else {isMovie = false;}
//                    MovieModel movie = new MovieModel(id, title, overview, posterLink, releaseDate, isMovie);
//                    favorit.add(movie);
//                } while (c.moveToNext());
//            }
//        }
//        catch (Exception E){
//
//        }
//    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
