package com.mwano.lauren.popular_movies.utils;

import android.util.Log;

import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ElleMwa on 24/02/2018.
 */

public class JsonUtils {

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String VIDEO_NAME = "video_name";
    private static final String VIDEO_KEY = "video_key";
    private static final String TAG = JsonUtils.class.getSimpleName();

    private JsonUtils() {
    }

    /**
     * Parse
     * @param json
     * @return
     */
    public static ArrayList<Movie> parseMovieJson(String json) {

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(json);

            if (rootObject.has(RESULTS)) {
                JSONArray movieArray = rootObject.getJSONArray(RESULTS);

                for (int i = 0; i < movieArray.length(); i++) {
                    JSONObject currentMovie = movieArray.getJSONObject(i);

                    int id = currentMovie.optInt(ID);
                    String imagePath = currentMovie.optString(POSTER_PATH);
                    String backdropPath = currentMovie.optString(BACKDROP_PATH);
                    String originalTitle = currentMovie.optString(ORIGINAL_TITLE);
                    String synopsis = currentMovie.optString(OVERVIEW);
                    String releaseDate = currentMovie.optString(RELEASE_DATE);
                    Double rating = currentMovie.optDouble(VOTE_AVERAGE);

                    Movie movie = new Movie(id, imagePath, backdropPath, originalTitle, synopsis, releaseDate, rating);
                    movies.add(movie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Problem parsing movies json");
            return null;
        }
        return movies;
    }


    /**
     *
     * @param json
     * @return
     */
    public static ArrayList<Video> parseVideoJson (String json) {

        ArrayList<Video> videos = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(json);

            if (rootObject.has(RESULTS)) {
                JSONArray videoArray = rootObject.getJSONArray(RESULTS);

                for (int i = 0; i < videoArray.length(); i++) {
                    JSONObject currentVideo = videoArray.getJSONObject(i);

                    String videoName = currentVideo.optString(VIDEO_NAME);
                    String videoKey = currentVideo.optString(VIDEO_KEY);

                    Video video = new Video(videoName, videoKey);
                    videos.add(video);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Problem parsing videos json");
            return null;
        }
        return videos;
    }
}



