package com.mwano.lauren.popular_movies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mwano.lauren.popular_movies.adapter.ReviewAdapter;
import com.mwano.lauren.popular_movies.adapter.VideoAdapter;
import com.mwano.lauren.popular_movies.data.FavouritesContract.FavouritesEntry;
import com.mwano.lauren.popular_movies.data.FavouritesDbHelper;
import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.model.Review;
import com.mwano.lauren.popular_movies.model.Video;
import com.mwano.lauren.popular_movies.utils.JsonUtils;
import com.mwano.lauren.popular_movies.utils.MovieApi;
import com.mwano.lauren.popular_movies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.mwano.lauren.popular_movies.utils.MovieApi.REVIEW_END;
import static com.mwano.lauren.popular_movies.utils.MovieApi.VIDEO_END;

/**
 * Created by ElleMwa on 25/02/2018.
 */

public class DetailActivity extends AppCompatActivity
        implements VideoAdapter.VideoAdapterOnClickHandler, ReviewAdapter.ReviewAdapterOnClickHandler {

    private Toolbar mToolbar;
    private ImageView mBackdropView;
    private ImageView mPosterView;
    private TextView mTitleView;
    private TextView mSynopsisView;
    private TextView mReleaseView;
    private TextView mRatingView;
    ArrayList<Video> videos;
    ArrayList<Review> reviews;
    public Movie currentMovie;
    private RecyclerView mVideoRecyclerView;
    private RecyclerView mReviewRecyclerView;
    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;
    private int mVideoNumber;
    private ImageView mArrowMore;
    private TextView mDownloadErrorMessageDisplay;
    int id;
    private FloatingActionButton fab;
    private FavouritesDbHelper mDbHelper;
    public static final String VIDEO_REVIEW_SORT = "video_review_sort";
    public static final String MOVIE_ID = "movie_id";
    private static final String TAG = DetailActivity.class.getSimpleName();

    // Implementing multiple loaders in one activity.
    // Source code: https://stackoverflow.com/questions/15643907/multiple-loaders-in-same-activity
    private LoaderManager.LoaderCallbacks VideoLoaderListener =
            new LoaderManager.LoaderCallbacks<ArrayList<Video>>() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public Loader<ArrayList<Video>> onCreateLoader(int id, final Bundle args) {
                    return (Loader<ArrayList<Video>>) new AsyncTaskLoader<ArrayList<Video>>(DetailActivity.this) {

                        Bundle mArgs = args;
                        ArrayList<Video> mVideoData;

                        @Override
                        protected void onStartLoading() {
                            if (mVideoData != null) {
                                // Use cached data
                                deliverResult(mVideoData);
                            } else {
                                // Load data as there's none
                                forceLoad();
                            }
                        }

                        @Override
                        public ArrayList<Video> loadInBackground() {
                            if (mArgs == null) {
                                return null;
                            }
                            String movieId = mArgs.getString(MOVIE_ID);
                            Log.i(TAG, "ID value is: " + movieId);
                            // ID is correct
                            String videoReviewSort = mArgs.getString(VIDEO_REVIEW_SORT);
                            Log.i(TAG, "Sort value is: " + videoReviewSort);
                            URL videoRequestUrl;

                            if ((movieId != null && movieId.equals(""))
                                    || (videoReviewSort != null && videoReviewSort.equals(""))) {
                                return null;
                            }
                            try {
                                videoRequestUrl = MovieApi.buildVideoReviewUrl(movieId, videoReviewSort);
                                // This URL is fine. Check logCat from MovieApi                                }
                                String jsonResponse = NetworkUtils.httpConnect(videoRequestUrl);
                                Log.i(TAG, JsonUtils.parseVideoJson(jsonResponse).toString());
                                // All good
                                return JsonUtils.parseVideoJson(jsonResponse);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    };
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<Video>> loader) {
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<Video>> loader, ArrayList<Video> videos) {
                    if (videos != null) {
                        mVideoAdapter.setVideoData(videos);
                        // Display "more" arrow icon if number of videos >2 in portrait and >3 in landscape
                        mVideoNumber = (int) getResources().getInteger(R.integer.num_of_videos);
                        if (videos.size() > mVideoNumber) {
                            mArrowMore.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //showConnectionErrorMessage();
                        Log.i(TAG, "Error displaying videos");
                    }
                }
            };

    private LoaderManager.LoaderCallbacks ReviewLoaderListener =
            new LoaderManager.LoaderCallbacks<ArrayList<Review>>() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public Loader<ArrayList<Review>> onCreateLoader(int id, final Bundle args) {
                    return (Loader<ArrayList<Review>>) new AsyncTaskLoader<ArrayList<Review>>(DetailActivity.this) {

                        Bundle mArgs = args;
                        ArrayList<Review> mReviewData;

                        @Override
                        protected void onStartLoading() {
                            if (mReviewData != null) {
                                // Use cached data
                                deliverResult(mReviewData);
                            } else {
                                // Load data as there's none
                                forceLoad();
                            }
                        }

                        @Override
                        public ArrayList<Review> loadInBackground() {
                            if (mArgs == null) {
                                return null;
                            }
                            String movieId = mArgs.getString(MOVIE_ID);
                            Log.i(TAG, "ID value is: " + movieId);
                            // ID is correct
                            String videoReviewSort = mArgs.getString(VIDEO_REVIEW_SORT);
                            Log.i(TAG, "Sort value is: " + videoReviewSort);
                            URL reviewRequestUrl;

                            if ((movieId != null && movieId.equals(""))
                                    || (videoReviewSort != null && videoReviewSort.equals(""))) {
                                return null;
                            }
                            try {
                                reviewRequestUrl = MovieApi.buildVideoReviewUrl(movieId, videoReviewSort);
                                // This URL is fine. Check logCat from MovieApi                                }
                                String jsonResponse = NetworkUtils.httpConnect(reviewRequestUrl);
                                Log.i(TAG, JsonUtils.parseVideoJson(jsonResponse).toString());
                                // All good
                                return JsonUtils.parseReviewJson(jsonResponse);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    };
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<Review>> loader) {
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> reviews) {
                    if (reviews != null) {
                        mReviewAdapter.setReviewData(reviews);
                    } else {
                        //showConnectionErrorMessage();
                        Log.i(TAG, "Error displaying reviews");
                    }
                }
            };

    private static final int VIDEO_QUERY_LOADER = 20;
    private static final int REVIEW_QUERY_LOADER = 30;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mToolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(mToolbar);

        mPosterView = (ImageView) findViewById(R.id.poster_iv);
        mBackdropView = (ImageView) findViewById(R.id.backdrop_iv);
        mTitleView = (TextView) findViewById(R.id.original_title_tv);
        mSynopsisView = (TextView) findViewById(R.id.synopsis_tv);
        mReleaseView = (TextView) findViewById(R.id.release_date_tv);
        mRatingView = (TextView) findViewById(R.id.rating_tv);
        mArrowMore = (ImageView) findViewById(R.id.arrow_right);

        // Details intent from Movies grid
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movie")) {
                currentMovie = intentThatStartedThisActivity.getParcelableExtra("movie");
                // Load movie posters from Picasso
                Picasso posterPicasso = Picasso.with(DetailActivity.this);
                posterPicasso.setIndicatorsEnabled(true);
                posterPicasso.load(Movie.buildFullPosterPath(currentMovie))
                        .into(mPosterView);
                // Load backdrop images from Picassso
                Picasso backPicasso = Picasso.with(DetailActivity.this);
                backPicasso.setIndicatorsEnabled(true);
                backPicasso.load(Movie.buildFullBackdropPath(currentMovie))
                        .into(mBackdropView);
                mTitleView.setText(currentMovie.getOriginalTitle());
                mSynopsisView.setText(currentMovie.getSynopsis());
                mReleaseView.setText(currentMovie.getReleaseDate());
                mRatingView.setText(String.valueOf(currentMovie.getRating()));
                setTitle(currentMovie.getOriginalTitle());
            }
        }

        // Set the action bar button to look like an up button
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get reference to VideoRecyclerView
        mVideoRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_videos);
        // TODO set error view
        // Get reference to error TextView
        //mDownloadErrorMessageDisplay = (TextView) findViewById(R.id.);
        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager
                (DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        mVideoRecyclerView.setHasFixedSize(true);
        // Create new Adapter and set to RecyclerView in layout
        mVideoAdapter = new VideoAdapter(DetailActivity.this, videos, this);
        mVideoRecyclerView.setAdapter(mVideoAdapter);

        //Get reference to Review RecyclerView
        mReviewRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_reviews);
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        mReviewRecyclerView.setHasFixedSize(true);
        mReviewAdapter = new ReviewAdapter(this, reviews, this);
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        String movieId = String.valueOf(currentMovie.getId());
        Log.i(TAG, "CurrentMovie ID is: " + movieId);
        // ID is correct
        // VideoLoader bundle
        Bundle videoBundle = new Bundle();
        videoBundle.putString(MOVIE_ID, movieId);
        videoBundle.putString(VIDEO_REVIEW_SORT, VIDEO_END);
        // Initialise VideoLoader
        getSupportLoaderManager().initLoader(VIDEO_QUERY_LOADER, videoBundle, VideoLoaderListener);
        Bundle reviewBundle = new Bundle();
        reviewBundle.putString(MOVIE_ID, movieId);
        reviewBundle.putString(VIDEO_REVIEW_SORT, REVIEW_END);
        getSupportLoaderManager().initLoader(REVIEW_QUERY_LOADER, reviewBundle, ReviewLoaderListener);

        // Instantiate subclass of SQLiteOpenHelper
        mDbHelper = new FavouritesDbHelper(this);

        // TODO Change to toast
        // FAB to add movie to Favourites and show messsage (currently a snackbar)
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setImageDrawable(R.drawable.ic);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add movie to favourite
                addFavourite();
                // Change colour
                // TODO
                fab.setImageResource(R.drawable.ic_details_favorite_full);
//                // Display message to user
//                Snackbar.make(v, "Added to Favourites!",
//                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    // Implicit intent to play YouTube Video trailers from video URI
    @Override
    public void onClickVideo(Video currentVideo) {
        Intent intentPlayVideo = new Intent(Intent.ACTION_VIEW, Video.buildYouTubePath(currentVideo));
        if(intentPlayVideo.resolveActivity(getPackageManager()) != null) {
            startActivity(intentPlayVideo);
        }
    }

    // TODO Implement expanding TextView
    // Implicit intent to show full Review on webpage
    @Override
    public void onClickReview(Review currentReview) {
        String currentReviewUrl = currentReview.getReviewUrl();
        Intent intentOpenReview = new Intent(Intent.ACTION_VIEW, Uri.parse(currentReviewUrl));
        if(intentOpenReview.resolveActivity(getPackageManager()) != null) {
            startActivity(intentOpenReview);
        }
    }

    // Add movie to Favourites
    private void addFavourite() {
        // Get the database in write mode
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a ContentValues object with column names as the keys and movie data as the values
        ContentValues values = new ContentValues();
        values.put(FavouritesEntry.COLUMN_MOVIE_ID, currentMovie.getId());
        values.put(FavouritesEntry.COLUMN_MOVIE_TITLE, currentMovie.getOriginalTitle());
        values.put(FavouritesEntry.COLUMN_RATING, currentMovie.getRating());
        values.put(FavouritesEntry.COLUMN_MOVIE_POSTER, Movie.buildFullPosterPath(currentMovie).toString());
        values.put(FavouritesEntry.COLUMN_MOVIE_BACKDROP, Movie.buildFullBackdropPath(currentMovie).toString());
        values.put(FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE, currentMovie.getReleaseDate());
        values.put(FavouritesEntry.COLUMN_MOVIE_SYNOPSIS, currentMovie.getSynopsis());
        // IInsert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(FavouritesEntry.CONTENT_URI, values);
        // Add a toast to confirm insertion
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

}
