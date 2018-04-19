package com.mwano.lauren.popular_movies;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mwano.lauren.popular_movies.adapter.FavouriteAdapter;
import com.mwano.lauren.popular_movies.adapter.MovieAdapter;
import com.mwano.lauren.popular_movies.data.FavouritesContract;
import com.mwano.lauren.popular_movies.data.MoviePosterLoader;
import com.mwano.lauren.popular_movies.model.Movie;

import java.util.ArrayList;

import static com.mwano.lauren.popular_movies.data.MoviePosterLoader.MOVIE_QUERY_LOADER;
import static com.mwano.lauren.popular_movies.data.MoviePosterLoader.SORT_QUERY;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler,
        FavouriteAdapter.FavouriteAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>>,
        NavigationView.OnNavigationItemSelectedListener {

    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<Movie> movies;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mConnectionErrorMessageDisplay;
    private int mColumnsNumber;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private Context mContext;
    private FavouriteAdapter mFavouriteAdapter;
    private int displaySelected;

    public static final int FAVOURITES_LOADER = 50;

    //
    private LoaderManager.LoaderCallbacks FavouritesCursorLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
                    switch (loaderId) {
                        case FAVOURITES_LOADER:
                            // Define a projection that specifies the columns from the table
                            String[] projection = {
                                    FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID,
                                    FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER,
                                    FavouritesContract.FavouritesEntry.COLUMN_MOVIE_BACKDROP,
                                    FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE,
                                    FavouritesContract.FavouritesEntry.COLUMN_MOVIE_SYNOPSIS,
                                    FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE,
                                    FavouritesContract.FavouritesEntry.COLUMN_RATING
                            };
                            return new CursorLoader(MainActivity.this,
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
                public void onLoaderReset(Loader loader) {
                    mFavouriteAdapter.swapCursor(null);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        mMovieAdapter = new MovieAdapter(this, movies, this);
        mFavouriteAdapter = new FavouriteAdapter(this, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        // TODO TO RETHINK
        // Movie loader bundle
        Bundle popularBundle = new Bundle();
        popularBundle.putString(SORT_QUERY, POPULAR);
        // Initialise Movie loader
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ArrayList<Movie>> moviePosterLoader = loaderManager.getLoader(MOVIE_QUERY_LOADER);


//        // Persistence
//        if (savedInstanceState != null) {
//            displaySelected = savedInstanceState.getInt("selected display");
//        } else {
            if (moviePosterLoader == null) {
                getSupportLoaderManager().initLoader(MOVIE_QUERY_LOADER, popularBundle, MainActivity.this);
            } else {
                getSupportLoaderManager().restartLoader(MOVIE_QUERY_LOADER, popularBundle, MainActivity.this);
            }
//        }

    }

    // Create new MoviePosterLoader
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, final Bundle args) {
        return new MoviePosterLoader(MainActivity.this, args);
    }

    // Set Movie data to the adapter
    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        if (movies != null) {
            mMovieAdapter.setMovieData(movies);
        } else {
            showConnectionErrorMessage();
            Log.i(TAG, "Error displaying movies");
        }
    }

    // Reset Movie loader
    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    }

    // Open DetailActivity intent, using parcelable
    @Override
    public void onClick(Movie clickedMovie) {
        mContext = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(this, destinationClass);
        intentToStartDetailActivity.putExtra("movie", clickedMovie);
        startActivity(intentToStartDetailActivity);
    }

    // Sorting popular/top rated/favourite movies via the navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mContext = MainActivity.this;
        displaySelected = item.getItemId();
        switch (displaySelected) {
            case R.id.nav_popular:
                // Sort movies by Popular Movies
                drawer.closeDrawer(GravityCompat.START);
                displayPopularMovies();
                return true;
            case R.id.nav_top_rated:
                // Sort movies by Top Rated Movies
                drawer.closeDrawer(GravityCompat.START);
                displayTopRatedMovies();
                return true;
            case R.id.nav_favourite:
                // Display user's Favourite Movies
                drawer.closeDrawer(GravityCompat.START);
                displayFavouriteMovies();
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
        // Create new Adapter and set to RecyclerView in layout
        //mMovieAdapter = new MovieAdapter(this, movies, this);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    private void displayPopularMovies() {
        loadMovieData(POPULAR);
        setTitle(R.string.popular_movies);
    }

    private void displayTopRatedMovies() {
        loadMovieData(TOP_RATED);
        setTitle(R.string.top_rated_movies);
    }

    private void displayFavouriteMovies() {
        showMovieDataView();
        getSupportLoaderManager().initLoader(FAVOURITES_LOADER, null, FavouritesCursorLoader);
        mRecyclerView.setAdapter(mFavouriteAdapter);
        setTitle("My Favourite movies");
    }

    private void showMovieDataView() {
        mConnectionErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showConnectionErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mConnectionErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

//        // TODO State persistence
//    @Override
//    protected void onSaveInstanceState(Bundle selectedState) {
//        selectedState.putInt("selected display", displaySelected);
//        super.onSaveInstanceState(selectedState);
//
//    }
}

