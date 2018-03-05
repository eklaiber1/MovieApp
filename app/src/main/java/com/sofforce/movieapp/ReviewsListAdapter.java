package com.sofforce.movieapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mac on 3/3/18.
 */

public class ReviewsListAdapter extends ArrayAdapter<MovieReviews> {

    private ArrayList<MovieReviews> movieReviews;
    private int resource;
    private Context context;

    public ReviewsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MovieReviews> movieReviews) {
        super( context, resource, movieReviews );

        this.movieReviews = movieReviews;
        this.resource = resource;
        this.context = context;
    }

    static class ViewHolder {

        TextView textView;

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = new ViewHolder();

        try {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate( R.layout.reviews_list, parent, false );
                convertView.setTag( mViewHolder );
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            MovieReviews movieRev = getItem(position);

            mViewHolder.textView =  (TextView) convertView.findViewById(R.id.theAuthor );
            mViewHolder.textView =  (TextView) convertView.findViewById( R.id.theContent );



        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return convertView;
    }



        @Override
        public int getCount() {
            return movieReviews != null?
                    movieReviews.size():0;
        }

}


