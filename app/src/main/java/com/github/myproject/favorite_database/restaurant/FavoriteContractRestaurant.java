package com.github.myproject.favorite_database;
import android.content.Context;

import java.util.List;

public interface FavoriteContract {
    interface view {
        void successAdd();

        void successDelete();

        void getData(List<FavoriteData> listFavoriteDatabase);

        void deleteFavoriteData(FavoriteData favoriteData);

        void dataAlready();
    }

    // interfaace presenter digunakan untuk kodingan database nya
    interface presenter {
        void insertFavoriteData(String id, String name, String vicinity, Double rating, FavoriteDatabase favoriteDatabase);

        void readFavoriteData(FavoriteDatabase favoriteDatabase, Context context);

        void deleteFavoriteData(FavoriteData favoriteData, FavoriteDatabase favoriteDatabase);
    }
}
