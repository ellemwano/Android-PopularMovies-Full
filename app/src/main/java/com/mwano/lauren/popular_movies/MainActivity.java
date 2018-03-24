package com.mwano.lauren.popular_movies;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.utils.JsonUtils;
import com.mwano.lauren.popular_movies.utils.MovieApi;
import com.mwano.lauren.popular_movies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>>, NavigationView.OnNavigationItemSelectedListener {

    private static final int MOVIE_QUERY_LOADER = 10;
    private static final String SORT_QUERY = "sort query";

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<Movie> movies;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mConnectionErrorMessageDisplay;
    private int mColumnsNumber;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference to RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        // Get reference to error TextView
        mConnectionErrorMessageDisplay = (TextView) findViewById(R.id.connection_error_message_tv);
        // Set GridLinearLayout to RecyclerView, 2 columns if vertical, 4 columns if horizontal
        // (Source code, Udacity forum mentor Nisha Shinde:
        // https://discussions.udacity.com/t/gridlayoutmanager-recyclerview/499251/4)
        mColumnsNumber = (int) getResources().getInteger(R.integer.num_of_columns);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, mColumnsNumber));
        mRecyclerView.setHasFixedSize(true);
        // Create new Adapter and set to RecyclerView in layout
        mMovieAdapter = new MovieAdapter(this, movies, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        // Instantiate the navigation drawer
        //(Source code, java and xml, Google's Android Developer Fundamental Course:
        // https://google-developer-training.gitbooks.io
        // /android-developer-fundamentals-course-concepts/content/en/Unit%202/42_c_menus.html
        mDrawer = (DrawerLayout)
                findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set toolbar as action bar
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        if (mDrawer != null) {
            mDrawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView)
                findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);

            setTitle(R.string.my_app_title);
        }

        // Bundle
        Bundle popularBundle = new Bundle();
        popularBundle.putString(SORT_QUERY, POPULAR);

        // TODO CHeck - Initialise the loader
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ArrayList<Movie>> moviePosterLoader = loaderManager.getLoader(MOVIE_QUERY_LOADER);
        if (moviePosterLoader == null) {
            getSupportLoaderManager().initLoader(MOVIE_QUERY_LOADER, popularBundle, MainActivity.this);
        } else {
            getSupportLoaderManager().restartLoader(MOVIE_QUERY_LOADER, popularBundle, MainActivity.this);
        }
    }


    @Override
    public void onClick(Movie currentMovie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movie", currentMovie);
        startActivity(intentToStartDetailActivity);
    }


    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Movie>>(this) {

            ArrayList<Movie> mMovieData;

            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    // Use cached data
                    deliverResult(mMovieData);
                } else {
                    //There is no data, let's load it
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Movie> loadInBackground() {
                String movieSort = args.getString(SORT_QUERY);

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
        };
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        if (movies != null) {
            mMovieAdapter.setMovieData(movies);
        } else {
            showConnectionErrorMessage();
            Log.i(TAG, "Error displaying movies");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Context context = MainActivity.this;
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_popular:
                // Sort movies by Popular Movies
                drawer.closeDrawer(GravityCompat.START);
                loadMovieData(POPULAR);
                setTitle(R.string.popular_movies);
                return true;
            case R.id.nav_top_rated:
                // Sort movies by Top Rated Movies
                drawer.closeDrawer(GravityCompat.START);
                loadMovieData(TOP_RATED);
                setTitle(R.string.top_rated_movies);
                return true;
            case R.id.nav_favourite:
                // Will handle displaying User's favourite movies (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(context, getString(R.string.toast_favourites), Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }

    private void loadMovieData(String sortMode) {
        showMovieDataView();
        Bundle sortModeBundle = new Bundle();
        sortModeBundle.putString(SORT_QUERY, sortMode);
        getSupportLoaderManager().restartLoader(MOVIE_QUERY_LOADER, sortModeBundle, MainActivity.this);
    }

    private void showMovieDataView() {
        mConnectionErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showConnectionErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mConnectionErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}

