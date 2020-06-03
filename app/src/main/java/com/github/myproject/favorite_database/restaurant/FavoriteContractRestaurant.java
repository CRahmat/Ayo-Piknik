package com.github.myproject.favorite_database.restaurant;

import android.content.Context;

import java.util.List;

public interface FavoriteContractRestaurant {
    interface view {
        void successAdd();

        void successDelete();

        void getDataRestaurant(List<FavoriteDataRestaurant> favoriteDataRestaurants);

        void deleteFavoriteDataRestaurant(FavoriteDataRestaurant favoriteDataRestaurant);

        void dataAlready();
    }

    // interfaace presenter digunakan untuk kodingan database nya
    interface presenter {
        void insertFavoriteDataRestaurant(String id, String name, String vicinity, Double rating, String image, FavoriteDatabaseRestaurant favoriteDatabaseRestaurant);

        void readFavoriteDataRestaurant(FavoriteDatabaseRestaurant favoriteDatabaseRestaurant, Context context);

        void deleteFavoriteDataRestaurant(FavoriteDataRestaurant favoriteDataRestaurant, FavoriteDatabaseRestaurant favoriteDatabaseRestaurant);
    }
}
