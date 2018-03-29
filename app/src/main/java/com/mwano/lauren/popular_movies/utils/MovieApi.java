package com.mwano.lauren.popular_movies.utils;

import android.net.Uri;
import android.util.Log;

import com.mwano.lauren.popular_movies.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ElleMwa on 07/03/2018.
 */

public class MovieApi {

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String API_END = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String MOVIE_ID = "movie_id";
    private static final String VIDEO_END = "videos";
    private static final String TAG = MovieApi.class.getSimpleName();


    /**
     * Build request URL to the MovieDB for popular or top_rated movies
     * @param sortType The popular or top_rated endpoints
     * @return Request URL to the MovieDB for popular or top_rated movies
     */
    public static URL buildUrl(String sortType) {

        Uri builtMovieUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(sortType)
                .appendQueryParameter(API_END, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtMovieUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Built movieURI " + url);
        return url;
    }

    /**
     * Build request URL to the MovieDB for video trailers for a given movie
     * @param movieId The movie id
     * @return Request URL to the MovieDB for video trailers for a given movie
     */
    public static URL buildVideoUrl (String movieId) {

        Uri builtVideoUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(MOVIE_ID)
                .appendPath(VIDEO_END)
                .appendQueryParameter(API_END, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtVideoUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Built videoURI " + url);
        return url;
    }
}



