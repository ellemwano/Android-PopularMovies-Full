package com.mwano.lauren.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwano.lauren.popular_movies.Adapter.VideoAdapter;
import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ElleMwa on 25/02/2018.
 */

public class DetailActivity extends AppCompatActivity implements VideoAdapter.VideoAdapterOnClickHandler{

    private Toolbar mToolbar;
    private ImageView mBackdropView;
    private ImageView mPosterView;
    private TextView mTitleView;
    private TextView mSynopsisView;
    private TextView mReleaseView;
    private TextView mRatingView;
    ArrayList<Video> videos;
    private RecyclerView mVideoRecyclerView;
    private VideoAdapter mVideoAdapter;

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
                Movie currentMovie = intentThatStartedThisActivity.getParcelableExtra("movie");
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
        // Get reference to error TextView
        //mConnectionErrorMessageDisplay = (TextView) findViewById(R.id.connection_error_message_tv);
        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));
        mVideoRecyclerView.setHasFixedSize(true);
        // Create new Adapter and set to RecyclerView in layout
        mVideoAdapter = new VideoAdapter(this, videos, this);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
    }

    @Override
    public void onClick(Video currentVideo) {

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
