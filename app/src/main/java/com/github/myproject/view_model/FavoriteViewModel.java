package com.github.myproject.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.myproject.favorite_database.destination.FavoriteDAODestination;
import com.github.myproject.favorite_database.destination.FavoriteDataDestination;
import com.github.myproject.favorite_database.destination.FavoriteDatabaseDestination;
import com.github.myproject.favorite_database.hotels.FavoriteDAOHotel;
import com.github.myproject.favorite_database.hotels.FavoriteDataHotel;
import com.github.myproject.favorite_database.hotels.FavoriteDatabaseHotel;
import com.github.myproject.favorite_database.restaurant.FavoriteDAORestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDataRestaurant;
import com.github.myproject.favorite_database.restaurant.FavoriteDatabaseRestaurant;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {
    FavoriteDAODestination favoriteDAODestination;
    FavoriteDatabaseDestination favoriteDatabaseDestination;
    List<FavoriteDataDestination> favoriteDatumDestinations = new ArrayList<>();

    FavoriteDAOHotel favoriteDAOHotel;
    FavoriteDatabaseHotel favoriteDatabaseHotel;
    List<FavoriteDataHotel> favoriteDataHotelList = new ArrayList<>();

    FavoriteDAORestaurant favoriteDAORestaurant;
    FavoriteDatabaseRestaurant favoriteDatabaseRestaurant;
    List<FavoriteDataRestaurant> favoriteDataRestaurantList = new ArrayList<>();
    private MutableLiveData<List<FavoriteDataDestination>> liveData = new MutableLiveData<>();
    private MutableLiveData<List<FavoriteDataHotel>> liveDataHotel = new MutableLiveData<>();
    private MutableLiveData<List<FavoriteDataRestaurant>> liveDataRestaurant = new MutableLiveData<>();

    public LiveData<List<FavoriteDataDestination>> getRepository() {
        return liveData;
    }

    public void setRepository(Context context) {
        if (favoriteDatabaseDestination == null) {
            favoriteDatabaseDestination = FavoriteDatabaseDestination.database(context);
            favoriteDAODestination = favoriteDatabaseDestination.favoriteDAO();
            favoriteDatumDestinations = favoriteDatabaseDestination.favoriteDAO().getFavorite();
        }
        if (favoriteDatumDestinations.size() != 0) {
            liveData.postValue(favoriteDatumDestinations);
        }
    }

    public LiveData<List<FavoriteDataHotel>> getRepositoryHotel() {
        return liveDataHotel;
    }

    public void setRepositoryHotel(Context context) {
        if (favoriteDatabaseHotel == null) {
            favoriteDatabaseHotel = FavoriteDatabaseHotel.database(context);
            favoriteDAOHotel = favoriteDatabaseHotel.favoriteDAOHotels();
            favoriteDataHotelList = favoriteDatabaseHotel.favoriteDAOHotels().getFavorite();
        }
        if (favoriteDataHotelList.size() != 0) {
            liveDataHotel.postValue(favoriteDataHotelList);
        }
    }

    public LiveData<List<FavoriteDataRestaurant>> getRepositoryRestaurant() {
        return liveDataRestaurant;
    }

    public void setRepositoryRestaurant(Context context) {
        if (favoriteDatabaseRestaurant == null) {
            favoriteDatabaseRestaurant = FavoriteDatabaseRestaurant.database(context);
            favoriteDAORestaurant = favoriteDatabaseRestaurant.favoriteDAORestaurant();
            favoriteDataRestaurantList = favoriteDatabaseRestaurant.favoriteDAORestaurant().getFavorite();
        }
        if (favoriteDataRestaurantList.size() != 0) {
            liveDataRestaurant.postValue(favoriteDataRestaurantList);
        }
    }
}
