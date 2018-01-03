package com.sofforce.movieapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mac on 12/28/17.
 */

public class DetailedActivity extends AppCompatActivity {


    TextView txtname;
    TextView txtrating;
    TextView txtoverview;
    TextView txtyear;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview);

        String pref ="http://image.tmdb.org/t/p/w342";
        Context context;

        TextView txtname = (TextView) findViewById(R.id.marquee_title);
        TextView txtrating = (TextView) findViewById(R.id.movie_rating);
        TextView txtoverview = (TextView) findViewById(R.id.movie_overview);
        TextView txtyear = (TextView) findViewById(R.id.movie_year);
        ImageView imageView = (ImageView) findViewById(R.id.grid_item_image);

        Bundle mbundle =  getIntent().getExtras();
        if (mbundle != null) {
            txtname.setText(mbundle.getString("title"));
            txtrating.setText(mbundle.getString("vote_average"));
            txtoverview.setText(mbundle.getString("overview"));
            txtyear.setText(mbundle.getString("release_date"));
            String url = mbundle.getString("poster_path");
            Picasso.load(url).into(imageView);

        }





    }





}
