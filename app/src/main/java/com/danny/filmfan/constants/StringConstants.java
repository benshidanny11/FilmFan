package com.danny.filmfan.constants;

public class StringConstants {
    public static final String ERROR_MESSAGE="Unknown error!";
    public static final String TOKEN="967ba4ba5c3bb2ca2e0c38c50af20bb324bac036";
    public static final String API_IMG_LINK="https://image.tmdb.org/t/p/original";
    public static String API_MOVIE_LINK="https://api.themoviedb.org/3/movie/now_playing?api_key=4f3f3b9e17bbb737bec032243589842e&page=1";

    public static String getMovieRatingUrl(int movieId){
        return "https://api.themoviedb.org/3/movie/"+movieId+"/rating?api_key=4f3f3b9e17bbb737bec032243589842e&session_id=81d36d0fefb76297fcd9ab8adad0c0379e96a408";
    }
    public static String getSimilarMoviesAPILink(int movieId){
        return "https://api.themoviedb.org/3/movie/"+movieId+"/similar?api_key=4f3f3b9e17bbb737bec032243589842e&language=en-US&page=1";
    }

    public static String getActorsListAPIURL(int movieId){
        return "https://api.themoviedb.org/3/movie/"+movieId+"/credits?api_key=4f3f3b9e17bbb737bec032243589842e";
    }
}
