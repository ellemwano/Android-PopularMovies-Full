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
    private static final String TAG = MovieApi.class.getSimpleName();

    public static URL buildUrl(String sortType) {

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(sortType)
                .appendQueryParameter(API_END, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Built URI " + url);
        return url;
    }



}
