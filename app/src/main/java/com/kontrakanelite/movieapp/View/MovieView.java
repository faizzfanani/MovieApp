package com.kontrakanelite.movieapp.View;

import android.content.Context;
import com.kontrakanelite.movieapp.Model.MovieModel;

public interface MovieView {
    void showMovie(MovieModel movieModel, int i);
    void prepare(Context context);
    void addItem();
}
