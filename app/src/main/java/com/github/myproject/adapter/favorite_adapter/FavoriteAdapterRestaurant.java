package com.github.myproject.adapter.favorite_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.myproject.R;
import com.github.myproject.favorite_database.restaurant.FavoriteContractRestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDataRestaurant;

import java.util.ArrayList;
import java.util.List;


public class FavoriteAdapterRestaurant extends RecyclerView.Adapter<FavoriteAdapterRestaurant.ViewHolder> {
    private Context context;
    private List<FavoriteDataRestaurant> favoriteDataRestaurantList = new ArrayList<>();
    private FavoriteContractRestaurant.view repositoryView;
    private FavoriteDataRestaurant favoriteDataRestaurant;
    private CardView favCardView;

    public FavoriteAdapterRestaurant(Context context, List<FavoriteDataRestaurant> repositoryResponse, FavoriteContractRestaurant.view repositoryView) {
        this.context = context;
        this.favoriteDataRestaurantList = repositoryResponse;
        this.repositoryView = repositoryView;
    }

    public FavoriteAdapterRestaurant(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteAdapterRestaurant.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_destination_favorite_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapterRestaurant.ViewHolder holder, final int position) {
        holder.favCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.placeName.setText(favoriteDataRestaurantList.get(position).getName());
        holder.placeAddress.setText(favoriteDataRestaurantList.get(position).getVicinity());
        String photoReference = favoriteDataRestaurantList.get(position).getImage();
        Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().placeholder(R.drawable.special_loading).into(holder.placeImage);
        double rating = favoriteDataRestaurantList.get(position).getRating();
        String castRating = String.valueOf(rating);
        holder.placeRating.setText("Rating : " + castRating);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteDataRestaurant = favoriteDataRestaurantList.get(position);
                repositoryView.deleteFavoriteDataRestaurant(favoriteDataRestaurant);
            }
        });
        holder.favCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "https://www.google.com/search?q=".concat(favoriteDataRestaurantList.get(position).getName());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteDataRestaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        TextView placeAddress;
        TextView placeRating;
        ImageView deleteButton;
        CardView favCardView;
        ImageView placeImage;

        public ViewHolder(@NonNull View view) {
            super(view);
            placeName = view.findViewById(R.id.fav_place_name);
            placeAddress = view.findViewById(R.id.fav_place_address);
            placeRating = view.findViewById(R.id.fav_place_rating);
            deleteButton = view.findViewById(R.id.fav_delete_button);
            favCardView = view.findViewById(R.id.favorite_cv);
            placeImage = view.findViewById(R.id.fav_image_place);
        }
    }
}
