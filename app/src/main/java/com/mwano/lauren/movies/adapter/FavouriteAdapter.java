//package com.mwano.lauren.popular_movies.Adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.mwano.lauren.popular_movies.R;
//import com.mwano.lauren.popular_movies.model.Movie;
//
//import java.util.ArrayList;
//
//
//public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
//
//    private final FavouriteAdapterOnClickHandler mFavouriteClickHandler;
//    private Context mContext;
//    private ArrayList<Movie> mMovies;
//
//    // FavouriteAdapter constructor
//    public FavouriteAdapter(Context context, ArrayList<Movie> movies, FavouriteAdapterOnClickHandler favouriteClickHandler) {
//        mContext = context;
//        mMovies = movies;
//        mFavouriteClickHandler = favouriteClickHandler;
//    }
//
//    @Override
//    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        mContext = parent.getContext();
//        int layoutIdForFavouriteItem = R.layout.favourite_item;
//        LayoutInflater mInflater = LayoutInflater.from(mContext);
//        View mItemView = mInflater.inflate(layoutIdForFavouriteItem, parent, false);
//        return new FavouriteAdapter.FavouriteViewHolder(mItemView);
//    }
//
//    @Override
//    public void onBindViewHolder(FavouriteAdapter.FavouriteViewHolder favouriteViewHolder, int position) {
//        mContext = favouriteViewHolder.mFavView.getContext();
//        Movie mCurrentMovie = mMovies.get(position);
//        // setText
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mMovies == null) return 0;
//        return mMovies.size();
//    }
//
//    public void setFavouriteData(ArrayList<Movie> favouriteData) {
//        mMovies = favouriteData;
//        notifyDataSetChanged();
//    }
//
//    /**
//     * Create an OnClickHandler interface
//     */
//    public interface FavouriteAdapterOnClickHandler {
//        void onClick(Movie currentMovie);
//    }
//
//    /**
//     * Create a ViewHolder with a click listener
//     */
//    public class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        public final TextView mFavTextView;
//
//        // MovieViewHolder constructor
//        public FavouriteViewHolder(View view) {
//            super(view);
//            // to change
//            mFavTextView = (TextView) view.findViewById(R.id.movie_poster);
//            view.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            int adapterPosition = getAdapterPosition();
//            Movie currentMovie = mMovies.get(adapterPosition);
//            mFavouriteClickHandler.onClick(currentMovie);
//        }
//    }
//}
