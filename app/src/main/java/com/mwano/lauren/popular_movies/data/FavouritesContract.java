package com.mwano.lauren.popular_movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavouritesContract {

    // Private constructor
    private FavouritesContract() {
    }

    /**
     * Represents the domain
     */
    public static final String CONTENT_AUTHORITY = "com.mwano.lauren.popular_movies";
    /**
     * The base of all URIs apps will use to contact the content provider
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Possible path (when appended to base_content_uri)
     */
    public static final String PATH_FAVOURITES = "favourites";


    /**
     * Inner class defining the constant values for the favourites database table
     */
    public static final class FavouritesEntry implements BaseColumns {

        /**
         * The content URI to access the favourite data in the content provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAVOURITES);

        /**
         * The MIME type of the content_uri for a list of favourites
         */
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";
        public static final String COLUMN_MOVIE_BACKDROP = "movieBackdrop";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_SYNOPSIS = "movieSynopsis";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_RATING = "movieRating";
    }
}
