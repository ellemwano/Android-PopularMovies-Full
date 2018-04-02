package com.mwano.lauren.popular_movies.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ElleMwa on 22/02/2018.
 */

public class Movie implements Parcelable {

//    {
//        "page": 1,
//            "results": [
//        {
//            "poster_path": "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
//                "adult": false,
//                "overview": "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
//                "release_date": "2016-08-03",
//                "genre_ids": [
//            14,
//                    28,
//                    80
//      ],
//            "id": 297761,
//                "original_title": "Suicide Squad",
//                "original_language": "en",
//                "title": "Suicide Squad",
//                "backdrop_path": "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
//                "popularity": 48.261451,
//                "vote_count": 1466,
//                "video": false,
//                "vote_average": 5.91
//        },

    // static
    private static int mId;
    private String mImagePath;
    private String mBackdropPath;
    private String mOriginalTitle;
    private String mSynopsis;
    private String mReleaseDate;
    private double mRating;

    private static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w185/";
    private static final String BASE_BACKDROP_PATH = "http://image.tmdb.org/t/p/w500/";

    public Movie
            (int id, String imagePath, String backdropPath, String originalTitle, String synopsis, String releaseDate,
             Double rating) {
        // static
        mId = id;
        mImagePath = imagePath;
        mBackdropPath = backdropPath;
        mOriginalTitle = originalTitle;
        mSynopsis = synopsis;
        mReleaseDate = releaseDate;
        mRating = rating;
    }

    public static int getId() {
        return mId;
    }

    public void setId(int id) {
        // static
        mId = id;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImage(String imagePath) {
        mImagePath = imagePath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropImage(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        mSynopsis = synopsis;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public void setReleaseDate (String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    public static Uri buildFullPosterPath (Movie movie) {
        Uri builtPosterUri = null;
        try {
            builtPosterUri = Uri.parse(BASE_POSTER_PATH
                    + movie.getImagePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builtPosterUri;
    }

    public static Uri buildFullBackdropPath (Movie movie) {
        Uri builtBackdropUri = null;
        try {
            builtBackdropUri = Uri.parse(BASE_BACKDROP_PATH
                    + movie.getBackdropPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builtBackdropUri;
    }

    // Implement Parcelable
    private Movie(Parcel in) {
        mId = in.readInt();
        mImagePath = in.readString();
        mBackdropPath = in.readString();
        mOriginalTitle = in.readString();
        mSynopsis = in.readString();
        mReleaseDate = in.readString();
        mRating = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return mId + "--" + mImagePath + "--" + mBackdropPath + "--" + mOriginalTitle + "--"
                + mSynopsis + "--" + mReleaseDate + "--" + mRating;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mImagePath);
        dest.writeString(mBackdropPath);
        dest.writeString(mOriginalTitle);
        dest.writeString(mSynopsis);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mRating);
    }

    // Creator needs to be static here
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
