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

public class ATMListAdapter extends RecyclerView.Adapter<ATMListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<PlacesResultsItem> modelATM = new ArrayList<>();
    private ArrayList<PlacesResultsItem> modelATMFiltered = new ArrayList<>();

    public ATMListAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<PlacesResultsItem> items) {
        modelATM.clear();
        modelATM.addAll(items);
        notifyDataSetChanged();

        modelATMFiltered.clear();
        modelATMFiltered.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ATMListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ATMListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ATMListAdapter.ViewHolder holder, final int position) {
        holder.tvHotelName.setText(modelATMFiltered.get(position).getName());
        holder.tvHotelAddress.setText(modelATMFiltered.get(position).getVicinity());

        if (modelATMFiltered.get(position).getOpeningHours().isOpenNow())
            holder.tvHotelOpenStatus.setText("Open");
        else holder.tvHotelOpenStatus.setText("Closed");

        double rating = modelATMFiltered.get(position).getRating();
        if (Double.toString(rating) == "") holder.tvHotelRating.setText("-");
        else holder.tvHotelRating.setText(Double.toString(rating));

        holder.hotelCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.containerLeft.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

        final String url = "https://www.google.com/search?q=".concat(modelATMFiltered.get(position).getName());

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
        return modelATMFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();

                if (Key.isEmpty()) {
                    modelATMFiltered = modelATM;
                } else {
                    ArrayList<PlacesResultsItem> listFiltered = new ArrayList<>();
                    for (PlacesResultsItem row : modelATM) {
                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                            listFiltered.add(row);
                        }
                    }
                    modelATMFiltered = listFiltered;
                }

                FilterResults results = new FilterResults();
                results.values = modelATMFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                modelATMFiltered = (ArrayList<PlacesResultsItem>) results.values;
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
