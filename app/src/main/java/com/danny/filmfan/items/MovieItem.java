package com.danny.filmfan.items;

import org.json.JSONArray;

import java.io.Serializable;

public class MovieItem implements Serializable {

    int movieId;
    String movieTitle,moviePoster,releaseDate,movieOverview;
    double averageVote;
    boolean isAddedToFavorite;
    int genre;
    double rating;


    public MovieItem(int movieId, String movieTitle, String moviePoster, String releaseDate,  String movieOverview, double averageVote, boolean isAddedToFavorite, int genre, double rating) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.releaseDate = releaseDate;
        this.movieOverview = movieOverview;
        this.averageVote = averageVote;
        this.isAddedToFavorite = isAddedToFavorite;
        this.genre = genre;
        this.rating = rating;
    }


    public void setAddedToFavorite(boolean addedToFavorite) {
        isAddedToFavorite = addedToFavorite;
    }

    public boolean isAddedToFavorite() {
        return isAddedToFavorite;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public int getGenre() {
        return genre;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getRating() {
        return rating;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getAverageVote() {
        return averageVote;
    }
}
