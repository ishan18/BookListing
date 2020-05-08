package com.example.android.booklisting;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class BookLoader extends AsyncTaskLoader<ArrayList<Books>> {
    String url;
    public BookLoader(@NonNull Context context,String url) {
        super(context);
        this.url=url;
    }

    @Nullable
    @Override
    public ArrayList<Books> loadInBackground() {
        return QueryUtils.fetchData(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
