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

public class HomeTouristAttractionAdapter extends RecyclerView.Adapter<HomeTouristAttractionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PlacesResultsItem> modelTouristAttraction = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelTouristAttractionFiltered = new ArrayList<>();
    private List<PhotosItem> touristAttractionPhotosItem = new ArrayList<>();

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
    public void onBindViewHolder(@NonNull HomeTouristAttractionAdapter.ViewHolder holder, final int position) {
        holder.tvTouristAttractionName.setText(modelTouristAttractionFiltered.get(position).getName());
        holder.tvTouristAttractionAddress.setText(modelTouristAttractionFiltered.get(position).getVicinity());
        touristAttractionPhotosItem = modelTouristAttractionFiltered.get(position).getPhotos();
        int maxWidth = touristAttractionPhotosItem.get(0).getWidth();
        String photoReference = touristAttractionPhotosItem.get(0).getPhotoReference();
        Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + maxWidth + "&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().placeholder(R.drawable.ic_search_black).into(holder.containerLeft);


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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelTouristAttractionFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTouristAttractionName, tvTouristAttractionAddress, tvTouristAttractionOpenStatus, tvTouristAttractionRating;
        private CardView touristAttractionCardView;
        private ImageView containerLeft;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTouristAttractionName = itemView.findViewById(R.id.home_place_name);
            tvTouristAttractionAddress = itemView.findViewById(R.id.home_place_address);
            tvTouristAttractionOpenStatus = itemView.findViewById(R.id.home_place_status);
            tvTouristAttractionRating = itemView.findViewById(R.id.home_place_rating);

            containerLeft = itemView.findViewById(R.id.home_place_image);

            touristAttractionCardView = itemView.findViewById(R.id.home_cv_content);

        }
    }

}
