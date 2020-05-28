package com.github.myproject.view.fragment;


import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.myproject.R;
import com.github.myproject.model.PlacesResultsItem;
import com.github.myproject.view.HomeAdapter.HomeHotelListAdapter;
import com.github.myproject.view.HomeAdapter.HomeRestaurantListAdapter;
import com.github.myproject.view.HomeAdapter.HomeTouristAttractionAdapter;
import com.github.myproject.view.activity.HotelListActivity;
import com.github.myproject.view.activity.RestaurantListActivity;
import com.github.myproject.view.activity.TourismAttractionListActivity;
import com.github.myproject.view.view_model.HotelListViewModel;
import com.github.myproject.view.view_model.RestaurantListViewModel;
import com.github.myproject.view.view_model.TouristAttractionViewModel;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private CardView containerLeft, containerRight, containerBottom;
    private HomeHotelListAdapter adapterHotels;
    private HotelListViewModel viewModelHotels;
    private RecyclerView recyclerViewHotels;

    private HomeRestaurantListAdapter adapterRestaurant;
    private RestaurantListViewModel viewModelRestaurant;
    private RecyclerView recyclerViewRestaurant;

    private HomeTouristAttractionAdapter adapterTouristAttraction;
    private TouristAttractionViewModel touristAttractionViewModel;
    private RecyclerView recyclerTouristAttraction;

    private TextView showAllAttraction, showAllHotels, showAllRestaurant;
    private LottieAnimationView showSearch;
    private LinearLayout searchLayout;
    private ImageView logoLayout;
    private Boolean searchShow = false;
    private String formattedDate;
    private TextView welcomeText;
    private TextView welcomeTextDetail;
    private ImageView homeWelcomeImage;
    private Observer<ArrayList<PlacesResultsItem>> getModelHotels = new Observer<ArrayList<PlacesResultsItem>>() {
        @Override
        public void onChanged(final ArrayList<PlacesResultsItem> placesResultsItems) {
            adapterHotels.setData(placesResultsItems);
        }
    };
    private Observer<ArrayList<PlacesResultsItem>> getModelRestaurant = new Observer<ArrayList<PlacesResultsItem>>() {
        @Override
        public void onChanged(final ArrayList<PlacesResultsItem> placesResultsItems) {
            adapterRestaurant.setData(placesResultsItems);
        }
    };
    private Observer<ArrayList<PlacesResultsItem>> getModelTouristAttraction = new Observer<ArrayList<PlacesResultsItem>>() {
        @Override
        public void onChanged(final ArrayList<PlacesResultsItem> placesResultsItems) {
            adapterTouristAttraction.setData(placesResultsItems);
        }
    };


    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Get Time Now
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        formattedDate = df.format(c.getTime());

        containerLeft = view.findViewById(R.id.fragmentHome_cardViewLeft);
        containerRight = view.findViewById(R.id.fragmentHome_cardViewRight);
        containerBottom = view.findViewById(R.id.fragmentHome_cardViewBottom);
        showAllAttraction = view.findViewById(R.id.show_all_attraction);
        showAllHotels = view.findViewById(R.id.show_all_hotels);
        showAllRestaurant = view.findViewById(R.id.show_all_restaurant);
        showSearch = view.findViewById(R.id.ic_search_show);
        searchLayout = view.findViewById(R.id.search_layout);
        logoLayout = view.findViewById(R.id.logo_layout);
        welcomeText = view.findViewById(R.id.welcome_text);
        welcomeTextDetail = view.findViewById(R.id.welcome_detail);
        homeWelcomeImage = view.findViewById(R.id.home_image);

        if (Integer.parseInt(formattedDate) > 4 && Integer.parseInt(formattedDate) <= 11) {
            welcomeText.setText("Hello, Good Morning");
            homeWelcomeImage.setImageResource(R.drawable.morning_view);
        } else if (Integer.parseInt(formattedDate) > 12 && Integer.parseInt(formattedDate) <= 18) {
            welcomeText.setText("Hello, Good Afternoon");
            homeWelcomeImage.setImageResource(R.drawable.afternoon_view);
            welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
            welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
        } else if (Integer.parseInt(formattedDate) > 19 || Integer.parseInt(formattedDate) <= 3) {
            welcomeText.setText("Hello, Good Night");
            welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
            welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
            homeWelcomeImage.setImageResource(R.drawable.night_view);
        }

        recyclerTouristAttraction = view.findViewById(R.id.rv_content_tourist_attraction);
        LinearLayoutManager touristAttractionLayoutManager = new LinearLayoutManager(getContext());
        touristAttractionLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerTouristAttraction.setLayoutManager(touristAttractionLayoutManager);
        adapterTouristAttraction = new HomeTouristAttractionAdapter(getContext());
        adapterTouristAttraction.notifyDataSetChanged();
        recyclerTouristAttraction.setAdapter(adapterTouristAttraction);
        touristAttractionViewModel = new ViewModelProvider(this).get(TouristAttractionViewModel.class);
        touristAttractionViewModel.setModelTouristAttractions();
        touristAttractionViewModel.getModelTouristAttractions().observe(this, getModelTouristAttraction);

        recyclerViewRestaurant = view.findViewById(R.id.rv_content_restaurant);
        LinearLayoutManager restaurantLayoutManager = new LinearLayoutManager(getContext());
        restaurantLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewRestaurant.setLayoutManager(restaurantLayoutManager);
        adapterRestaurant = new HomeRestaurantListAdapter(getContext());
        adapterRestaurant.notifyDataSetChanged();
        recyclerViewRestaurant.setAdapter(adapterRestaurant);
        viewModelRestaurant = new ViewModelProvider(this).get(RestaurantListViewModel.class);
        viewModelRestaurant.setModelRestaurants();
        viewModelRestaurant.getModelRestaurants().observe(this, getModelRestaurant);

        recyclerViewHotels = view.findViewById(R.id.rv_content_hotels);
        LinearLayoutManager hotelsLayoutManager = new LinearLayoutManager(getContext());
        hotelsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHotels.setLayoutManager(hotelsLayoutManager);
        adapterHotels = new HomeHotelListAdapter(getContext());
        adapterHotels.notifyDataSetChanged();
        recyclerViewHotels.setAdapter(adapterHotels);
        viewModelHotels = new ViewModelProvider(this).get(HotelListViewModel.class);
        viewModelHotels.setModelHotel();
        viewModelHotels.getModelHotel().observe(this, getModelHotels);

        Animation animBottomTop = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_home_bottom_to_top);
        Animation animLeftRight = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_home_left_to_right);
        Animation animRightLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_home_right_to_left);

        containerBottom.setAnimation(animBottomTop);
        containerLeft.setAnimation(animLeftRight);
        containerRight.setAnimation(animRightLeft);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        containerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantListActivity.class);
                startActivity(intent);
            }
        });

        containerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelListActivity.class);
                startActivity(intent);

            }
        });

        containerBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TourismAttractionListActivity.class);
                startActivity(intent);

            }
        });
        showSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchShow == false) {
                    searchShow = true;
                    searchLayout.setVisibility(View.VISIBLE);
                    searchLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_scale_animation));
                    logoLayout.setVisibility(View.INVISIBLE);
                    showSearch.setMinProgress(0.0f);
                    showSearch.setMaxProgress(0.5f);
                    showSearch.playAnimation();
                } else {
                    searchShow = false;
                    searchLayout.setVisibility(View.INVISIBLE);
                    logoLayout.setVisibility(View.VISIBLE);
                    showSearch.setMinProgress(0.5f);
                    showSearch.setMaxProgress(1.0f);
                    showSearch.playAnimation();
                }
            }
        });

        showAllRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantListActivity.class);
                startActivity(intent);
            }
        });

        showAllHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelListActivity.class);
                startActivity(intent);

            }
        });

        showAllAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TourismAttractionListActivity.class);
                startActivity(intent);

            }
        });

    }

}
