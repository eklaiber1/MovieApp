package com.sofforce.movieapp.datafavorites;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mac on 2/12/18.
 */

public class MovieContract  {

    public static final String AUTHORITY = "com.sofforce.movieApp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "favoriteMovies";




    //the inner class that defines the contents of the task table
    public static final class MovieEntry implements BaseColumns {

        // this CONTENT_URI is what will be used over and over again to access data
        // it will look like this  content://com.sofforce.movieApp/favoriteMovies
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        // this is the table name
        public static final String TABLE_NAME = "favoriteMovies";

        //the BaseColumn interface automatically produces an ID so the constant variable is not needed
        public static final String COLUMN_MOVIE_POSTER = "poster";

        //this is the movie name
        public static final String COLUMN_MOVIE_NAME = "movie_name";


    }




}
