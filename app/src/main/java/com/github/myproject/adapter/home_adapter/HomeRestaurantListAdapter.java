package com.github.myproject.adapter.home_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.myproject.R;
import com.github.myproject.favorite_database.restaurant.FavoriteDAORestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDataRestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDatabaseRestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteOperationRestaurant;
import com.github.myproject.model.PhotosItem;
import com.github.myproject.model.PlacesResultsItem;
import com.github.myproject.view.fragment.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeRestaurantListAdapter extends RecyclerView.Adapter<HomeRestaurantListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PlacesResultsItem> modelRestaurant = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelRestaurantFiltered = new ArrayList<>();
    private List<PhotosItem> restaurantPhotosItem = new ArrayList<>();
    private FavoriteOperationRestaurant favoriteOperationRestaurant;
    private FavoriteDatabaseRestaurant favoriteDatabaseRestaurant;
    private List<FavoriteDataRestaurant> favoriteDatumRestaurants = new ArrayList<>();
    private FavoriteDAORestaurant favoriteDAORestaurant;

    public HomeRestaurantListAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<PlacesResultsItem> items) {
        modelRestaurant.clear();
        modelRestaurant.addAll(items);
        notifyDataSetChanged();

        modelRestaurantFiltered.clear();
        modelRestaurantFiltered.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeRestaurantListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_restaurant_design, parent, false);
        return new HomeRestaurantListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeRestaurantListAdapter.ViewHolder holder, final int position) {
        try {
            final FavoriteOperationRestaurant favoriteOperationRestaurant = new FavoriteOperationRestaurant(context);
            //Check Favorite Data
            favoriteDatabaseRestaurant = FavoriteDatabaseRestaurant.database(context);
            favoriteDAORestaurant = favoriteDatabaseRestaurant.favoriteDAORestaurant();
            favoriteDatumRestaurants = favoriteDatabaseRestaurant.favoriteDAORestaurant().getFavorite();
            int favSize = favoriteDatumRestaurants.size();
            if (favoriteDatumRestaurants.size() == 0) {
            } else {
                for (int i = 0; i < favSize; i++) {
                    if (modelRestaurantFiltered.get(position).getName().equals(favoriteDatumRestaurants.get(i).getName()) && modelRestaurantFiltered.get(position).getVicinity().equals(favoriteDatumRestaurants.get(i).getVicinity())) {
                        holder.favooriteButton.setImageResource(R.drawable.ic_favorite_red);
                    }
                }
            }

            holder.tvRestaurantName.setText(modelRestaurantFiltered.get(position).getName());
//        Tidak Digunakan, Jika ingin digunakan ubah ukuran pada xml code
            holder.tvRestaurantAddress.setText(modelRestaurantFiltered.get(position).getVicinity());
            restaurantPhotosItem = modelRestaurantFiltered.get(position).getPhotos();
            int maxWidth = restaurantPhotosItem.get(0).getWidth();
            String photoReference = restaurantPhotosItem.get(0).getPhotoReference();
            Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + maxWidth + "&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().placeholder(R.drawable.special_loading).into(holder.containerLeft);
            holder.photosItems.setText(photoReference);
            if (modelRestaurantFiltered.get(position).getOpeningHours().isOpenNow())
                holder.tvRestaurantOpenStatus.setText("Open");
            else holder.tvRestaurantOpenStatus.setText("Closed");

            double rating = modelRestaurantFiltered.get(position).getRating();
            if (Double.toString(rating) == "") holder.tvRestaurantRating.setText("-");
            else holder.tvRestaurantRating.setText(Double.toString(rating));

            holder.restaurantCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
            holder.containerLeft.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

            final String url = "https://www.google.com/search?q=".concat(modelRestaurantFiltered.get(position).getName());

            holder.restaurantCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
            });
            holder.favooriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile profile = new Profile();
                    if (profile.aBoolean == true) {
                        favoriteDatabaseRestaurant = FavoriteDatabaseRestaurant.database(context);
                        favoriteDAORestaurant = favoriteDatabaseRestaurant.favoriteDAORestaurant();
                        favoriteDatumRestaurants = favoriteDatabaseRestaurant.favoriteDAORestaurant().getFavorite();
                        String placeName = holder.tvRestaurantName.getText().toString();
                        String placeAddress = holder.tvRestaurantAddress.getText().toString();
                        int allData = favoriteDatumRestaurants.size();
                        Boolean result = false;
                        if (allData != 0) {
                            for (int i = 0; i < allData; i++) {
                                if (placeName.equals(favoriteDatumRestaurants.get(i).getName()) && placeAddress.equals(favoriteDatumRestaurants.get(i).getVicinity())) {
                                    Toast.makeText(context, holder.tvRestaurantName.getText().toString() + "Already in Favorite", Toast.LENGTH_SHORT).show();
                                    result = true;
                                    break;
                                }
                            }
                            if (result == false) {
                                favoriteOperationRestaurant.insertFavoriteDataRestaurant(
                                        "restaurant",
                                        holder.tvRestaurantName.getText().toString(),
                                        holder.tvRestaurantAddress.getText().toString(),
                                        Double.valueOf(holder.tvRestaurantRating.getText().toString()),
                                        holder.photosItems.getText().toString(),
                                        FavoriteDatabaseRestaurant.database(context)
                                );
                                holder.favooriteButton.setImageResource(R.drawable.ic_favorite_red);
                            }
                        } else {
                            favoriteOperationRestaurant.insertFavoriteDataRestaurant(
                                    "restaurant",
                                    holder.tvRestaurantName.getText().toString(),
                                    holder.tvRestaurantAddress.getText().toString(),
                                    Double.valueOf(holder.tvRestaurantRating.getText().toString()),
                                    holder.photosItems.getText().toString(),
                                    FavoriteDatabaseRestaurant.database(context)
                            );
                            holder.favooriteButton.setImageResource(R.drawable.ic_favorite_red);
                        }

                    } else {
                        Toast.makeText(context, "Please, login now and try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return modelRestaurantFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRestaurantName, tvRestaurantOpenStatus, tvRestaurantRating, tvRestaurantAddress;
        private CardView restaurantCardView;
        private ImageView containerLeft, favooriteButton;
        private TextView photosItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRestaurantName = itemView.findViewById(R.id.home_place_name);
            tvRestaurantRating = itemView.findViewById(R.id.home_place_rating);
            tvRestaurantOpenStatus = itemView.findViewById(R.id.home_place_status);
            tvRestaurantAddress = itemView.findViewById(R.id.home_restaurant_address);
            containerLeft = itemView.findViewById(R.id.home_place_image);
            photosItems = itemView.findViewById(R.id.home_place_photo_reference);
            favooriteButton = itemView.findViewById(R.id.restaurant_fav_button);
            restaurantCardView = itemView.findViewById(R.id.home_cv_content_restaurant);

        }
    }
}
