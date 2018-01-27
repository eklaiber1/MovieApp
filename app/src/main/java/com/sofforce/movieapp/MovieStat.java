package com.sofforce.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mac on 12/18/17.
 */

public class MovieStat implements Parcelable {

    //this class is going to be the MVC model viewer controller for the json object to parse


    //this is the name of the movie
    private String title;

    //this is going to be for "most popular"
    private Double popularity;

    //this is the poster image of the movie
    private String posterPath;

    //this is the poster backdrop
    private String backdropPath;

    //this is the summary of the movie
    private String overview;

    //this is going to be for "top rated"
    private Double voteCount;

    //this is the release date
    private  String releaseDate;

    //this is the vote average
    private Double voteAverage;

    //this is the Id number of the movie
    private int idNumber;

    //this is the Parcelable object needed for the procedure
    private Parcelable mInfo;



    public MovieStat(String title,
                     String posterPath,
                     Double popularity,
                     String backdropPath,
                     Double voteCount,
                     String releaseDate,
                     Double voteAverage,
                     String overview,
                     int idNumber) {

        this.title = title;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.backdropPath = backdropPath;
        this.voteCount = voteCount;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.idNumber = idNumber;


    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }



    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }



    public Double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Double voteCount) {
        this.voteCount = voteCount;
    }



    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }



    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }



    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }



    public int getIdNumber() { return idNumber;}

    public void setIdNumber(int idNumber) { this.idNumber = idNumber;}


    // these are all the implementations for the interface Parcelable
    // the override methods are all from the Parcelable class


    // these values are the values that i want to be saved to the parcel
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeDouble(popularity);
        parcel.writeString(backdropPath);
        parcel.writeDouble(voteCount);
        parcel.writeString(releaseDate);
        parcel.writeDouble(voteAverage);
        parcel.writeString(overview);
        parcel.writeInt(idNumber);
        parcel.writeParcelable(mInfo, flags);

    }

    //Using in variable we can retrieve the values that we orginally wrote to the Parcel
    private MovieStat(Parcel in) {
        title =  in.readString();
        posterPath = in.readString();
        popularity = in.readByte() == 0x00 ? null : in.readDouble();
        backdropPath = in.readString();
        voteCount = in.readByte() == 0x00 ? null : in.readDouble();
        releaseDate = in.readString();
        voteAverage = in.readByte() == 0x00 ? null : in.readDouble();
        overview = in.readString();
        idNumber = in.readInt();
        mInfo = (Parcelable) in.readValue(Parcelable.class.getClassLoader());
    }


    //Describe the kinds of special objects contained in this Parcelable instance's marshaled representation
    @Override
    public int describeContents() {
        return 0;
    }


    //the public CREATOR field generates instances of your Parcelable class from a Parcel.
    public static final Parcelable.Creator<MovieStat> CREATOR
            = new Parcelable.Creator<MovieStat>() {

        //Create a new instance of the Parcelable class, instantiating it from the given Parcel
        // whose data had previously been written by Parcelable.writeToParcel().
        @Override
        public MovieStat createFromParcel(Parcel in) {
            return new MovieStat(in);
        }

        //Create a new array of the Parcelable class
        @Override
        public MovieStat[] newArray(int size) {
            return new MovieStat[size];
        }
    };

}
