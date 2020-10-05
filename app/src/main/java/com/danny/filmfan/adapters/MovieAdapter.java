package com.danny.filmfan.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.danny.filmfan.R;
import com.danny.filmfan.constants.StringConstants;
import com.danny.filmfan.data.DBHelper;
import com.danny.filmfan.items.MovieItem;
import com.danny.filmfan.ui.MovieDetailsActivity;
import com.danny.filmfan.utlis.ComparatorUtil;
import com.danny.filmfan.utlis.FagoriteMovieUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private Context context;
    private ArrayList<MovieItem> movieItems;
    private DBHelper dbHelper;


    public MovieAdapter(Context context, ArrayList<MovieItem> movieItems) {
        this.context = context;
        this.movieItems = movieItems;

    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        dbHelper=new DBHelper(context);
        return new MovieHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, int position) {

        final MovieItem movieItem=movieItems.get(position);
        FagoriteMovieUtil fagoriteMovieUtil = new FagoriteMovieUtil();

        if (dbHelper.getMovieTitle(movieItem.getMovieId()).equals(movieItem.getMovieTitle())){
            holder.btnAddToFaforite.setVisibility(View.GONE);
            holder.imgAddedToFavorite.setVisibility(View.VISIBLE);
            holder.txtMovieTitle.setText(movieItem.getMovieTitle());
            holder.txtMediaType.setText(movieItem.getMovieOverview());
            holder.txtMovieRealeseDate.setText("Released on "+movieItem.getReleaseDate());
            holder.txtMovieVotes.setText(String.valueOf(movieItem.getAverageVote()));
            Glide.with(context).load(StringConstants.API_IMG_LINK+movieItem.getMoviePoster()).placeholder(R.drawable.ic_baseline_play_arrow_24).into(holder.imgMoviePoster);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("id",movieItem.getMovieId());
                    intent.putExtra("title",movieItem.getMovieTitle());
                    intent.putExtra("date",movieItem.getReleaseDate());
                    intent.putExtra("rating",movieItem.getAverageVote());
                    intent.putExtra("overview",movieItem.getMovieOverview());
                    intent.putExtra("genre",movieItem.getGenre());
                    intent.putExtra("poster",StringConstants.API_IMG_LINK+movieItem.getMoviePoster());
                    intent.putExtra("item",movieItem);
                    context.startActivity(intent);

                }
            });
        }else{

            holder.btnAddToFaforite.setVisibility(View.VISIBLE);
            holder.imgAddedToFavorite.setVisibility(View.GONE);
            holder.txtMovieTitle.setText(movieItem.getMovieTitle());
            holder.txtMediaType.setText(movieItem.getMovieOverview());
            holder.txtMovieRealeseDate.setText("Released on "+movieItem.getReleaseDate());
            holder.txtMovieVotes.setText(String.valueOf(movieItem.getAverageVote()));
            Glide.with(context).load(StringConstants.API_IMG_LINK+movieItem.getMoviePoster()).placeholder(R.drawable.ic_baseline_play_arrow_24).into(holder.imgMoviePoster);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("title",movieItem.getMovieTitle());
                    intent.putExtra("id",movieItem.getMovieId());
                    intent.putExtra("date",movieItem.getReleaseDate());
                    intent.putExtra("rating",movieItem.getAverageVote());
                    intent.putExtra("overview",movieItem.getMovieOverview());
                    intent.putExtra("genre",movieItem.getGenre());
                    intent.putExtra("poster",StringConstants.API_IMG_LINK+movieItem.getMoviePoster());

                    context.startActivity(intent);

                }
            });
            holder.btnAddToFaforite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHelper.addToFaforite(movieItem);
                    holder.btnAddToFaforite.setVisibility(View.GONE);
                    holder.imgAddedToFavorite.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public void updateAdapter(MovieItem movieItem){
        movieItems.add(movieItem);
        Collections.sort(movieItems, new ComparatorUtil());
        notifyDataSetChanged();
    }

    class MovieHolder extends RecyclerView.ViewHolder{
        TextView txtMovieTitle,txtMediaType,txtMovieRealeseDate,txtMovieVotes;
        Button btnAddToFaforite;
        ImageView imgMoviePoster,imgAddedToFavorite;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieTitle=(TextView)itemView.findViewById(R.id.txt_movie_title_in_item);
            txtMediaType=(TextView)itemView.findViewById(R.id.txt_media_type_in_item);
            txtMovieRealeseDate=(TextView)itemView.findViewById(R.id.txt_movie_release_date_in_item);
            txtMovieVotes=(TextView)itemView.findViewById(R.id.txt_movie_votes_in_item);
            btnAddToFaforite=(Button)itemView.findViewById(R.id.btn_add_to_favorite);
            imgMoviePoster=(ImageView)itemView.findViewById(R.id.img_movie_poster_in_item);
            imgAddedToFavorite=(ImageView)itemView.findViewById(R.id.img_added_to_favorite);
        }
    }
}
