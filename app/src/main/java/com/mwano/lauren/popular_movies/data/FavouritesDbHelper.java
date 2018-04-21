package com.mwano.lauren.popular_movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mwano.lauren.popular_movies.data.FavouritesContract.FavouritesEntry;


public class FavouritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public FavouritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " +
                FavouritesEntry.TABLE_NAME + " (" +
                FavouritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouritesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                FavouritesEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL, " +
                FavouritesEntry.COLUMN_MOVIE_BACKDROP + " TEXT NOT NULL, " +
                FavouritesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavouritesEntry.COLUMN_MOVIE_SYNOPSIS + " TEXT NOT NULL, " +
                FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                FavouritesEntry.COLUMN_RATING + " DOUBLE NOT NULL, " +
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
