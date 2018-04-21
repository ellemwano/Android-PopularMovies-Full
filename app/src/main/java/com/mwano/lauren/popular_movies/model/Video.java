package com.mwano.lauren.popular_movies.model;

import android.net.Uri;

public class Video {

    private String mVideoName;
    private String mVideoKey;

    private static final String BASE_VIDEO_THUMBNAIL_PATH = "https://i1.ytimg.com/vi";
    private static final String END_VIDEO_THUMBNAIL_PATH = "0.jpg";
    private static final String BASE_YOU_TUBE_PATH = "https://www.youtube.com/watch";

    // Constructor
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

    @Override
    public String toString() {
        return mVideoName + "--" + mVideoKey + "--";
    }

    /**
     * Build the uri for the YouTube video thumbnail to be displayed
     * @param video The given video
     * @return The uri to the YouTube thumbnail for the given video
     */
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

    /**
     Build the uri for the YouTube video to be played
     * @param video The given video
     * @return The uri of the given video on YouTube
     */
    public static Uri buildYouTubePath (Video video) {
        Uri builtYouTubeUri = null;
        String keySort = video.getVideoKey();
        try {
            builtYouTubeUri = Uri.parse(BASE_YOU_TUBE_PATH)
                    .buildUpon()
                    .appendQueryParameter("v", keySort)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builtYouTubeUri;
    }

}
