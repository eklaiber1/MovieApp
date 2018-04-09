package com.sofforce.movieapp.datafavorites;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import static com.sofforce.movieapp.datafavorites.MovieContract.MovieEntry.TABLE_NAME;

/**
 * Created by mac on 2/12/18.
 */

public class MovieContentProvider extends ContentProvider {

    private MovieDbHelper mMovieDbHelper;
    private SQLiteDatabase db;


    public static final int  MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
       UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

       uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);

       uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIES_WITH_ID);

       return uriMatcher;

    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mMovieDbHelper = new MovieDbHelper(context);
        db = mMovieDbHelper.getWritableDatabase();

        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String s1) {

        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match){
            case MOVIES:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        s1,
                        null,
                        null
                        );
                break;

            case MOVIES_WITH_ID:

                String id = uri.getPathSegments().get( 1 );
                String mSelection = "_ID=?";
                String[] mSelectionArgs = new String [] {id};

                retCursor = db.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        s1,
                        null,
                        null
                );
                break;

            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d( "QUERTY FUNCTION", "The query is showing: F_Database______________" + retCursor.toString() );

        return retCursor;
    }






    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;
        switch (match) {
            case MOVIES:

                long id =  -1;

                try{
                    id = db.insert(TABLE_NAME, null, contentValues);

                } catch (Exception e ) {

                }

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String id, @Nullable String[] selectionArgs) {

        db = mMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int count = 0;
        switch (match){
            case MOVIES:
                count = db.delete(TABLE_NAME, id, selectionArgs);
                break;

            case MOVIES_WITH_ID:
                id = uri.getPathSegments().get(1);
                String[] mSelectionArgs = new String [] {id};

                count = db.delete( TABLE_NAME, "_ID=? " + id , mSelectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }






    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                count = db.update(TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case MOVIES_WITH_ID:
                count = db.update(TABLE_NAME, contentValues,
                        MovieContract.MovieEntry._ID + " = " + uri.getPathSegments().get(1) +
                                  (!TextUtils.isEmpty(selection) ? " " + " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}
