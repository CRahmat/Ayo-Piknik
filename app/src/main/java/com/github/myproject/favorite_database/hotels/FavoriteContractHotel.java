package com.github.myproject.favorite_database.hotels;

import android.content.Context;

import java.util.List;

public interface FavoriteContractHotel {
    interface view {
        void successAdd();

        void successDelete();

        void getDataHotel(List<FavoriteDataHotel> listFavoriteDataHotels);

        void deleteFavoriteDataHotel(FavoriteDataHotel favoriteDataHotel);

        void dataAlready();
    }

    // interfaace presenter digunakan untuk kodingan database nya
    interface presenter {
        void insertFavoriteDataHotel(String id, String name, String vicinity, Double rating, String image, FavoriteDatabaseHotel favoriteDatabaseHotel);

        void readFavoriteDataHotel(FavoriteDatabaseHotel favoriteDatabaseHotel, Context context);

        void deleteFavoriteDataHotel(FavoriteDataHotel favoriteDataHotel, FavoriteDatabaseHotel favoriteDatabaseHotel);
    }
}
