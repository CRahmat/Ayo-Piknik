package com.github.myproject.view.activity;

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
import com.github.myproject.adapter.TourismAttractionAdapter;
import com.github.myproject.model.PlacesResultsItem;
import com.github.myproject.view.view_model.TouristAttractionViewModel;

import java.util.ArrayList;

public class TourismAttractionListActivity extends AppCompatActivity {

    private TourismAttractionAdapter adapter;
    private TouristAttractionViewModel viewModel;
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
        setContentView(R.layout.activity_tourism_attraction_list);

        recyclerView = findViewById(R.id.tourism_attraction_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TourismAttractionAdapter(this);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(TouristAttractionViewModel.class);
        viewModel.setModelTouristAttractions();
        viewModel.getModelTouristAttractions().observe(this, getModel);

        editTextSearch = findViewById(R.id.tourism_attraction_list_search_bar);
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
