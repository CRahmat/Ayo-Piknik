package com.github.myproject.view.HomeAdapter;

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
import com.github.myproject.model.PhotosItem;
import com.github.myproject.model.PlacesResultsItem;

import java.util.ArrayList;
import java.util.List;

public class HomeRestaurantListAdapter extends RecyclerView.Adapter<HomeRestaurantListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PlacesResultsItem> modelRestaurant = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelRestaurantFiltered = new ArrayList<>();
    private List<PhotosItem> restaurantPhotosItem = new ArrayList<>();

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
    public void onBindViewHolder(@NonNull HomeRestaurantListAdapter.ViewHolder holder, final int position) {
        holder.tvRestaurantName.setText(modelRestaurantFiltered.get(position).getName());
        restaurantPhotosItem = modelRestaurantFiltered.get(position).getPhotos();
        int maxWidth = restaurantPhotosItem.get(0).getWidth();
        String photoReference = restaurantPhotosItem.get(0).getPhotoReference();
        Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + maxWidth + "&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().placeholder(R.drawable.ic_search_black).into(holder.containerLeft);

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
    }

    @Override
    public int getItemCount() {
        return modelRestaurantFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRestaurantName, tvRestaurantOpenStatus, tvRestaurantRating;
        private CardView restaurantCardView;
        private ImageView containerLeft;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRestaurantName = itemView.findViewById(R.id.home_place_name);
            tvRestaurantRating = itemView.findViewById(R.id.home_place_rating);
            tvRestaurantOpenStatus = itemView.findViewById(R.id.home_place_status);

            containerLeft = itemView.findViewById(R.id.home_place_image);

            restaurantCardView = itemView.findViewById(R.id.home_cv_content_restaurant);

        }
    }
}
