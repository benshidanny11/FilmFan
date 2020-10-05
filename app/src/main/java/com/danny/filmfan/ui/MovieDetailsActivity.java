package com.danny.filmfan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.danny.filmfan.R;
import com.danny.filmfan.adapters.ActorAdapter;
import com.danny.filmfan.adapters.MovieAdapter;
import com.danny.filmfan.constants.StringConstants;
import com.danny.filmfan.data.DBHelper;
import com.danny.filmfan.items.ActorItem;
import com.danny.filmfan.items.MovieItem;
import com.danny.filmfan.utlis.ClientSSLSocketFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView txtMovieTitle,txtReleaseDate,txtRating,txtOverView,txtGenre,similarMovies;
    RatingBar ratingBar;
    ImageView imgPoster,imgMore;
    Bundle bundle;
    DBHelper dbHelper;
    MovieItem movieItem;
    RecyclerView recSimilarMovieList,recActorsList;
    ProgressBar progressBar,progressActors;
    ArrayList<MovieItem> movieItems;
    ArrayList<ActorItem> actorItems;
    MovieAdapter movieAdapter;
    ActorAdapter actorAdapter;
    Button btnLoadRecommended;
    RelativeLayout lytSimilarMovies;

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
        ratingBar=(RatingBar)findViewById(R.id.m_rating_in_details);
        recSimilarMovieList=(RecyclerView)findViewById(R.id.rec_similar_movies);
        progressBar=(ProgressBar)findViewById(R.id.progress_in_details);
        lytSimilarMovies=(RelativeLayout)findViewById(R.id.lyt_similar_movie_container);
        recActorsList=(RecyclerView) findViewById(R.id.rec_actors_list);
        imgPoster=(ImageView)findViewById(R.id.img_movie_poster_in_details_activity);
        progressActors=(ProgressBar) findViewById(R.id.progress_actors_in_details);
        btnLoadRecommended=(Button)findViewById(R.id.btn_load_recommended);
        bundle=getIntent().getExtras();
        dbHelper=new DBHelper(this);

        if(bundle!=null){
            txtMovieTitle.setText(bundle.getString("title"));
            txtReleaseDate.setText(bundle.getString("date"));
            txtRating.setText(String.valueOf(bundle.getDouble("rating")));
            txtOverView.setText(bundle.getString("overview"));
            txtGenre.setText(String.valueOf(bundle.getInt("genre")));
            Glide.with(this).load(bundle.getString("poster")).placeholder(R.drawable.ic_baseline_play_arrow_24).into(imgPoster);
            movieItem=(MovieItem)bundle.getSerializable("item");
        }


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rateMove(rating);
            }
        });
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    PopupMenu menu=new PopupMenu(MovieDetailsActivity.this,v);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.mnu_view_favorites_added:

                                    startActivity(new Intent(MovieDetailsActivity.this,VIewFavoriteMoviesActivity.class));
                                    return true;
                                default:
                                    return false;
                            }

                        }
                    });
                    menu.inflate(R.menu.menu_favorite_added);
                    menu.show();


            }
        });

        btnLoadRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytSimilarMovies.setVisibility(View.VISIBLE);
                loadSimilarMovies();
            }
        });

        loadActors();
    }

    private void loadActors() {

        progressActors.setVisibility(View.VISIBLE);

        actorItems=new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.getActorsListAPIURL(getIntent().getExtras().getInt("id")),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressActors.setVisibility(View.GONE);
                        try {
                            JSONArray array = response.getJSONArray("cast");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject O = array.getJSONObject(i);
                                ActorItem actorItem =new ActorItem(O.getString("name"),O.getString("character"),O.getString("profile_path"));

                                actorAdapter=new ActorAdapter(MovieDetailsActivity.this,actorItems);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(MovieDetailsActivity.this);
                                layoutManager.setSmoothScrollbarEnabled(true);
                                recActorsList.setLayoutManager(layoutManager);
                                recActorsList.setAdapter(actorAdapter);
                                ((ActorAdapter)recActorsList.getAdapter()).updateAdapter(actorItem);


                            }
                        } catch (JSONException e) {

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MovieDetailsActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();


                            e.printStackTrace();
                        }catch (Exception e){
                            Toast.makeText(MovieDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                VolleyLog.d("Error", "Error: " + error.getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void loadSimilarMovies() {
        try {
            progressBar.setVisibility(View.VISIBLE);

            movieItems=new ArrayList<>();
            StringRequest stringRequest = new StringRequest(Request.Method.GET,StringConstants.getSimilarMoviesAPILink(bundle.getInt("id")),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);
                            try {
                                JSONObject j = null;
                                j = new JSONObject(response);
                                JSONArray array = j.getJSONArray("results");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject O = array.getJSONObject(i);
                                    MovieItem movieItem;
                                    movieItem =new MovieItem(O.getInt("id"),
                                            O.getString("title"),
                                            O.getString("poster_path"),
                                            O.getString("release_date"),
                                            O.getString("overview"),
                                            O.getDouble("vote_average"),
                                            false,
                                            O.getJSONArray("genre_ids").getInt(0),
                                            O.getDouble("popularity")
                                    );

                                    movieAdapter=new MovieAdapter(MovieDetailsActivity.this,movieItems);
                                    LinearLayoutManager layoutManager=new LinearLayoutManager(MovieDetailsActivity.this);
                                    layoutManager.setSmoothScrollbarEnabled(true);
                                    recSimilarMovieList.setLayoutManager(layoutManager);
                                    recSimilarMovieList.setAdapter(movieAdapter);
                                    ((MovieAdapter)recSimilarMovieList.getAdapter()).updateAdapter(movieItem);


                                }
                            } catch (JSONException e) {

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MovieDetailsActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();


                                e.printStackTrace();
                            }catch (Exception e){
                                Toast.makeText(MovieDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressBar.setVisibility(View.GONE);


                        }
                    });
            // RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            RequestQueue requestQueue = Volley.newRequestQueue(MovieDetailsActivity.this, new HurlStack(
                    null, ClientSSLSocketFactory.getSocketFactory(MovieDetailsActivity.this)));
            requestQueue.add(stringRequest);
        }catch (Exception e){
            Toast.makeText(MovieDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void rateMove(final float rating) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Please wait...");
        loading.setCancelable(false);
        loading.show();
        JSONObject object = new JSONObject();
        try {
            object.put("value",rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, StringConstants.getMovieRatingUrl(getIntent().getExtras().getInt("id")), object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            loading.dismiss();
                            Toast.makeText(MovieDetailsActivity.this, response.getString("status_message"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            loading.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(MovieDetailsActivity.this, "Message "+obj.getString("status_message"), Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                VolleyLog.d("Error", "Error: " + error.getMessage());

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
}