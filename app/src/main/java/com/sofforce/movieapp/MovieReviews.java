package com.sofforce.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mac on 3/2/18.
 */

public class MovieReviews implements Parcelable {

    private String id;

    private String author;

    private String content;

    private String url;

    private Parcelable mInfo;


    public MovieReviews(String id,
                        String author,
                        String content,
                        String url) {

        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    protected MovieReviews (Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
        mInfo = (Parcelable) in.readValue(Parcelable.class.getClassLoader());


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Parcelable getmInfo() {
        return mInfo;
    }

    public void setmInfo(Parcelable mInfo) {
        this.mInfo = mInfo;
    }

    public static Creator<MovieReviews> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int in) {

        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);



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
