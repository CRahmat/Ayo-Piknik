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
import com.github.myproject.favorite_database.hotels.FavoriteDAOHotel;
import com.github.myproject.favorite_database.hotels.FavoriteDataHotel;
import com.github.myproject.favorite_database.hotels.FavoriteDatabaseHotel;
import com.github.myproject.favorite_database.hotels.FavoriteOperationHotel;
import com.github.myproject.model.PhotosItem;
import com.github.myproject.model.PlacesResultsItem;
import com.github.myproject.view.fragment.Profile;

import java.util.ArrayList;
import java.util.List;

public class HomeHotelListAdapter extends RecyclerView.Adapter<HomeHotelListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PlacesResultsItem> modelHotel = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelHotelFiltered = new ArrayList<>();
    private List<PhotosItem> hotelPhotosItem = new ArrayList<>();
    private FavoriteOperationHotel favoriteOperationHotel;
    private FavoriteDatabaseHotel favoriteDatabaseHotel;
    private List<FavoriteDataHotel> dataHotelList = new ArrayList<>();
    private FavoriteDAOHotel favoriteDAOHotel;

    public HomeHotelListAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<PlacesResultsItem> items) {
        modelHotel.clear();
        modelHotel.addAll(items);
        notifyDataSetChanged();

        modelHotelFiltered.clear();
        modelHotelFiltered.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeHotelListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hotels_design, parent, false);
        return new HomeHotelListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeHotelListAdapter.ViewHolder holder, final int position) {
        try {
            final FavoriteOperationHotel favoriteOperationHotel = new FavoriteOperationHotel(context);
            //Check Favorite Data
            favoriteDatabaseHotel = FavoriteDatabaseHotel.database(context);
            favoriteDAOHotel = favoriteDatabaseHotel.favoriteDAOHotels();
            dataHotelList = favoriteDatabaseHotel.favoriteDAOHotels().getFavorite();
            int favSize = dataHotelList.size();
            if (dataHotelList.size() == 0) {
            } else {
                for (int i = 0; i < favSize; i++) {
                    if (modelHotelFiltered.get(position).getName().equals(dataHotelList.get(i).getName()) && modelHotelFiltered.get(position).getVicinity().equals(dataHotelList.get(i).getVicinity())) {
                        holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red);
                    }
                }
            }
            holder.tvHotelName.setText(modelHotelFiltered.get(position).getName());
            holder.tvHotelAddress.setText(modelHotelFiltered.get(position).getVicinity());
            if (modelHotelFiltered.get(position).getOpeningHours().isOpenNow())
                holder.tvHotelStatus.setText("Open");
            else holder.tvHotelStatus.setText("Closed");
            hotelPhotosItem = modelHotelFiltered.get(position).getPhotos();
            int maxWidth = hotelPhotosItem.get(0).getWidth();
            String photoReference = hotelPhotosItem.get(0).getPhotoReference();
            Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + maxWidth + "&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().placeholder(R.drawable.special_loading).into(holder.containerLeft);
            holder.photosItems.setText(photoReference);
            double rating = modelHotelFiltered.get(position).getRating();
            if (Double.toString(rating) == "") holder.tvHotelRating.setText("-");
            else holder.tvHotelRating.setText(Double.toString(rating));

            holder.hotelCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
            holder.containerLeft.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

            final String url = "https://www.google.com/search?q=".concat(modelHotelFiltered.get(position).getName());

            holder.hotelCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
            });
            holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile profile = new Profile();
                    if (profile.aBoolean == true) {
                        favoriteDatabaseHotel = FavoriteDatabaseHotel.database(context);
                        favoriteDAOHotel = favoriteDatabaseHotel.favoriteDAOHotels();
                        dataHotelList = favoriteDatabaseHotel.favoriteDAOHotels().getFavorite();
                        String placeName = holder.tvHotelName.getText().toString();
                        String placeAddress = holder.tvHotelAddress.getText().toString();
                        int allData = dataHotelList.size();
                        Boolean result = false;
                        if (allData != 0) {
                            for (int i = 0; i < allData; i++) {
                                if (placeName.equals(dataHotelList.get(i).getName()) && placeAddress.equals(dataHotelList.get(i).getVicinity())) {
                                    Toast.makeText(context, holder.tvHotelName.getText().toString() + "Already in Favorite", Toast.LENGTH_SHORT).show();
                                    result = true;
                                    break;
                                }
                            }
                            if (result == false) {
                                favoriteOperationHotel.insertFavoriteDataHotel(
                                        "hotel",
                                        holder.tvHotelName.getText().toString(),
                                        holder.tvHotelAddress.getText().toString(),
                                        Double.valueOf(holder.tvHotelRating.getText().toString()),
                                        holder.photosItems.getText().toString(),
                                        FavoriteDatabaseHotel.database(context)
                                );
                                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red);
                            }
                        } else {
                            favoriteOperationHotel.insertFavoriteDataHotel(
                                    "hotel",
                                    holder.tvHotelName.getText().toString(),
                                    holder.tvHotelAddress.getText().toString(),
                                    Double.valueOf(holder.tvHotelRating.getText().toString()),
                                    holder.photosItems.getText().toString(),
                                    FavoriteDatabaseHotel.database(context)
                            );
                            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red);
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
        return modelHotelFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHotelName, tvHotelRating, tvHotelStatus, tvHotelAddress;
        private CardView hotelCardView;
        private ImageView containerLeft, favoriteButton;
        private TextView photosItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHotelName = itemView.findViewById(R.id.home_place_name);
            tvHotelRating = itemView.findViewById(R.id.home_place_rating);
            containerLeft = itemView.findViewById(R.id.home_place_image);
            tvHotelAddress = itemView.findViewById(R.id.home_hotel_address);
            tvHotelStatus = itemView.findViewById(R.id.home_open_status);
            photosItems = itemView.findViewById(R.id.home_place_photo_reference);
            hotelCardView = itemView.findViewById(R.id.home_cv_content_hotels);
            favoriteButton = itemView.findViewById(R.id.hotels_fav_button);
        }
    }
}
