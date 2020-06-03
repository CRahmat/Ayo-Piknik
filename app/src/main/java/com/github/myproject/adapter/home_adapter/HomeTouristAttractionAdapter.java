package com.github.myproject.adapter.home_adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.github.myproject.favorite_database.destination.FavoriteDAODestination;
import com.github.myproject.favorite_database.destination.FavoriteDataDestination;
import com.github.myproject.favorite_database.destination.FavoriteDatabaseDestination;
import com.github.myproject.favorite_database.destination.FavoriteOperationDestination;
import com.github.myproject.model.PhotosItem;
import com.github.myproject.model.PlacesResultsItem;
import com.github.myproject.view.activity.activity.DetailPlaceWebView;
import com.github.myproject.view.fragment.Profile;

import java.util.ArrayList;
import java.util.List;

public class HomeTouristAttractionAdapter extends RecyclerView.Adapter<HomeTouristAttractionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PlacesResultsItem> modelTouristAttraction = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelTouristAttractionFiltered = new ArrayList<>();
    private List<PhotosItem> touristAttractionPhotosItem = new ArrayList<>();
    private FavoriteOperationDestination favoriteOperationDestination;
    private FavoriteDatabaseDestination favoriteDatabaseDestination;
    private List<FavoriteDataDestination> favoriteDatumDestinations = new ArrayList<>();
    private FavoriteDAODestination favoriteDAODestination;

    public HomeTouristAttractionAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<PlacesResultsItem> items) {
        modelTouristAttraction.clear();
        modelTouristAttraction.addAll(items);
        notifyDataSetChanged();
        modelTouristAttractionFiltered.clear();
        modelTouristAttractionFiltered.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeTouristAttractionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_destination_design, parent, false);
        return new HomeTouristAttractionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeTouristAttractionAdapter.ViewHolder holder, final int position) {
        try {

            final FavoriteOperationDestination favoriteOperationDestination = new FavoriteOperationDestination(context);
            //Check Favorite Data
            favoriteDatabaseDestination = FavoriteDatabaseDestination.database(context);
            favoriteDAODestination = favoriteDatabaseDestination.favoriteDAO();
            favoriteDatumDestinations = favoriteDatabaseDestination.favoriteDAO().getFavorite();
            int favSize = favoriteDatumDestinations.size();
            if (favoriteDatumDestinations.size() == 0) {

            } else {
                for (int i = 0; i < favSize; i++) {
                    if (modelTouristAttractionFiltered.get(position).getName().equals(favoriteDatumDestinations.get(i).getName()) && modelTouristAttractionFiltered.get(position).getVicinity().equals(favoriteDatumDestinations.get(i).getVicinity())) {
                        holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red);
                    }
                }
            }

            //Input Data to View
            holder.tvTouristAttractionName.setText(modelTouristAttractionFiltered.get(position).getName());
            holder.tvTouristAttractionAddress.setText(modelTouristAttractionFiltered.get(position).getVicinity());
            touristAttractionPhotosItem = modelTouristAttractionFiltered.get(position).getPhotos();
            int maxWidth = touristAttractionPhotosItem.get(0).getWidth();
            String photoReference = touristAttractionPhotosItem.get(0).getPhotoReference();
            Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + maxWidth + "&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().into(holder.containerLeft);

            holder.photosItems.setText(photoReference);

            if (modelTouristAttractionFiltered.get(position).getOpeningHours().isOpenNow())
                holder.tvTouristAttractionOpenStatus.setText("Open");
            else holder.tvTouristAttractionOpenStatus.setText("Closed");

            double rating = modelTouristAttractionFiltered.get(position).getRating();
            if (Double.toString(rating) == "") holder.tvTouristAttractionRating.setText("-");
            else holder.tvTouristAttractionRating.setText(Double.toString(rating));

            holder.touristAttractionCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
            holder.containerLeft.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

            final String url = "https://www.google.com/search?q=".concat(modelTouristAttractionFiltered.get(position).getName());

            holder.touristAttractionCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webView = new Intent(context, DetailPlaceWebView.class);
                    webView.putExtra("load_url", url);
                    context.startActivity(webView);
                }
            });
            holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile profile = new Profile();
                    if (profile.aBoolean == true) {
                        favoriteDatabaseDestination = FavoriteDatabaseDestination.database(context);
                        favoriteDAODestination = favoriteDatabaseDestination.favoriteDAO();
                        favoriteDatumDestinations = favoriteDatabaseDestination.favoriteDAO().getFavorite();
                        String placeName = holder.tvTouristAttractionName.getText().toString();
                        String placeAddress = holder.tvTouristAttractionAddress.getText().toString();
                        int allData = favoriteDatumDestinations.size();
                        Boolean result = false;
                        if (allData != 0) {
                            for (int i = 0; i < allData; i++) {
                                if (placeName.equals(favoriteDatumDestinations.get(i).getName()) && placeAddress.equals(favoriteDatumDestinations.get(i).getVicinity())) {
                                    Toast.makeText(context, holder.tvTouristAttractionName.getText().toString() + "Already in Favorite", Toast.LENGTH_SHORT).show();
                                    result = true;
                                    break;
                                }
                            }
                            if (result == false) {
                                favoriteOperationDestination.insertFavoriteData(
                                        "destination",
                                        holder.tvTouristAttractionName.getText().toString(),
                                        holder.tvTouristAttractionAddress.getText().toString(),
                                        Double.valueOf(holder.tvTouristAttractionRating.getText().toString()),
                                        holder.photosItems.getText().toString(),
                                        FavoriteDatabaseDestination.database(context)
                                );
                                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red);
                            }
                        } else {
                            favoriteOperationDestination.insertFavoriteData(
                                    "destination",
                                    holder.tvTouristAttractionName.getText().toString(),
                                    holder.tvTouristAttractionAddress.getText().toString(),
                                    Double.valueOf(holder.tvTouristAttractionRating.getText().toString()),
                                    holder.photosItems.getText().toString(),
                                    FavoriteDatabaseDestination.database(context)
                            );
                            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red);
                        }

                    } else {
                        Toast.makeText(context, R.string.try_again, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Failed To Get The Data", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return modelTouristAttractionFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTouristAttractionName, tvTouristAttractionAddress, tvTouristAttractionOpenStatus, tvTouristAttractionRating;
        private CardView touristAttractionCardView;
        private ImageView containerLeft, favoriteButton;
        private TextView photosItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTouristAttractionName = itemView.findViewById(R.id.home_place_name);
            tvTouristAttractionAddress = itemView.findViewById(R.id.home_place_address);
            tvTouristAttractionOpenStatus = itemView.findViewById(R.id.home_place_status);
            tvTouristAttractionRating = itemView.findViewById(R.id.home_place_rating);
            favoriteButton = itemView.findViewById(R.id.attraction_fav_button);
            containerLeft = itemView.findViewById(R.id.home_destination_image);
            touristAttractionCardView = itemView.findViewById(R.id.home_cv_content);
            photosItems = itemView.findViewById(R.id.home_place_photo_reference);

        }
    }

}
