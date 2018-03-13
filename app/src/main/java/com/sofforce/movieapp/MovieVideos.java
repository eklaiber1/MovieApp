package com.sofforce.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mac on 3/7/18.
 */

public class MovieVideos implements Parcelable {


    private String id;

    private String key;

    private String name;

    private Parcelable mInfo;


    public MovieVideos(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    protected MovieVideos (Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        mInfo = (Parcelable) in.readValue(Parcelable.class.getClassLoader());

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parcelable getmInfo() {
        return mInfo;
    }

    public void setmInfo(Parcelable mInfo) {
        this.mInfo = mInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int in) {

        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);

    }


    public static final Parcelable.Creator<MovieReviews> CREATOR
            = new Parcelable.Creator<MovieReviews>() {

        //Create a new instance of the Parcelable class, instantiating it from the given Parcel
        // whose data had previously been written by Parcelable.writeToParcel().
        @Override
        public MovieReviews createFromParcel(Parcel in) {return new MovieReviews(in);}

        //Create a new array of the Parcelable class
        @Override
        public MovieReviews[] newArray(int size) {return new MovieReviews[size];}
    };




}



