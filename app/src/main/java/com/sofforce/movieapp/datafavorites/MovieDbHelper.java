package com.sofforce.movieapp.datafavorites;

import android.content.Context;
import android.database.Cursor;
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

    @Override
    public SQLiteDatabase getWritableDatabase() {

        return super.getWritableDatabase();
    }


    public boolean isFavorite(String id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("favoriteMovies", new String[]{"_ID"}, "_ID=?", new String[]{id}, null, null, null);
        if (c.moveToFirst()) {
            if (c.getCount() > 0){
                return true;
            }
        }
        c.close();
        return false;
    }




    public void delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + MovieEntry.TABLE_NAME + " WHERE " +
                        MovieEntry._ID + " = '" + id + "'";
        db.execSQL( query );
        db.close();
    }


    public void deleteAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + MovieEntry.TABLE_NAME;
        db.execSQL( query );

        db.close();

    }

    public Cursor getItemID(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query =  "SELECT " + MovieEntry._ID + " FROM " + MovieEntry.TABLE_NAME +
                       " WHERE " + MovieEntry._ID + " = '" + id + "'" ;
        Cursor data = db.rawQuery( query, null );
        data.close();

        return  data;

    }

//    public void update() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String query = "UPDATE " + MovieEntry.TABLE_NAME + "_ID";
//        db.update( query );
//    }



}

