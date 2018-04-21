package com.mwano.lauren.popular_movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavouritesProvider extends ContentProvider{

    /** URI matcher code for the content URI for the favourites table */
    private static final int FAVOURITES = 100;
    /**  URI matcher code for the content URI for a single favourite in the favourites table */
    private static final int FAVOURITE_ID = 101;

    /** Urimatcher object to match a content URI to a corresponding code */
    private static final UriMatcher sUrimatcher = buildUriMatcher();
    private FavouritesDbHelper mDbHelper;

    public static UriMatcher buildUriMatcher() {
        // Initialise a UriMatcher with no matches
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // To provide access to all rows of the favourites table
        uriMatcher.addURI(FavouritesContract.CONTENT_AUTHORITY,
                FavouritesContract.PATH_FAVOURITES, FAVOURITES);
        // To provide access to a single row of the favourites table
        uriMatcher.addURI(FavouritesContract.CONTENT_AUTHORITY,
                FavouritesContract.PATH_FAVOURITES + "/#", FAVOURITE_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new FavouritesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        final SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int match = sUrimatcher.match(uri);
        // Cursor result of the query
        Cursor retCursor;
        switch (match) {
            case FAVOURITES:
                retCursor = database.query(FavouritesContract.FavouritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Get writable database
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert the new favourite with the given values
        int match = sUrimatcher.match(uri);
        Uri returnUri;

        switch(match) {
            case FAVOURITES:
                long id = database.insert(FavouritesContract.FavouritesEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavouritesContract.FavouritesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Notify the resolver if the uri has been changed
        getContext().getContentResolver().notifyChange(uri, null);
        // return newly inserted row
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writable database
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUrimatcher.match(uri);
        int moviesDeleted;

        switch(match) {
            case FAVOURITES:
                moviesDeleted = database.delete(FavouritesContract.FavouritesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case FAVOURITE_ID:
                // Delete a single row given by the ID in the URI
                selection = FavouritesContract.FavouritesEntry._ID + " = ? ";
                selectionArgs = new String[] {
                        String.valueOf(ContentUris.parseId(uri))
                };
                moviesDeleted = database.delete(FavouritesContract.FavouritesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (moviesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return moviesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
