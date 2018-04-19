package com.mwano.lauren.popular_movies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwano.lauren.popular_movies.R;
import com.mwano.lauren.popular_movies.data.FavouritesContract;
import com.mwano.lauren.popular_movies.model.Movie;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    /** TODO cf Sunshine*/
    private Context mContext;
    private final FavouriteAdapterOnClickHandler mFavouriteClickHandler;
    private Cursor mCursor;

    // FavouriteAdapter constructor
    public FavouriteAdapter(Context context, FavouriteAdapterOnClickHandler favouriteClickHandler) {
        mContext = context;
        mFavouriteClickHandler = favouriteClickHandler;

    }

    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForFavouriteItem = R.layout.movie_item;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mItemView = mInflater.inflate(layoutIdForFavouriteItem, parent, false);
        return new FavouriteAdapter.FavouriteViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.FavouriteViewHolder favouriteViewHolder, int position) {
        mCursor.moveToPosition(position);
        // TODO add placeholder
        Picasso mPicasso = Picasso.with(mContext);
        //Debug indicators
        mPicasso.setIndicatorsEnabled(true);
        // Load movie posters
        mPicasso.load(Movie.BASE_POSTER_PATH
                + mCursor.getString(mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER)))
                //.networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(favouriteViewHolder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    /**
     * Create an OnClickHandler interface
     */
    public interface FavouriteAdapterOnClickHandler {
        void onClick(Movie currentFavouriteMovie);
    }

    /**
     * Create a ViewHolder with a click listener
     */
    public class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMovieImageView;


        // MovieViewHolder constructor
        public FavouriteViewHolder(View view) {
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            // Get movie from database
            mFavouriteClickHandler.onClick(queryFavourite());
        }

        // Create a new Movie object from the cursor query (that will be passed as parcelable
        // in the DetailActivity intent)
        private Movie queryFavourite() {
            Movie currentFavouriteMovie = new Movie();
            currentFavouriteMovie.setId(mCursor.getInt(
                    mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID)));
            currentFavouriteMovie.setImage(mCursor.getString(
                    mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER)));
            currentFavouriteMovie.setBackdropImage(mCursor.getString(
                    mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_BACKDROP)));
            currentFavouriteMovie.setOriginalTitle(mCursor.getString(
                    mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE)));
            currentFavouriteMovie.setSynopsis(mCursor.getString(
                    mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_SYNOPSIS)));
            currentFavouriteMovie.setReleaseDate(mCursor.getString(
                    mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE)));
            currentFavouriteMovie.setRating(mCursor.getDouble(
                    mCursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_RATING)));
            return currentFavouriteMovie;
        }
    }
}