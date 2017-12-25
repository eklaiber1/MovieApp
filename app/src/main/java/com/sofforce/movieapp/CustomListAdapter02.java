package com.sofforce.movieapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mac on 12/24/17.
 */

public class CustomListAdapter02 extends ArrayAdapter<MovieStat>{

    private ArrayList<MovieStat> moviepics;
    private int resource;
    private Context context;

    public CustomListAdapter02(@NonNull Context context, int resource, @NonNull ArrayList<MovieStat> moviepics) {
        super(context, resource, moviepics);

        this.context = context;
        this.moviepics = moviepics;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.movie_list, null, true);

        }

        MovieStat movieItems = getItem(position);


//        TextView txtname = (TextView) convertView.findViewById(R.id.marquee_title);
//        txtname.setText(movieItems.getTitle());
//
//        TextView txtyear = (TextView) convertView.findViewById(R.id.movie_year);
//        txtyear.setText(movieItems.getRelease_date());

//        TextView txtrating = (TextView) convertView.findViewById(R.id.movie_rating);
//        txtrating.setText(movieItems.getVote_average());

        //     TextView txttime = (TextView) convertView.findViewById(R.id.movie_duration);
        //     txttime.setText(movieItems.getTitle());

//        TextView txtoverview = (TextView) convertView.findViewById(R.id.movie_overview);
//        txtoverview.setText(movieItems.getOverview());


        ImageView imageView = (ImageView) convertView.findViewById(R.id.movieListgrid);
        Picasso.with(context).load(movieItems.getPoster_path()).into(imageView);

        return convertView;

    }
}
