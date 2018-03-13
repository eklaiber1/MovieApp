package com.sofforce.movieapp.datafavorites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sofforce.movieapp.datafavorites.MovieContract.MovieEntry;


/**
 * Created by mac on 2/12/18.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDB.db";

    private static final int VERSION = 1;

    //the constructor
    public MovieDbHelper(Context context) {

        super( context, DATABASE_NAME, null, VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL);";

        db.execSQL( CREATE_TABLE );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME );
        onCreate( db );
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {

        return super.getReadableDatabase();
    }

//
//    public void deleteMovie(int id, String movieName) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM" + MovieEntry.TABLE_NAME +
//                " WHERE " +
//                MovieEntry._ID + " = '" + id + "'" +
//                " AND " +
//                MovieEntry.COLUMN_MOVIE_NAME + " = '" + movieName + "'";
//        db.execSQL( query );
//
//        Log.d( "TAG", "deleteName: query: " + query );
//        Log.d( "TAG", "deleteName: Deleting:  " + movieName + " from database." );
//    }

    /*
    *This updates the name field in the database
    * @newName
    * @id
    * @oldName
    * */
//    public void updateName(String name, int id, String oldName) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE " + MovieEntry.TABLE_NAME +
//                " SET "
//                + MovieEntry.COLUMN_MOVIE_NAME + " = '" + name +
//                "' WHERE "
//                + MovieEntry._ID + " = '" + id + "'" +
//                " AND "
//                + MovieEntry.COLUMN_MOVIE_NAME + " = '" + oldName + "'";
//
//        Log.d( "TAG", "updateName: query: " + query );
//        Log.d( "TAG", "UpdateName: Setting name to " + name );
//
//        db.execSQL( query );
//    }


//    public void deleteAllDAtaInTable(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete( MovieEntry.TABLE_NAME, "_ID = * ", new String[] {id} );
//    }






}

