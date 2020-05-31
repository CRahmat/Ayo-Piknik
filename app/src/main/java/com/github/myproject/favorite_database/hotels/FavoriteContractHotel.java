package com.github.myproject.favorite_database.restaurant;
import android.content.Context;

import com.github.myproject.favorite_database.FavoriteData;
import com.github.myproject.favorite_database.FavoriteDatabase;

import java.util.List;

public interface FavoriteContractRestaurant {
    interface view {
        void successAdd();

        void successDelete();

        void getData(List<FavoriteData> listFavoriteDatabase);

        void deleteFavoriteDataRestaurant(FavoriteData favoriteData);

        void dataAlready();
    }

    // interfaace presenter digunakan untuk kodingan database nya
    interface presenter {
        void insertFavoriteDataRestaurant(String id, String name, String vicinity, Double rating, FavoriteDatabase favoriteDatabase);

        void readFavoriteDataRestaurant(FavoriteDatabase favoriteDatabase, Context context);

        void deleteFavoriteDataRestaurant(FavoriteData favoriteData, FavoriteDatabase favoriteDatabase);
    }
}
