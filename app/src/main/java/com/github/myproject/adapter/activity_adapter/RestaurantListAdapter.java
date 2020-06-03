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

import com.github.myproject.R;
import com.github.myproject.model.PlacesResultsItem;

import java.util.ArrayList;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<PlacesResultsItem> modelRestaurant = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelRestaurantFiltered = new ArrayList<>();

    public RestaurantListAdapter(Context context) {
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
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new RestaurantListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.ViewHolder holder, final int position) {
        holder.tvRestaurantName.setText(modelRestaurantFiltered.get(position).getName());
        holder.tvRestaurantAddress.setText(modelRestaurantFiltered.get(position).getVicinity());

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();

                if (Key.isEmpty()) {
                    modelRestaurantFiltered = modelRestaurant;
                } else {
                    ArrayList<PlacesResultsItem> listFiltered = new ArrayList<>();
                    for (PlacesResultsItem row : modelRestaurant) {
                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }
                    modelRestaurantFiltered = listFiltered;
                }

                FilterResults results = new FilterResults();
                results.values = modelRestaurantFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                modelRestaurantFiltered = (ArrayList<PlacesResultsItem>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRestaurantName, tvRestaurantAddress, tvRestaurantOpenStatus, tvRestaurantRating;
        private CardView restaurantCardView;
        private ImageView containerLeft;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRestaurantName = itemView.findViewById(R.id.item_list_place_name);
            tvRestaurantAddress = itemView.findViewById(R.id.item_list_place_address);
            tvRestaurantOpenStatus = itemView.findViewById(R.id.item_list_place_open_status);
            tvRestaurantRating = itemView.findViewById(R.id.item_list_place_rating);

            containerLeft = itemView.findViewById(R.id.item_list_left_container);

            restaurantCardView = itemView.findViewById(R.id.item_list_card_view);

        }
    }
}
