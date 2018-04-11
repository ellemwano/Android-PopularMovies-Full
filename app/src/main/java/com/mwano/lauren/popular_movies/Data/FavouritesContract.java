package com.mwano.lauren.popular_movies.Data;

import android.provider.BaseColumns;

public class FavouritesContract {

    // Private constructor
    private FavouritesContract() {
    }

    public static final class FavouritesEntry implements BaseColumns {

        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_RATING = "movieRating";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_SYNOPSIS = "movieSynopsis";
    }

}
