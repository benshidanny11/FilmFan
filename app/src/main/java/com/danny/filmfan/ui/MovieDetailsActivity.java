package com.danny.filmfan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danny.filmfan.R;

import org.json.JSONArray;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView txtMovieTitle,txtReleaseDate,txtRating,txtOverView,txtGenre;
    ImageView imgPoster,imgMore;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        txtMovieTitle=(TextView)findViewById(R.id.txt_movie_title_in_details);
        txtReleaseDate=(TextView)findViewById(R.id.txt_movie_release_date_details);
        txtRating=(TextView)findViewById(R.id.txt_movie_votes_in_details);
        txtOverView=(TextView)findViewById(R.id.txt_over_view);
        imgMore=(ImageView)findViewById(R.id.img_more_in_details);
        txtGenre=(TextView)findViewById(R.id.txt_genre_in_details);
        imgPoster=(ImageView)findViewById(R.id.img_movie_poster_in_details_activity);
        bundle=getIntent().getExtras();

        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(MovieDetailsActivity.this,v);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.mnu_add_favorite:

                                return true;
                            case R.id.mnu_view_favorites:

                                return true;
                            default:
                                return false;
                        }

                    }
                });
                menu.inflate(R.menu.mnu_favorite_options);
                menu.show();
            }
        });
        if(bundle!=null){
              txtMovieTitle.setText(bundle.getString("title"));
              txtReleaseDate.setText(bundle.getString("date"));
              txtRating.setText(String.valueOf(bundle.getDouble("rating")));
               txtOverView.setText(bundle.getString("overview"));
              txtGenre.setText(String.valueOf(bundle.getInt("genre")));
            Glide.with(this).load(bundle.getString("poster")).placeholder(R.drawable.ic_baseline_play_arrow_24).into(imgPoster);
        }

    }
}