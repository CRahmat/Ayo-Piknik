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

import com.github.myproject.R;
import com.github.myproject.favorite_database.destination.FavoriteContractDestination;
import com.github.myproject.favorite_database.destination.FavoriteDataDestination;

import java.util.ArrayList;
import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private List<FavoriteDataDestination> favoriteDatumDestinations = new ArrayList<>();
    private FavoriteContractDestination.view repositoryView;
    private FavoriteDataDestination favoriteDelete;
    private CardView favCardView;

    public FavoriteAdapter(Context context, List<FavoriteDataDestination> repositoryResponse, FavoriteContractDestination.view repositoryView) {
        this.context = context;
        this.favoriteDatumDestinations = repositoryResponse;
        this.repositoryView = repositoryView;
    }

    public FavoriteAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_destination_favorite_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, final int position) {
        holder.favCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.placeName.setText(favoriteDatumDestinations.get(position).getName());
            holder.placeAddress.setText(favoriteDatumDestinations.get(position).getVicinity());
            double rating = favoriteDatumDestinations.get(position).getRating();
            String castRating = String.valueOf(rating);
            holder.placeRating.setText("Rating : "+castRating);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteDelete = favoriteDatumDestinations.get(position);
                repositoryView.deleteFavoriteData(favoriteDelete);
            }
        });
            holder.favCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String url = "https://www.google.com/search?q=".concat(favoriteDatumDestinations.get(position).getName());
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            context.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return favoriteDatumDestinations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        TextView placeAddress;
        TextView placeRating;
        ImageView deleteButton;
        CardView favCardView;
        public ViewHolder(@NonNull View view) {
            super(view);
            placeName = view.findViewById(R.id.fav_place_name);
            placeAddress = view.findViewById(R.id.fav_place_address);
            placeRating = view.findViewById(R.id.fav_place_rating);
            deleteButton = view.findViewById(R.id.fav_delete_button);
            favCardView = view.findViewById(R.id.favorite_cv);
        }
    }
}
