package com.mwano.lauren.popular_movies.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.mwano.lauren.popular_movies.adapter.FavouriteAdapter;


public class FavouritesCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private FavouriteAdapter mFavouriteAdapter;
    public static final int FAVOURITES_LOADER = 40;

    // Constructor
    public FavouritesCursorLoader(Context context, FavouriteAdapter favouriteAdapter) {
        mContext = context;
        mFavouriteAdapter = favouriteAdapter;
    }

    /** TODO code based Sunshine */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch(loaderId) {
            case FAVOURITES_LOADER:
            // Define a projection that specifies the columns from the table
                String[] projection = {
                        FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID,
                        FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE,
                        FavouritesContract.FavouritesEntry.COLUMN_RATING,
                        FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER,
                        FavouritesContract.FavouritesEntry.COLUMN_MOVIE_BACKDROP,
                        FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE,
                        FavouritesContract.FavouritesEntry.COLUMN_MOVIE_SYNOPSIS
                };
                return new CursorLoader(mContext,
                        FavouritesContract.FavouritesEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader not implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavouriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavouriteAdapter.swapCursor(null);
    }
}
