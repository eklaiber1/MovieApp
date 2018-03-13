package com.sofforce.movieapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mac on 3/7/18.
 */

public class VideosListAdapter extends ArrayAdapter<MovieVideos> {

    private ArrayList<MovieVideos> movieVideos;
    private int resource;
    private Context context;

    public VideosListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MovieVideos> movieVideos) {
        super( context, resource, movieVideos );

        this.movieVideos = movieVideos;
        this.resource = resource;
        this.context = context;
    }

    static class ViewHolder {

        TextView textName;
        ImageView videoContent;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = new VideosListAdapter.ViewHolder();

        try {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService( Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate( R.layout.videos_list, parent, false );
                convertView.setTag( mViewHolder );
            } else {
                mViewHolder = (VideosListAdapter.ViewHolder) convertView.getTag();
            }

            MovieVideos movieVid = getItem(position);
            String videoUrl = "http://i.ytimg.com/vi/" + movieVid.getKey()+ "/hqdefault.jpg" ;


            mViewHolder.textName =  (TextView) convertView.findViewById(R.id.nameOfVideo );
            mViewHolder.videoContent =  (ImageView) convertView.findViewById( R.id.videos );

            mViewHolder.textName.setText( movieVid.getName() );
            Picasso.with(getContext()).load(videoUrl).into(mViewHolder.videoContent);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return convertView;
    }



    @Override
    public int getCount() {
        return movieVideos != null?
                movieVideos.size():0;
    }
}
