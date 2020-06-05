package com.github.myproject.view.fragment;


import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.github.myproject.adapter.home_adapter.HomeHotelListAdapter;
import com.github.myproject.adapter.home_adapter.HomeRestaurantListAdapter;
import com.github.myproject.adapter.home_adapter.HomeTouristAttractionAdapter;
import com.github.myproject.model.PlacesResultsItem;
import com.github.myproject.view.activity.categories.AirportList;
import com.github.myproject.view.activity.categories.AtmList;
import com.github.myproject.view.activity.categories.BankList;
import com.github.myproject.view.activity.categories.ChurchList;
import com.github.myproject.view.activity.categories.HospitalList;
import com.github.myproject.view.activity.categories.HotelListActivity;
import com.github.myproject.view.activity.categories.MosqueList;
import com.github.myproject.view.activity.categories.RestaurantListActivity;
import com.github.myproject.view.activity.categories.SupermarketList;
import com.github.myproject.view.activity.categories.TourismAttractionListActivity;
import com.github.myproject.view.activity.categories.TrainstationList;
import com.github.myproject.view.activity.categories.ViharaList;
import com.github.myproject.view_model.HotelListViewModel;
import com.github.myproject.view_model.RestaurantListViewModel;
import com.github.myproject.view_model.TouristAttractionViewModel;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private CardView bttn_restaurant,
            bttn_hotel,
            bttn_destination,
            bttn_mosque,
            bttn_church,
            bttn_vihara,
            bttn_bank,
            bttn_atm,
            btn_hospital,
            bttn_supermarket,
            bttn_airport,
            bttn_station;
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
    private TextView restaurantLabel;
    private ProgressBar progressBarDestination, progressBarRestaurant, progressBarHotels;
    private TextView progressBarDestinationDetails, progressBarRestaurantDetails, progressBarHotelsDetails;
    private Observer<ArrayList<PlacesResultsItem>> getModelHotels = new Observer<ArrayList<PlacesResultsItem>>() {
        @Override
        public void onChanged(final ArrayList<PlacesResultsItem> placesResultsItems) {
            adapterHotels.setData(placesResultsItems);
            if (placesResultsItems.size() != 0) {
                progressBarHotels.setVisibility(View.INVISIBLE);
                progressBarHotelsDetails.setVisibility(View.INVISIBLE);
            }
        }
    };
    private Observer<ArrayList<PlacesResultsItem>> getModelRestaurant = new Observer<ArrayList<PlacesResultsItem>>() {
        @Override
        public void onChanged(final ArrayList<PlacesResultsItem> placesResultsItems) {
            adapterRestaurant.setData(placesResultsItems);
            if (placesResultsItems.size() != 0) {
                progressBarRestaurant.setVisibility(View.INVISIBLE);
                progressBarRestaurantDetails.setVisibility(View.INVISIBLE);
            }
        }
    };
    private Observer<ArrayList<PlacesResultsItem>> getModelTouristAttraction = new Observer<ArrayList<PlacesResultsItem>>() {
        @Override
        public void onChanged(final ArrayList<PlacesResultsItem> placesResultsItems) {
            adapterTouristAttraction.setData(placesResultsItems);
            if (placesResultsItems.size() != 0) {
                progressBarDestination.setVisibility(View.INVISIBLE);
                progressBarDestinationDetails.setVisibility(View.INVISIBLE);
            }

        }
    };


    public Home() {
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
        //Instansiasi Obyek
        btn_hospital = view.findViewById(R.id.home_categories_hospital);
        bttn_airport = view.findViewById(R.id.home_categories_airport);
        bttn_atm = view.findViewById(R.id.home_categories_atm);
        bttn_bank = view.findViewById(R.id.home_categories_bank);
        bttn_church = view.findViewById(R.id.home_categories_church);
        bttn_destination = view.findViewById(R.id.home_categories_deestination);
        bttn_hotel = view.findViewById(R.id.home_categories_hotel);
        bttn_restaurant = view.findViewById(R.id.home_categories_restaurant);
        bttn_station = view.findViewById(R.id.home_categories_station);
        bttn_supermarket = view.findViewById(R.id.home_categories_supermarket);
        bttn_vihara = view.findViewById(R.id.home_categories_vihara);
        bttn_mosque = view.findViewById(R.id.home_categories_mosque);
        showAllAttraction = view.findViewById(R.id.show_all_attraction);
        showAllHotels = view.findViewById(R.id.show_all_hotels);
        showAllRestaurant = view.findViewById(R.id.show_all_restaurant);
        showSearch = view.findViewById(R.id.ic_search_show);
        searchLayout = view.findViewById(R.id.search_layout);
        logoLayout = view.findViewById(R.id.logo_layout);
        welcomeText = view.findViewById(R.id.welcome_text);
        welcomeTextDetail = view.findViewById(R.id.welcome_detail);
        homeWelcomeImage = view.findViewById(R.id.home_image);
        restaurantLabel = view.findViewById(R.id.restaurant_label);
        progressBarDestination = view.findViewById(R.id.home_destination_progress_bar);
        progressBarRestaurant = view.findViewById(R.id.home_restaurant_progress_bar);
        progressBarHotels = view.findViewById(R.id.home_hotels_progress_bar);
        progressBarDestinationDetails = view.findViewById(R.id.home_destination_progress_bar_details);
        progressBarRestaurantDetails = view.findViewById(R.id.home_restaurant_progress_bar_details);
        progressBarHotelsDetails = view.findViewById(R.id.home_hotels_progress_bar_details);
        //Setting  Bahasa Ucapan
        if (restaurantLabel.getText().toString().equals("Tempat Makan")) {
            if (Integer.parseInt(formattedDate) >= 4 && Integer.parseInt(formattedDate) <= 11) {
                welcomeText.setText("Hello, Selamat Pagi");
                homeWelcomeImage.setImageResource(R.drawable.morning_view);
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
            } else if (Integer.parseInt(formattedDate) >= 12 && Integer.parseInt(formattedDate) <= 14) {
                welcomeText.setText("Hello, Selamat Siang");
                homeWelcomeImage.setImageResource(R.drawable.morning_view);
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
            } else if (Integer.parseInt(formattedDate) >= 14 && Integer.parseInt(formattedDate) <= 18) {
                welcomeText.setText("Hello, Selamat Sore");
                homeWelcomeImage.setImageResource(R.drawable.afternoon_view);
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
            } else if (Integer.parseInt(formattedDate) >= 19 || Integer.parseInt(formattedDate) <= 3) {
                welcomeText.setText("Hello, Selamat Malam");
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
                homeWelcomeImage.setImageResource(R.drawable.night_view);
            }
        } else {
            if (Integer.parseInt(formattedDate) >= 4 && Integer.parseInt(formattedDate) <= 11) {
                welcomeText.setText("Hello, Good Morning");
                homeWelcomeImage.setImageResource(R.drawable.morning_view);
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
            } else if (Integer.parseInt(formattedDate) >= 12 && Integer.parseInt(formattedDate) <= 14) {
                welcomeText.setText("Hello, Good Afternoon");
                homeWelcomeImage.setImageResource(R.drawable.morning_view);
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
            } else if (Integer.parseInt(formattedDate) >= 14 && Integer.parseInt(formattedDate) <= 17) {
                welcomeText.setText("Hello, Good Afternoon");
                homeWelcomeImage.setImageResource(R.drawable.afternoon_view);
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
            } else if (Integer.parseInt(formattedDate) >= 18 || Integer.parseInt(formattedDate) <= 3) {
                welcomeText.setText("Hello, Good Night ");
                welcomeText.setTextColor(getResources().getColor(R.color.bgColor3));
                welcomeTextDetail.setTextColor(getResources().getColor(R.color.bgColor3));
                homeWelcomeImage.setImageResource(R.drawable.night_view);
            }
        }

        //Setting Destination
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

        //Setting Restaurant
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

        //Setting Hotels
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bttn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantListActivity.class);
                startActivity(intent);
            }
        });

        bttn_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelListActivity.class);
                startActivity(intent);

            }
        });

        bttn_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TourismAttractionListActivity.class);
                startActivity(intent);

            }
        });
        bttn_mosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MosqueList.class);
                startActivity(intent);

            }
        });
        bttn_church.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChurchList.class);
                startActivity(intent);

            }
        });
        bttn_vihara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViharaList.class);
                startActivity(intent);

            }
        });
        bttn_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BankList.class);
                startActivity(intent);

            }
        });
        bttn_atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AtmList.class);
                startActivity(intent);

            }
        });
        btn_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HospitalList.class);
                startActivity(intent);

            }
        });
        bttn_supermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SupermarketList.class);
                startActivity(intent);

            }
        });
        bttn_airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AirportList.class);
                startActivity(intent);

            }
        });
        bttn_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrainstationList.class);
                startActivity(intent);

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

        //Menampilkan search pada home
        showSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchShow == false) {
                    searchShow = true;
                    searchLayout.setVisibility(View.VISIBLE);
                    searchLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_scale_animation));
                    logoLayout.setVisibility(View.INVISIBLE);
                    //Setting Lottie Animation
                    showSearch.setMinProgress(0.0f);
                    showSearch.setMaxProgress(0.5f);
                    showSearch.playAnimation();
                } else {
                    searchShow = false;
                    searchLayout.setVisibility(View.INVISIBLE);
                    logoLayout.setVisibility(View.VISIBLE);
                    logoLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_scale_animation));
                    //Setting Lottie Animation
                    showSearch.setMinProgress(0.5f);
                    showSearch.setMaxProgress(1.0f);
                    showSearch.playAnimation();
                }
            }
        });

    }

}
