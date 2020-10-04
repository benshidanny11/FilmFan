package com.danny.filmfan.utlis;

import com.danny.filmfan.items.MovieItem;

import java.util.ArrayList;
import java.util.Collections;

public class FagoriteMovieUtil {

    ArrayList<MovieItem> favoriteMovies;

    public FagoriteMovieUtil() {
        favoriteMovies=new ArrayList<>();
    }

    public  void addFavoriteMovie(MovieItem item){
        favoriteMovies.add(item);
        Collections.sort(favoriteMovies, new ComparatorUtil());
    }

    public ArrayList<MovieItem> getAllFavoriteMovies(){
        return favoriteMovies;
    }
}
