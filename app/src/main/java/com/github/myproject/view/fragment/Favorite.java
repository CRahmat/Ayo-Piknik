package com.github.myproject.view.fragment;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.myproject.R;
import com.github.myproject.adapter.favorite_adapter.FavoriteAdapterDestination;
import com.github.myproject.adapter.favorite_adapter.FavoriteAdapterHotel;
import com.github.myproject.adapter.favorite_adapter.FavoriteAdapterRestaurant;
import com.github.myproject.favorite_database.destination.FavoriteContractDestination;
import com.github.myproject.favorite_database.destination.FavoriteDAODestination;
import com.github.myproject.favorite_database.destination.FavoriteDataDestination;
import com.github.myproject.favorite_database.destination.FavoriteDatabaseDestination;
import com.github.myproject.favorite_database.destination.FavoriteOperationDestination;
import com.github.myproject.favorite_database.hotels.FavoriteContractHotel;
import com.github.myproject.favorite_database.hotels.FavoriteDAOHotel;
import com.github.myproject.favorite_database.hotels.FavoriteDataHotel;
import com.github.myproject.favorite_database.hotels.FavoriteDatabaseHotel;
import com.github.myproject.favorite_database.hotels.FavoriteOperationHotel;
import com.github.myproject.favorite_database.restaurant.FavoriteContractRestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDAORestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDataRestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDatabaseRestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteOperationRestaurant;
import com.github.myproject.view_model.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite extends Fragment implements FavoriteContractDestination.view, FavoriteContractHotel.view, FavoriteContractRestaurant.view {

    private FavoriteViewModel favoriteViewModel;

    private RecyclerView recyclerViewDestination, recyclerViewHotel, recyclerViewRestaurant;
    private ConstraintLayout notFound;
    private List<FavoriteDataDestination> favoriteDatumDestinations = new ArrayList<>();
    private FavoriteContractDestination.view repositoryView;
    private FavoriteOperationDestination favoriteOperationDestination;
    private FavoriteDAODestination favoriteDAODestination;
    private FavoriteDatabaseDestination favoriteDatabaseDestination;
    private FavoriteAdapterDestination favoriteAdapterDestination;

    private List<FavoriteDataHotel> favoriteDataHotelList = new ArrayList<>();
    private FavoriteContractHotel.view hotelView;
    private FavoriteOperationHotel favoriteOperationHotel;
    private FavoriteDAOHotel favoriteDAOHotel;
    private FavoriteDatabaseHotel favoriteDatabaseHotel;
    private FavoriteAdapterHotel favoriteAdapterHotel;

    private List<FavoriteDataRestaurant> favoriteDataRestaurantList = new ArrayList<>();
    private FavoriteContractRestaurant.view restaurantView;
    private FavoriteOperationRestaurant favoriteOperationRestaurant;
    private FavoriteDAORestaurant favoriteDAORestaurant;
    private FavoriteDatabaseRestaurant favoriteDatabaseRestaurant;
    private FavoriteAdapterRestaurant favoriteAdapterRestaurant;
    private LinearLayout layoutFavHotel, layoutFavRestaurant, layoutFavDestination;
    private Observer<List<FavoriteDataDestination>> getFavorite = new Observer<List<FavoriteDataDestination>>() {
        @Override
        public void onChanged(List<FavoriteDataDestination> favoriteDatumDestinations) {
            if (favoriteDatumDestinations != null) {
                setData(favoriteDatumDestinations);
            } else {
                Toast.makeText(getContext(), R.string.failed_get_data, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Observer<List<FavoriteDataHotel>> getFavoriteHotel = new Observer<List<FavoriteDataHotel>>() {
        @Override
        public void onChanged(List<FavoriteDataHotel> favoriteDataHotels) {
            if (favoriteDataHotels != null) {
                setDataHotel(favoriteDataHotels);
            } else {
                Toast.makeText(getContext(), R.string.failed_get_data, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Observer<List<FavoriteDataRestaurant>> getFavoriteRestaurant = new Observer<List<FavoriteDataRestaurant>>() {
        @Override
        public void onChanged(List<FavoriteDataRestaurant> favoriteDataRestaurants) {
            if (favoriteDataRestaurants != null) {
                setDataRestaurant(favoriteDataRestaurants);
            } else {
                Toast.makeText(getContext(), R.string.failed_get_data, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public Favorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewDestination = view.findViewById(R.id.rv_favorite_destination);
        recyclerViewHotel = view.findViewById(R.id.rv_favorite_hotels);
        recyclerViewRestaurant = view.findViewById(R.id.rv_favorite_restaurant);

        layoutFavDestination = view.findViewById(R.id.fav_destination_layout);
        layoutFavHotel = view.findViewById(R.id.fav_hotels_layout);
        layoutFavRestaurant = view.findViewById(R.id.fav_restaurant_layout);

        favoriteAdapterDestination = new FavoriteAdapterDestination(getContext());
        favoriteAdapterDestination.notifyDataSetChanged();
        recyclerViewDestination.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        favoriteViewModel.setRepository(getContext());
        favoriteViewModel.getRepository().observe(this, getFavorite);
        recyclerViewDestination.setAdapter(favoriteAdapterDestination);
        favoriteOperationDestination = new FavoriteOperationDestination(this);
        favoriteOperationDestination.readFavoriteData(favoriteDatabaseDestination, getContext());

        favoriteAdapterRestaurant = new FavoriteAdapterRestaurant(getContext());
        favoriteAdapterRestaurant.notifyDataSetChanged();
        recyclerViewRestaurant.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        favoriteViewModel.setRepositoryRestaurant(getContext());
        favoriteViewModel.getRepositoryRestaurant().observe(this, getFavoriteRestaurant);
        recyclerViewRestaurant.setAdapter(favoriteAdapterRestaurant);
        favoriteOperationRestaurant = new FavoriteOperationRestaurant(this);
        favoriteOperationRestaurant.readFavoriteDataRestaurant(favoriteDatabaseRestaurant, getContext());

        favoriteAdapterHotel = new FavoriteAdapterHotel(getContext());
        favoriteAdapterHotel.notifyDataSetChanged();
        recyclerViewHotel.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        favoriteViewModel.setRepositoryHotel(getContext());
        favoriteViewModel.getRepositoryHotel().observe(this, getFavoriteHotel);
        recyclerViewHotel.setAdapter(favoriteAdapterHotel);
        favoriteOperationHotel = new FavoriteOperationHotel(this);
        favoriteOperationHotel.readFavoriteDataHotel(favoriteDatabaseHotel, getContext());


    }

    @Override
    public void successAdd() {
        Toast.makeText(getContext(), R.string.success_add_favorite, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        Toast.makeText(getContext(), R.string.success_delete_fav, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataRestaurant(List<FavoriteDataRestaurant> favoriteDataRestaurants) {
        favoriteAdapterRestaurant = new FavoriteAdapterRestaurant(getContext(), favoriteDataRestaurants, this);
        int favoriteDataSize = favoriteDataRestaurants.size();
        for (int position = 0; position < favoriteDataSize; position++) {
            if (favoriteDataRestaurants.get(position).getIdentity().equals("restaurant")) {
                recyclerViewRestaurant.setAdapter(favoriteAdapterRestaurant);
            }
        }
    }

    @Override
    public void deleteFavoriteDataRestaurant(final FavoriteDataRestaurant favoriteDataRestaurant) {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(R.string.delete_data_conf)
                .setMessage(R.string.delete_conf_2)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        favoriteOperationRestaurant.deleteFavoriteDataRestaurant(favoriteDataRestaurant, favoriteDatabaseRestaurant);
                        if (favoriteOperationRestaurant != null) {
                            favoriteDatabaseRestaurant = FavoriteDatabaseRestaurant.database(getContext());
                            favoriteDAORestaurant = favoriteDatabaseRestaurant.favoriteDAORestaurant();
                            favoriteDataRestaurantList = favoriteDatabaseRestaurant.favoriteDAORestaurant().getFavorite();

                            if (favoriteDataRestaurantList.size() != 0) {
                                notFound.setVisibility(View.GONE);
                                layoutFavRestaurant.setVisibility(View.VISIBLE);
                                recyclerViewRestaurant.setAnimation(null);
                                favoriteOperationRestaurant.readFavoriteDataRestaurant(favoriteDatabaseRestaurant, getContext());
                            } else {
                                layoutFavRestaurant.setVisibility(View.GONE);
                                favoriteOperationRestaurant.readFavoriteDataRestaurant(favoriteDatabaseRestaurant, getContext());
                                if (favoriteDatumDestinations.size() == 0 && favoriteDataHotelList.size() == 0 && favoriteDataRestaurantList.size() == 0) {
                                    notFound.setVisibility(View.VISIBLE);
                                    recyclerViewRestaurant.setAnimation(null);
                                    layoutFavDestination.setVisibility(View.GONE);
                                    layoutFavHotel.setVisibility(View.GONE);
                                    layoutFavRestaurant.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void getDataHotel(List<FavoriteDataHotel> listFavoriteDataHotels) {
        favoriteAdapterHotel = new FavoriteAdapterHotel(getContext(), listFavoriteDataHotels, this);
        int favoriteDataSize = listFavoriteDataHotels.size();
        for (int position = 0; position < favoriteDataSize; position++) {
            if (listFavoriteDataHotels.get(position).getIdentity().equals("hotel")) {
                recyclerViewHotel.setAdapter(favoriteAdapterHotel);
            }
        }
    }

    @Override
    public void deleteFavoriteDataHotel(final FavoriteDataHotel favoriteDataHotel) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(R.string.delete_data_conf)
                .setMessage(R.string.delete_conf_2)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        favoriteOperationHotel.deleteFavoriteDataHotel(favoriteDataHotel, favoriteDatabaseHotel);
                        if (favoriteOperationHotel != null) {
                            favoriteDatabaseHotel = FavoriteDatabaseHotel.database(getContext());
                            favoriteDAOHotel = favoriteDatabaseHotel.favoriteDAOHotels();
                            favoriteDataHotelList = favoriteDatabaseHotel.favoriteDAOHotels().getFavorite();

                            if (favoriteDataHotelList.size() != 0) {
                                notFound.setVisibility(View.GONE);
                                layoutFavHotel.setVisibility(View.VISIBLE);
                                recyclerViewHotel.setAnimation(null);
                                favoriteOperationHotel.readFavoriteDataHotel(favoriteDatabaseHotel, getContext());
                            } else {
                                layoutFavHotel.setVisibility(View.GONE);
                                favoriteOperationHotel.readFavoriteDataHotel(favoriteDatabaseHotel, getContext());
                                if (favoriteDatumDestinations.size() == 0 && favoriteDataHotelList.size() == 0 && favoriteDataRestaurantList.size() == 0) {
                                    notFound.setVisibility(View.VISIBLE);
                                    recyclerViewHotel.setAnimation(null);
                                    layoutFavDestination.setVisibility(View.GONE);
                                    layoutFavHotel.setVisibility(View.GONE);
                                    layoutFavRestaurant.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void getData(List<FavoriteDataDestination> listFavoriteDatabaseDestination) {
        favoriteAdapterDestination = new FavoriteAdapterDestination(getContext(), listFavoriteDatabaseDestination, this);
        int favoriteDataSize = listFavoriteDatabaseDestination.size();
        for (int position = 0; position < favoriteDataSize; position++) {
            if (listFavoriteDatabaseDestination.get(position).getIdentity().equals("destination")) {
                recyclerViewDestination.setAdapter(favoriteAdapterDestination);
            }
        }
    }

    @Override
    public void deleteFavoriteData(final FavoriteDataDestination favoriteDataDestination) {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(R.string.delete_data_conf)
                .setMessage(R.string.delete_conf_2)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        favoriteOperationDestination.deleteFavoriteData(favoriteDataDestination, favoriteDatabaseDestination);
                        if (favoriteOperationDestination != null) {
                            favoriteDatabaseDestination = FavoriteDatabaseDestination.database(getContext());
                            favoriteDAODestination = favoriteDatabaseDestination.favoriteDAO();
                            favoriteDatumDestinations = favoriteDatabaseDestination.favoriteDAO().getFavorite();
                            if (favoriteDatumDestinations.size() != 0) {
                                notFound.setVisibility(View.GONE);
                                layoutFavDestination.setVisibility(View.VISIBLE);
                                recyclerViewDestination.setAnimation(null);
                                favoriteOperationDestination.readFavoriteData(favoriteDatabaseDestination, getContext());
                            } else {
                                layoutFavDestination.setVisibility(View.GONE);
                                favoriteOperationDestination.readFavoriteData(favoriteDatabaseDestination, getContext());
                                if (favoriteDatumDestinations.size() == 0 && favoriteDataHotelList.size() == 0 && favoriteDataRestaurantList.size() == 0) {
                                    notFound.setVisibility(View.VISIBLE);
                                    recyclerViewDestination.setAnimation(null);
                                    layoutFavDestination.setVisibility(View.GONE);
                                    layoutFavHotel.setVisibility(View.GONE);
                                    layoutFavRestaurant.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void dataAlready() {
        Toast.makeText(getContext(), R.string.already_exist, Toast.LENGTH_SHORT).show();
    }

    public void setData(List<FavoriteDataDestination> repositoryResponses) {
        this.favoriteDatumDestinations.clear();
        this.favoriteDatumDestinations.addAll(repositoryResponses);

        if (favoriteDatabaseDestination == null) {
            favoriteDatabaseDestination = FavoriteDatabaseDestination.database(getContext());
            favoriteDAODestination = favoriteDatabaseDestination.favoriteDAO();
            favoriteDatumDestinations = favoriteDatabaseDestination.favoriteDAO().getFavorite();
            notFound = getView().findViewById(R.id.animation_layout);
            if (favoriteDatumDestinations.size() != 0) {
                favoriteOperationDestination.readFavoriteData(favoriteDatabaseDestination, getContext());
                int favoriteDataSize = favoriteDatumDestinations.size();
                for (int position = 0; position < favoriteDataSize; position++) {
                    if (favoriteDatumDestinations.get(position).getIdentity().equals("destination")) {
                        notFound.setVisibility(View.GONE);
                        layoutFavDestination.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void setDataHotel(List<FavoriteDataHotel> dataHotel) {
        this.favoriteDataHotelList.clear();
        this.favoriteDataHotelList.addAll(dataHotel);

        if (favoriteDatabaseHotel == null) {
            favoriteDatabaseHotel = FavoriteDatabaseHotel.database(getContext());
            favoriteDAOHotel = favoriteDatabaseHotel.favoriteDAOHotels();
            favoriteDataHotelList = favoriteDatabaseHotel.favoriteDAOHotels().getFavorite();
            layoutFavHotel = getView().findViewById(R.id.fav_hotels_layout);
            notFound = getView().findViewById(R.id.animation_layout);
            if (favoriteDataHotelList.size() != 0) {
                favoriteOperationHotel.readFavoriteDataHotel(favoriteDatabaseHotel, getContext());
                int favoriteDataSize = favoriteDataHotelList.size();
                for (int position = 0; position < favoriteDataSize; position++) {
                    if (favoriteDataHotelList.get(position).getIdentity().equals("hotel")) {
                        notFound.setVisibility(View.GONE);
                        layoutFavHotel.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void setDataRestaurant(List<FavoriteDataRestaurant> dataRestaurant) {
        this.favoriteDataRestaurantList.clear();
        this.favoriteDataRestaurantList.addAll(dataRestaurant);

        if (favoriteDatabaseRestaurant == null) {
            favoriteDatabaseRestaurant = FavoriteDatabaseRestaurant.database(getContext());
            favoriteDAORestaurant = favoriteDatabaseRestaurant.favoriteDAORestaurant();
            favoriteDataRestaurantList = favoriteDatabaseRestaurant.favoriteDAORestaurant().getFavorite();
            notFound = getView().findViewById(R.id.animation_layout);
            if (favoriteDataRestaurantList.size() != 0) {
                favoriteOperationRestaurant.readFavoriteDataRestaurant(favoriteDatabaseRestaurant, getContext());
                int favoriteDataSize = favoriteDataRestaurantList.size();
                for (int position = 0; position < favoriteDataSize; position++) {
                    if (favoriteDataRestaurantList.get(position).getIdentity().equals("restaurant")) {
                        notFound.setVisibility(View.GONE);
                        layoutFavRestaurant.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}
