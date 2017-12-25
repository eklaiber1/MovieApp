package com.sofforce.movieapp;

/**
 * Created by mac on 12/18/17.
 */

public class MovieStat {

    //this class is going to be the model viewer for the json object to parse


    //this is the name of the movie
    private String title;

    //this is going to be for "most popular"
    private Double popularity;

    //this is the poster image of the movie
    private String poster_path;

    //this is the poster backdrop
    private String backdrop_path;

    //this is the summary of the movie
    private String overview;

    //this is going to be for "top rated"
    private Double vote_count;

    //this is the release date
    private  String release_date;

    //this is the vote average
    private Double vote_average;


    public MovieStat(String poster_path) {


        this.poster_path = poster_path;

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



    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }



    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }



    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }



    public Double getVote_count() {
        return vote_count;
    }

    public void setVote_count(Double vote_count) {
        this.vote_count = vote_count;
    }



    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }



    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }


}
