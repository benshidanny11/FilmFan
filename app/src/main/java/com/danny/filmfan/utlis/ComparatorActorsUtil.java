package com.danny.filmfan.utlis;


import com.danny.filmfan.items.ActorItem;
import com.danny.filmfan.items.MovieItem;

import java.util.Comparator;

public class ComparatorActorsUtil implements Comparator<ActorItem> {

    @Override
    public int compare(ActorItem m1, ActorItem m2) {

            String movieName1 = m1.getActorName();
            String movieName2=m2.getActorName();
            if (movieName1.compareToIgnoreCase(movieName2) > 0){
                return 1;
            }else if (movieName1.compareToIgnoreCase(movieName2) < 0){
                return -1;
            }
        return 0;
    }
}
