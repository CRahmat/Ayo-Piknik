package com.github.myproject.adapter.activity_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.myproject.R;
import com.github.myproject.model.PhotosItem;
import com.github.myproject.model.PlacesResultsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViharaListAdapter extends RecyclerView.Adapter<ViharaListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<PlacesResultsItem> modelVihara = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelViharaFiltered = new ArrayList<>();
    private List<PhotosItem> viharaPhotoItems = new ArrayList<>();


    public ViharaListAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<PlacesResultsItem> items) {
        modelVihara.clear();
        modelVihara.addAll(items);
        notifyDataSetChanged();

        modelViharaFiltered.clear();
        modelViharaFiltered.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViharaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViharaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViharaListAdapter.ViewHolder holder, final int position) {
        holder.tvHotelName.setText(modelViharaFiltered.get(position).getName());
        holder.tvHotelAddress.setText(modelViharaFiltered.get(position).getVicinity());
        viharaPhotoItems = modelViharaFiltered.get(position).getPhotos();
        int maxWidth = viharaPhotoItems.get(0).getWidth();
        String photoReference = viharaPhotoItems.get(0).getPhotoReference();
        Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + maxWidth + "&photoreference=" + photoReference + "&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14").centerCrop().placeholder(R.drawable.special_loading).into(holder.containerLeft);
        if (modelViharaFiltered.get(position).getOpeningHours().isOpenNow())
            holder.tvHotelOpenStatus.setText("Open");
        else holder.tvHotelOpenStatus.setText("Closed");

        double rating = modelViharaFiltered.get(position).getRating();
        if (Double.toString(rating) == "") holder.tvHotelRating.setText("-");
        else holder.tvHotelRating.setText(Double.toString(rating));

        holder.hotelCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.containerLeft.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

        final String url = "https://www.google.com/search?q=".concat(modelViharaFiltered.get(position).getName());

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
        return modelViharaFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();

                if (Key.isEmpty()) {
                    modelViharaFiltered = modelVihara;
                } else {
                    ArrayList<PlacesResultsItem> listFiltered = new ArrayList<>();
                    for (PlacesResultsItem row : modelVihara) {
                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }
                    modelViharaFiltered = listFiltered;
                }

                FilterResults results = new FilterResults();
                results.values = modelViharaFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                modelViharaFiltered = (ArrayList<PlacesResultsItem>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHotelName, tvHotelAddress, tvHotelOpenStatus, tvHotelRating;
        private CardView hotelCardView;
        private ImageView containerLeft;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHotelName = itemView.findViewById(R.id.item_list_place_name);
            tvHotelAddress = itemView.findViewById(R.id.item_list_place_address);
            tvHotelOpenStatus = itemView.findViewById(R.id.item_list_place_open_status);
            tvHotelRating = itemView.findViewById(R.id.item_list_place_rating);
            containerLeft = itemView.findViewById(R.id.item_list_left_container);
            hotelCardView = itemView.findViewById(R.id.item_list_card_view);

        }
    }
}
