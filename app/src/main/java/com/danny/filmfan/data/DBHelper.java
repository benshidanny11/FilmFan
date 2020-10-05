package com.danny.filmfan.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.danny.filmfan.items.MovieItem;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    Context mContext;

    public DBHelper(Context context) {

        super(context, "favorite_movies.db", null, 1);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS movies("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "'movieId' TEXT, 'movieTitle' TEXT, 'moviePoster' TEXT, 'releaseDate' TEXT," +
                    "'movieOverview' TEXT,'averageVote' DOUBLE,'genre' INTEGER,'rating' INTEGER);");
        } catch (Exception ex) {
            Toast.makeText(mContext,
                    "Error on create: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            db.execSQL("DROP TABLE IF EXISTS movies;");
        } catch (Exception ex) {
            Toast.makeText(mContext,
                    "Error on upgrade: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<MovieItem> getFavoriteMovies() {
        ArrayList<MovieItem> movieItems = new ArrayList<MovieItem>();
        SQLiteDatabase sqldg = this.getReadableDatabase();
        Cursor cur = sqldg.rawQuery("select * from movies" , null);
        if (cur != null) {

            while (cur.moveToNext()) {
                MovieItem movieItem = new MovieItem(cur.getInt(1),cur.getString(2),cur.getString(3),cur.getString(4),
                        cur.getString(5),cur.getDouble(6),true,cur.getInt(7),cur.getInt(8));
                movieItems.add(movieItem);
            }
        }
        return movieItems;
    }
    public long addToFaforite(MovieItem movieItem){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("movieId", movieItem.getMovieId());
        values.put("movieTitle", movieItem.getMovieTitle());
        values.put("moviePoster",movieItem.getMoviePoster());
        values.put("releaseDate",movieItem.getReleaseDate());
        values.put("movieOverview", movieItem.getMovieOverview());
        values.put("averageVote", movieItem.getAverageVote());
        values.put("genre", movieItem.getGenre());
        values.put("rating", movieItem.getRating());

        return db.insert("movies", null, values);
    }
    public boolean isMovieAddedInFaforite(int movieId){
        SQLiteDatabase sqldg = this.getReadableDatabase();
        boolean isUserExist=false;
        Cursor cur = sqldg.rawQuery("select movieId from movies where movieId="+movieId, null);
        if (cur != null && cur.moveToNext()) {
            if(cur.getInt(0)==movieId){
                isUserExist=true;
            }
        }
        return isUserExist;
    }
    public String getMovieTitle(int movieId){
        SQLiteDatabase sqldg = this.getReadableDatabase();
        String movieTitle="";
        Cursor cur = sqldg.rawQuery("select movieTitle from movies where movieId="+movieId, null);
        if (cur != null && cur.moveToNext()) {

                movieTitle=cur.getString(0);

        }
        return movieTitle;
    }




}
