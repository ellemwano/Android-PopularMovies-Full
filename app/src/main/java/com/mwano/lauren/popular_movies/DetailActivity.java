package com.mwano.lauren.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
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

import com.mwano.lauren.popular_movies.Adapter.ReviewAdapter;
import com.mwano.lauren.popular_movies.Adapter.VideoAdapter;
import com.mwano.lauren.popular_movies.Data.MoviePosterLoader;
import com.mwano.lauren.popular_movies.Data.VideoLoader;
import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.model.Review;
import com.mwano.lauren.popular_movies.model.Video;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import static com.mwano.lauren.popular_movies.Data.VideoLoader.MOVIE_ID;
import static com.mwano.lauren.popular_movies.Data.VideoLoader.VIDEO_QUERY_LOADER;

/**
 * Created by ElleMwa on 25/02/2018.
 */

public class DetailActivity extends AppCompatActivity
        implements VideoAdapter.VideoAdapterOnClickHandler, ReviewAdapter.ReviewAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<Video>>{

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
    private TextView mDownloadErrorMessageDisplay;
    int id;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        // Details intent from Movies grid
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movie")) {
                currentMovie = intentThatStartedThisActivity.getParcelableExtra("movie");
                Picasso.with(this).load(Movie.buildFullPosterPath(currentMovie))
                        .into(mPosterView);
                Picasso.with(this).load(Movie.buildFullBackdropPath(currentMovie))
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
                (this, LinearLayoutManager.HORIZONTAL, false));
        mVideoRecyclerView.setHasFixedSize(true);
        // Create new Adapter and set to RecyclerView in layout
        mVideoAdapter = new VideoAdapter(this, videos, this);
        mVideoRecyclerView.setAdapter(mVideoAdapter);

        //Get reference to Review RecyclerView
        mReviewRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_reviews);
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewRecyclerView.setHasFixedSize(true);
        mReviewAdapter = new ReviewAdapter(this, reviews,this);
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        // VideoLoader bundle
        Bundle videoBundle = new Bundle();
        String movieId = String.valueOf(currentMovie.getId());
        Log.i(TAG, "CurrentMovie ID is: " + movieId);
        // ID is correct
        videoBundle.putString(MOVIE_ID, movieId);

        // Initialise Video loader
        getSupportLoaderManager().initLoader(VIDEO_QUERY_LOADER, videoBundle, DetailActivity.this);
    }

    // Create new VideoLoader
    @Override
    public Loader<ArrayList<Video>> onCreateLoader(int id, final Bundle args) {
        return new VideoLoader(DetailActivity.this, args);
    }

    // Set Video data to the adapter
    @Override
    public void onLoadFinished(Loader<ArrayList<Video>> loader, ArrayList<Video> videos) {
        if (videos != null) {
             mVideoAdapter.setVideoData(videos);
        } else {
            //showConnectionErrorMessage();
            Log.i(TAG, "Error displaying videos");
        }

    } // Reset VideoLoader
    @Override
    public void onLoaderReset(Loader<ArrayList<Video>> loader) {
    }

    @Override
    public void onClick(Video currentVideo) {
    }

    @Override
    public void onClick(Review currentReview) {
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
