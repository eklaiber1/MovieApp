package com.sofforce.movieapp;

/**
 * Created by mac on 12/18/17.
 */

public class MovieStat {

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
}
