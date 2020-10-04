package com.danny.filmfan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.danny.filmfan.R;
import com.danny.filmfan.adapters.MovieAdapter;
import com.danny.filmfan.constants.StringConstants;
import com.danny.filmfan.items.MovieItem;
import com.danny.filmfan.utlis.ClientSSLSocketFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recMovieList;
    private ArrayList<MovieItem> movieItems;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recMovieList=(RecyclerView)findViewById(R.id.rec_movie_list);
        progressBar=(ProgressBar)findViewById(R.id.progress_in_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        fetchData();

    }

    private void fetchData(){

        try {
            progressBar.setVisibility(View.VISIBLE);

            movieItems=new ArrayList<>();
            StringRequest stringRequest = new StringRequest(Request.Method.GET,StringConstants.API_MOVIE_LINK,
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
                                    Intent intent=new Intent();

                                    MovieItem movieItem=new MovieItem(O.getInt("id"),
                                            O.getString("title"),
                                            O.getString("poster_path"),
                                            O.getString("release_date"),
                                            O.getString("overview"),
                                            O.getDouble("vote_average"),
                                            false,
                                            O.getJSONArray("genre_ids"),
                                            O.getDouble("popularity")
                                    );

                                    movieAdapter=new MovieAdapter(MainActivity.this,movieItems);
                                    LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
                                    layoutManager.setSmoothScrollbarEnabled(true);
                                    recMovieList.setLayoutManager(layoutManager);
                                    recMovieList.setAdapter(movieAdapter);
                                    ((MovieAdapter)recMovieList.getAdapter()).updateAdapter(movieItem);


                                }
                            } catch (JSONException e) {

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();


                                e.printStackTrace();
                            }catch (Exception e){
                                Toast.makeText(MainActivity.this, StringConstants.ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this, new HurlStack(
                    null, ClientSSLSocketFactory.getSocketFactory(MainActivity.this)));
            requestQueue.add(stringRequest);
        }catch (Exception e){
            Toast.makeText(MainActivity.this, StringConstants.ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.mnu_view_favorites_in_main){
            startActivity(new Intent(this,VIewFavoriteMoviesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}