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
    public static final String VIDEO_END = "videos";
    public static final String REVIEW_END = "reviews";
    private static final String TAG = MovieApi.class.getSimpleName();


    /**
     * Build request URL to the MovieDB for popular or top_rated movies
     * @param sortType The popular or top_rated endpoints
     * @return Request URL to the MovieDB for popular or top_rated movies
     */
    public static URL buildMovieUrl(String sortType) {

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
        //URL is wrong
        return url;
    }

    /**
     * Build request URL to the MovieDB for video trailers for a given movie
     * @param movieId The movie id
     * @return Request URL to the MovieDB for video trailers for a given movie
     */
    public static URL buildVideoReviewUrl(String movieId, String videoReviewSort) {

        Uri builtVideoUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(movieId)
                .appendPath(videoReviewSort)
                .appendQueryParameter(API_END, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtVideoUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Built videoURI " + url);
        //URL is correct
        return url;
    }
}



