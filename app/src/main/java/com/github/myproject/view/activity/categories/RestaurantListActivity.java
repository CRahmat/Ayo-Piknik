package com.github.myproject.view.activity.categories;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.myproject.R;
import com.github.myproject.adapter.activity_adapter.RestaurantListAdapter;
import com.github.myproject.model.PlacesResultsItem;
import com.github.myproject.view_model.RestaurantListViewModel;

import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity {

    private RestaurantListAdapter adapter;
    private RestaurantListViewModel viewModel;
    private RecyclerView recyclerView;
    private EditText editTextSearch;
    private Observer<ArrayList<PlacesResultsItem>> getModel = new Observer<ArrayList<PlacesResultsItem>>() {
        @Override
        public void onChanged(final ArrayList<PlacesResultsItem> placesResultsItems) {
            adapter.setData(placesResultsItems);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        recyclerView = findViewById(R.id.restaurant_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RestaurantListAdapter(this);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RestaurantListViewModel.class);
        viewModel.setModelRestaurants();
        viewModel.getModelRestaurants().observe(this, getModel);

        editTextSearch = findViewById(R.id.restaurant_list_search_bar);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
