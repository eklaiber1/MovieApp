package com.sofforce.movieapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.sofforce.movieapp.datafavorites.MovieDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.sofforce.movieapp.datafavorites.MovieContract.MovieEntry;


public class MovieListActivity extends AppCompatActivity implements HelperAsync.AsyncTaskCallback,
        LoaderManager.LoaderCallbacks<Cursor> {

    ArrayList<MovieStat> arrayList;
    GridView theGridView;
    ConnectionDetector cd =  new ConnectionDetector(this);
    MovieDbHelper mMovieDbHelper;

    HelperAsync helperAsync = new HelperAsync();
    private static final String TAG = MovieListActivity.class.getSimpleName();

    private static final int TASK_LOADER_ID = 0;

    private static final String API_KEY = BuildConfig.API_KEY;



    //comment001 this is the onCreate method that initiate the program
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);

        theGridView = (GridView) findViewById(R.id.movieListgrid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mMovieDbHelper = new MovieDbHelper (this);

         arrayList = new ArrayList<>();


        if (cd.isConnected()) {
                    Toast.makeText(MovieListActivity.this, "You are connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieListActivity.this, "You are not connected", Toast.LENGTH_SHORT).show();

                }
        try {

            loadData("http://api.themoviedb.org/3/movie/popular?api_key=");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        //comment002 this will send you to the detailedview when you click on a image
        theGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MovieStat movie = arrayList.get(i);

                Intent mIntent =  new Intent(MovieListActivity.this, DetailedActivity.class);

                  mIntent.putExtra("parcel", movie);

                startActivity(mIntent);

            }
        });

    }

    public void loadData(String string){

        HelperAsync helperAsync = new HelperAsync();
        // here `this` refers to the container class. Hence your Activity/Fragment needs to implement its methods.
        helperAsync.setAsyncTaskCallback(this);

        helperAsync.execute(string + API_KEY);

    }


    private void loadFavorites() {
        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }


    //comment003 this is to inflate the menu on the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    //comment004 this is the method that runs when you click on an item in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mostPopular:

                if (cd.isConnected()) {
                    Toast.makeText(MovieListActivity.this, "You are connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieListActivity.this, "You are not connected", Toast.LENGTH_SHORT).show();

                }

                this.loadData("http://api.themoviedb.org/3/movie/popular?api_key=");

                return true;

            case R.id.action_toprated:

                if (cd.isConnected()) {
                    Toast.makeText(MovieListActivity.this, "You are connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieListActivity.this, "You are not connected", Toast.LENGTH_SHORT).show();

                }

                this.loadData("http://api.themoviedb.org/3/movie/top_rated?api_key=");

                return true;

            case R.id.action_favorites:

                /*
                * when a user clicks this item in the menu it will load all the
                * favorites that the clicked on in the detailed view of the movie thumbnail
                * */
                loadFavorites();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void
    onProgressUpdate(String... values) {
    }

    @Override
    public void onPostExecute(String s) {

        arrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject detailedObjects = jsonArray.getJSONObject(i);
                    arrayList.add(new MovieStat( detailedObjects.getString("title"),
                            detailedObjects.getString("poster_path"),
                            Double.valueOf(detailedObjects.getString("popularity")),
                            detailedObjects.getString("backdrop_path"),
                            Double.valueOf(detailedObjects.getString("vote_count")),
                            detailedObjects.getString("release_date"),
                            Double.valueOf(detailedObjects.getString("vote_average")),
                            detailedObjects.getString("overview"),
                            Integer.valueOf(detailedObjects.getString("id"))
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setAdapter(arrayList);
    }

    private void setAdapter(ArrayList<MovieStat> movieStats) {
        CustomListAdapter adapter = new CustomListAdapter(
                getApplicationContext(), R.id.movieListgrid, movieStats
        );
        theGridView.setAdapter(adapter);
    }






    @Override
    public void onPreExecute() {

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // re-queries for all tasks
//        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
//    }

    //these are all the methods to be overwritten by the implemented interface
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    deliverResult(mTaskData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {


                try {
                    return getContentResolver().query(MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }

            }

            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d( "TEST", "data " + data.getCount() );

        ArrayList<MovieStat> posterArray = new ArrayList<>();
        SQLiteDatabase mDb = mMovieDbHelper.getReadableDatabase();
        data = mDb.query( MovieEntry.TABLE_NAME, new String[]{MovieEntry.COLUMN_MOVIE_POSTER, MovieEntry._ID, MovieEntry.COLUMN_MOVIE_NAME},
                null,
                null,
                null,
                null,
                null );
        if (data.moveToFirst()) {
            do {
                posterArray.add( new MovieStat( data.getString( data.getColumnIndex( MovieEntry.COLUMN_MOVIE_NAME )),
                        data.getString( data.getColumnIndex( MovieEntry.COLUMN_MOVIE_POSTER ) ),
                        0.0,
                        "",
                        0.0,
                        "",
                        0.0,
                        "",
                        data.getInt( data.getColumnIndex( MovieEntry._ID ) ) ) );
                Log.d( "ARRAYCHECK", "movie poster " + data.getString(data.getColumnIndex( MovieEntry.COLUMN_MOVIE_POSTER )) );

            } while (data.moveToNext());

        }

        setAdapter( posterArray );
        data.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

