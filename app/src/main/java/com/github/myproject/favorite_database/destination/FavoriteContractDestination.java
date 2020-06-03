package com.github.myproject.favorite_database.destination;

import android.content.Context;

import java.util.List;

public interface FavoriteContractDestination {
    interface view {
        void successAdd();

        void successDelete();

        void getData(List<FavoriteDataDestination> listFavoriteDatabaseDestination);

        void deleteFavoriteData(FavoriteDataDestination favoriteDataDestination);

        void dataAlready();
    }

    // interfaace presenter digunakan untuk kodingan database nya
    interface presenter {
        void insertFavoriteData(String id, String name, String vicinity, Double rating, String image, FavoriteDatabaseDestination favoriteDatabaseDestination);

        void readFavoriteData(FavoriteDatabaseDestination favoriteDatabaseDestination, Context context);

        void deleteFavoriteData(FavoriteDataDestination favoriteDataDestination, FavoriteDatabaseDestination favoriteDatabaseDestination);
    }
}
