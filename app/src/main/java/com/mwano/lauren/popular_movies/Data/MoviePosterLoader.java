package com.mwano.lauren.popular_movies.Data;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.utils.JsonUtils;
import com.mwano.lauren.popular_movies.utils.MovieApi;
import com.mwano.lauren.popular_movies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.mwano.lauren.popular_movies.MainActivity.POPULAR;
import static com.mwano.lauren.popular_movies.MainActivity.TOP_RATED;


/**
 * Created by ElleMwa on 24/03/2018.
 */

public class MoviePosterLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    public static final int MOVIE_QUERY_LOADER = 10;
    public static final String SORT_QUERY = "sort query";
    Bundle mArgs;
    ArrayList<Movie> mMovieData;

    // Constructor
    public MoviePosterLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieData != null) {
            // Use cached data
            deliverResult(mMovieData);
        } else {
            // Load data as there's none
            forceLoad();
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        String movieSort = mArgs.getString(SORT_QUERY);

        if (movieSort != null && movieSort.equals("")) {
            return null;
        }
        URL movieRequestUrl = MovieApi.buildUrl(movieSort);

        try {
            switch (movieSort) {
                case POPULAR:
                    movieRequestUrl = MovieApi.buildUrl(POPULAR);
                    break;
                case TOP_RATED:
                    movieRequestUrl = MovieApi.buildUrl(TOP_RATED);
                    break;
                // Default exception
                default:
                    throw new UnsupportedOperationException("Unknown url: " + movieRequestUrl);
            }
            String jsonResponse = NetworkUtils.httpConnect(movieRequestUrl);
            return JsonUtils.parseMovieJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
