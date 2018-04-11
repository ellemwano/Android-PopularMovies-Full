package com.mwano.lauren.popular_movies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class FavouritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = FavouritesDbHelper.class.getSimpleName();

    // Constructor
    public FavouritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct FavouritesDbHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " +
                FavouritesContract.FavouritesEntry.TABLE_NAME + " (" +
                FavouritesContract.FavouritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL, " +
                FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                FavouritesContract.FavouritesEntry.COLUMN_RATING + " DOUBLE NOT NULL, " +
                FavouritesContract.FavouritesEntry.COLUMN_MOVIE_SYNOPSIS + " TEXT NOT NULL, " +
                // To ensure this table can only have one entry per movieID
                " UNIQUE (" + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouritesContract.FavouritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
