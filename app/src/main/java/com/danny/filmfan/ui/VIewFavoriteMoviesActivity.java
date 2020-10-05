package com.danny.filmfan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.danny.filmfan.R;
import com.danny.filmfan.adapters.MovieAdapter;
import com.danny.filmfan.data.DBHelper;
import com.danny.filmfan.items.MovieItem;
import com.danny.filmfan.utlis.FagoriteMovieUtil;

import java.util.ArrayList;

public class VIewFavoriteMoviesActivity extends AppCompatActivity {

    private RecyclerView recFavoriteMovieList;
    private MovieAdapter movieAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_iew_favorite_movies);
        recFavoriteMovieList=(RecyclerView)findViewById(R.id.rec_favorite_movies_list);
         dbHelper=new DBHelper(this);
        viewFavorites();
    }

    private void viewFavorites() {
        movieAdapter=new MovieAdapter(this,dbHelper.getFavoriteMovies());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        recFavoriteMovieList.setLayoutManager(layoutManager);
        recFavoriteMovieList.setAdapter(movieAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}