package com.sofforce.movieapp;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sofforce.movieapp.datafavorites.MovieContract;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by mac on 12/28/17.
 */

public class DetailedActivity extends AppCompatActivity {


    TextView txtName;
    TextView txtRating;
    TextView txtOverview;
    TextView txtYear;
    ImageView imageView;
    Button favButton;


    ListView reviews;
    ListView videos;

    ArrayList<MovieReviews> reviewsArrayList;


    String pref ="http://image.tmdb.org/t/p/w342";
    String url;
    String pref2 ="http://image.tmdb.org/t/p/w500";


    private String imageUri;
    private int movieId;
    private String movieTitle;
    private static final String API_KEY = BuildConfig.API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();

        reviewsArrayList =  new ArrayList<>();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


         txtName = (TextView) findViewById(R.id.marquee_title);
         txtRating = (TextView) findViewById(R.id.movie_rating);
         txtOverview = (TextView) findViewById(R.id.movie_overview);
         txtYear = (TextView) findViewById(R.id.movie_year);
         imageView = (ImageView) findViewById(R.id.grid_item_image);
         reviews = (ListView) findViewById(R.id.reviewsList);   //this is for the reviews List view
         videos = (ListView) findViewById(R.id.videosList);   //this is for the reviews List view

        MovieStat object = (MovieStat) getIntent().getParcelableExtra("parcel");
            txtName.setText(object.getTitle());
            txtRating.setText(object.getVoteAverage().toString());
            txtOverview.setText(object.getOverview());
            txtYear.setText(object.getReleaseDate());
            url = object.getPosterPath();

            movieId = object.getIdNumber();
            movieTitle = object.getTitle();
            imageUri = url;


        Picasso.with(this).load(pref+url ).into(imageView);

            favButton =  (Button) findViewById(R.id.favoriteButt);

        Log.d( "MOVIE_ID", "this is the movie ID " + movieId );



        //this is the url string for the reviews.
        String newUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=";


       // MovieReviews object2 =  (MovieReviews) getParcelable("parcel");
        ReviewsAsync reviewsAsync = new ReviewsAsync();
        reviewsAsync.execute(newUrl+API_KEY);
    }

    //this is the addFavorite() for the favorite button
    public void addFavorite(View view) {

       /*in this method there are a few steps to follow
            step 1: grab the information that is displayed, the ID, the movieImage, and the name
            step 2: make a contentvalue object to pass theses values to it
            step 3: pass that information to be stored in the MovieDbHelper database
            step 4: finish the activity(this returns back to the mainActivity)
       * */


        ContentValues contentValues =  new ContentValues();

        contentValues.put(MovieContract.MovieEntry._ID, movieId);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, movieTitle);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, imageUri);

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        finish();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    //this is to retrieve the reviews
    public class ReviewsAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            StringBuilder content =  new StringBuilder();

            try {
                URL url  = new URL(params[0]);
                URLConnection urlConnection =  url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while((line = bufferedReader.readLine()) !=null) {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        public void onProgressUpdate(String... values) {

        }

        @Override
        public void onPostExecute(String s) {

            reviewsArrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject( s );
                JSONArray jsonArray = jsonObject.getJSONArray( "results" );
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject detailedObjects = jsonArray.getJSONObject( i );
                    reviewsArrayList.add( new MovieReviews( detailedObjects.getString( "id" ),
                            detailedObjects.getString( "author" ),
                            detailedObjects.getString( "content" ),
                            detailedObjects.getString( "url" )
                    ) );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setAdapter( reviewsArrayList );
        }

        private void setAdapter(ArrayList<MovieReviews> movieReviews) {
            ReviewsListAdapter adapter = new ReviewsListAdapter(
                    getApplicationContext(), R.id.reviewsList, movieReviews
            );
            reviews.setAdapter( adapter );
        }

        @Override
        public void onPreExecute() {

        }

    }
}
