package com.sofforce.movieapp;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sofforce.movieapp.datafavorites.MovieContract;
import com.squareup.picasso.Picasso;

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

    ArrayList<MovieStat> arrayList;


    String pref ="http://image.tmdb.org/t/p/w342";
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


         txtName = (TextView) findViewById(R.id.marquee_title);
         txtRating = (TextView) findViewById(R.id.movie_rating);
         txtOverview = (TextView) findViewById(R.id.movie_overview);
         txtYear = (TextView) findViewById(R.id.movie_year);
         imageView = (ImageView) findViewById(R.id.grid_item_image);

        MovieStat object = (MovieStat) getIntent().getParcelableExtra("parcel");
//          Bundle mbundle =  getIntent().getExtras();
            txtName.setText(object.getTitle());
            txtRating.setText(object.getVoteAverage().toString());
            txtOverview.setText(object.getOverview());
            txtYear.setText(object.getReleaseDate());
            url = object.getPosterPath();
            Picasso.with(this).load(pref+url).into(imageView);


            favButton =  (Button) findViewById(R.id.favoriteButt);



    }

    //this is the addFavorite() for the favorite button
    public void addFavorite(View view) {

       /*in this method there are a few steps to follow
            step 1: grab the information that is displayed, the ID, the movieImage, and the name
            step 2: make a contentvalue object to pass theses values to it
            step 3: pass that information to be stored in the MovieDbHelper database
            step 4: finish the activity(this returns back to the mainActivity)
       * */
       String inputPosterImage =  pref + url;
       String inputMovieName =   txtName.getText().toString();

        ContentValues contentValues =  new ContentValues();

        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, inputMovieName);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, inputPosterImage);

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



}
