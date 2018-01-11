package com.sofforce.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mac on 12/28/17.
 */

public class DetailedActivity extends AppCompatActivity {


    TextView txtName;
    TextView txtRating;
    TextView txtOverview;
    TextView txtYear;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview);

        String pref ="http://image.tmdb.org/t/p/w342";

         txtName = (TextView) findViewById(R.id.marquee_title);
         txtRating = (TextView) findViewById(R.id.movie_rating);
         txtOverview = (TextView) findViewById(R.id.movie_overview);
         txtYear = (TextView) findViewById(R.id.movie_year);
         imageView = (ImageView) findViewById(R.id.grid_item_image);

        Bundle mbundle =  getIntent().getExtras();
        if (mbundle != null) {
            txtName.setText(mbundle.getString("title"));
            txtRating.setText(mbundle.getString("vote_average"));
            txtOverview.setText(mbundle.getString("overview"));
            txtYear.setText(mbundle.getString("release_date"));
            String url = mbundle.getString("poster_path");
            Picasso.with(this).load(pref+url).into(imageView);



        }




    }





}
