package com.danny.filmfan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.danny.filmfan.R;
import com.danny.filmfan.constants.StringConstants;
import com.danny.filmfan.items.ActorItem;
import com.danny.filmfan.items.MovieItem;
import com.danny.filmfan.utlis.ComparatorActorsUtil;
import com.danny.filmfan.utlis.ComparatorUtil;

import java.util.ArrayList;
import java.util.Collections;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorHolder> {

    Context context;
    ArrayList<ActorItem> actorItems;

    public ActorAdapter(Context context, ArrayList<ActorItem> actorItems) {
        this.context = context;
        this.actorItems = actorItems;
    }

    @NonNull
    @Override
    public ActorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actors,parent,false);
        return new ActorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorHolder holder, int position) {
        holder.txtActorName.setText(actorItems.get(position).getActorName());
        holder.txtActorRole.setText(actorItems.get(position).getActorRole());
        Glide.with(context).load(StringConstants.API_IMG_LINK+actorItems.get(position).getActorImageUrl()).placeholder(R.drawable.ic_baseline_account_box_24).into(holder.imgActorImage);
    }

    @Override
    public int getItemCount() {
        return actorItems.size();
    }

    public void updateAdapter(ActorItem actorItem){
        actorItems.add(actorItem);
        Collections.sort(actorItems, new ComparatorActorsUtil());
        notifyDataSetChanged();
    }

    class ActorHolder extends RecyclerView.ViewHolder{

        TextView txtActorName,txtActorRole;
        ImageView imgActorImage;
        public ActorHolder(@NonNull View itemView) {
            super(itemView);
            txtActorName=(TextView)itemView.findViewById(R.id.txt_actor_name_in_item);
            txtActorRole=(TextView)itemView.findViewById(R.id.txt_actor_role_in_item);
            imgActorImage=(ImageView)itemView.findViewById(R.id.img_actor_in_item);
        }
    }
}
