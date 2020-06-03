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

public class TourismAttractionAdapter extends RecyclerView.Adapter<TourismAttractionAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<PlacesResultsItem> modelTouristAttraction = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelTouristAttractionFiltered = new ArrayList<>();

    public TourismAttractionAdapter(Context context) {
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
    public TourismAttractionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TourismAttractionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TourismAttractionAdapter.ViewHolder holder, final int position) {
        holder.tvTouristAttractionName.setText(modelTouristAttractionFiltered.get(position).getName());
        holder.tvTouristAttractionAddress.setText(modelTouristAttractionFiltered.get(position).getVicinity());

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();

                if (Key.isEmpty()) {
                    modelTouristAttractionFiltered = modelTouristAttraction;
                } else {
                    ArrayList<PlacesResultsItem> listFiltered = new ArrayList<>();
                    for (PlacesResultsItem row : modelTouristAttraction) {
                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }
                    modelTouristAttractionFiltered = listFiltered;
                }

                FilterResults results = new FilterResults();
                results.values = modelTouristAttractionFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                modelTouristAttractionFiltered = (ArrayList<PlacesResultsItem>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTouristAttractionName, tvTouristAttractionAddress, tvTouristAttractionOpenStatus, tvTouristAttractionRating;
        private CardView touristAttractionCardView;
        private ImageView containerLeft;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTouristAttractionName = itemView.findViewById(R.id.item_list_place_name);
            tvTouristAttractionAddress = itemView.findViewById(R.id.item_list_place_address);
            tvTouristAttractionOpenStatus = itemView.findViewById(R.id.item_list_place_open_status);
            tvTouristAttractionRating = itemView.findViewById(R.id.item_list_place_rating);
            containerLeft = itemView.findViewById(R.id.item_list_left_container);
            touristAttractionCardView = itemView.findViewById(R.id.item_list_card_view);

        }
    }

}
