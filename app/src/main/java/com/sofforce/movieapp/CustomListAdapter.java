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

public class CustomListAdapter extends ArrayAdapter<MovieStat> {

    private ArrayList<MovieStat> moviepics;
    private int resource;
    private Context context;

    public CustomListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MovieStat> moviepics) {
        super(context, resource, moviepics);

        this.context = context;
        this.moviepics = moviepics;
        this.resource = resource;

    }


    static class ViewHolder {

        ImageView imageView;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String pref ="http://image.tmdb.org/t/p/w500";

        ViewHolder mviewholder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_view, parent, false);
            convertView.setTag(mviewholder);
        } else {
            mviewholder =(ViewHolder) convertView.getTag();
        }

        MovieStat movieItems = getItem(position);

        mviewholder.imageView = (ImageView) convertView.findViewById(R.id.item);
        Picasso.with(context).load(pref+movieItems.getPoster_path()).into(mviewholder.imageView);

        return convertView;


    }

    @Override
    public int getCount() {
        return moviepics != null?
               moviepics.size():0;
    }

}