package com.mwano.lauren.popular_movies.model;

import android.net.Uri;
import android.util.Log;

public class Video {

//    {
    //  "id": 181808,
    //  "results": [
    //    {
    //      "id": "597bb8969251414c1d002d92",
    //      "iso_639_1": "en",
    //      "iso_3166_1": "US",
    //      "key": "ye6GCY_vqYk",
    //      "name": "Star Wars: The Last Jedi Behind The Scenes",
    //      "site": "YouTube",
    //      "size": 1080,
    //      "type": "Featurette"
    //    },
    //    {
    //      "id": "597bb8b3c3a368182a00000d",
    //      "iso_639_1": "en",
    //      "iso_3166_1": "US",
    //      "key": "zB4I68XVPzQ",
    //      "name": "Star Wars: The Last Jedi Official Teaser",
    //      "site": "YouTube",
    //      "size": 1080,
    //      "type": "Teaser"
    //    }
//    ]
//    }

// https://api.themoviedb.org/3/movie/(movieId)/videos?
// api_key=(API_KEY)&language=en-US
//
// https://www.youtube.com/watch?v=(key)      watch video
//    https://i1.ytimg.com/vi/(key)/0.jpg    video thumbnail

    private String mVideoName;
    private String mVideoKey;

    private static final String BASE_VIDEO_THUMBNAIL_PATH = "https://i1.ytimg.com/vi";
    private static final String END_VIDEO_THUMBNAIL_PATH = "0.jpg";


    public Video (String videoName, String videoKey) {
        mVideoName = videoName;
        mVideoKey = videoKey;
    }

    public String getVideoName() {
        return mVideoName;
    }

    public void setVideoName(String videoName) {
        mVideoName = videoName;
    }

    public String getVideoKey() {
        return mVideoKey;
    }

    public void setVideoKey(String videoKey) {
        mVideoKey = videoKey;
    }

    public static Uri buildVideoThumbnailPath (Video video) {
        Uri builtVideoThumbnailUri = null;
        String keySort = video.getVideoKey();
        try {
            builtVideoThumbnailUri = Uri.parse(BASE_VIDEO_THUMBNAIL_PATH)
                    .buildUpon()
                    .appendPath(keySort)
                    .appendPath(END_VIDEO_THUMBNAIL_PATH)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builtVideoThumbnailUri;
    }

}
