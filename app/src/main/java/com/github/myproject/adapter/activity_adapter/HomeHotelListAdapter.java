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

public class HomeHotelListAdapter extends RecyclerView.Adapter<HomeHotelListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PlacesResultsItem> modelHotel = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelHotelFiltered = new ArrayList<>();
    private List<PhotosItem> hotelPhotosItem = new ArrayList<>();

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
    public void onBindViewHolder(@NonNull HomeHotelListAdapter.ViewHolder holder, final int position) {
        holder.tvHotelName.setText(modelHotelFiltered.get(position).getName());
        holder.containerLeft.setImageResource(R.drawable.image_sample);
        hotelPhotosItem = modelHotelFiltered.get(position).getPhotos();
        int maxWidth = hotelPhotosItem.get(0).getWidth();
        String photoReference = hotelPhotosItem.get(0).getPhotoReference();
        Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + maxWidth + "&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().placeholder(R.drawable.ic_search_black).into(holder.containerLeft);

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
    }

    @Override
    public int getItemCount() {
        return modelHotelFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHotelName, tvHotelRating;
        private CardView hotelCardView;
        private ImageView containerLeft;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHotelName = itemView.findViewById(R.id.home_place_name);
            tvHotelRating = itemView.findViewById(R.id.home_place_rating);
            containerLeft = itemView.findViewById(R.id.home_place_image);
            hotelCardView = itemView.findViewById(R.id.home_cv_content_hotels);

        }
    }
}
