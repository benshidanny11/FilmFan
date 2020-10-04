package com.danny.filmfan.utlis;


import com.danny.filmfan.items.MovieItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ComparatorUtil implements Comparator<MovieItem> {

    @Override
    public int compare(MovieItem m1, MovieItem m2) {

            String movieName1 = m1.getMovieTitle();
            String movieName2=m2.getMovieTitle();
            if (movieName1.compareToIgnoreCase(movieName2) > 0){
                return 1;
            }else if (movieName1.compareToIgnoreCase(movieName2) < 0){
                return -1;
            }
        return 0;
    }
}
