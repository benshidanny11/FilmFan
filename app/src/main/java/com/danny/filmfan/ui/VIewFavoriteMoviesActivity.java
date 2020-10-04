package com.danny.filmfan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.danny.filmfan.R;
import com.danny.filmfan.adapters.MovieAdapter;
import com.danny.filmfan.items.MovieItem;
import com.danny.filmfan.utlis.FagoriteMovieUtil;

import java.util.ArrayList;

public class VIewFavoriteMoviesActivity extends AppCompatActivity {

    private RecyclerView recFavoriteMovieList;
    private MovieAdapter movieAdapter;
    FagoriteMovieUtil fagoriteMovieUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_iew_favorite_movies);
        recFavoriteMovieList=(RecyclerView)findViewById(R.id.rec_favorite_movies_list);
        fagoriteMovieUtil=new FagoriteMovieUtil();
        Toast.makeText(this, "List count: "+fagoriteMovieUtil.getAllFavoriteMovies().size(), Toast.LENGTH_SHORT).show();
        viewFavorites();
    }

    private void viewFavorites() {
        movieAdapter=new MovieAdapter(this,fagoriteMovieUtil.getAllFavoriteMovies());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        recFavoriteMovieList.setLayoutManager(layoutManager);
        recFavoriteMovieList.setAdapter(movieAdapter);
    }
}