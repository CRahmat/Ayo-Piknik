package com.github.myproject.favorite_database.restaurant;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteDataRestaurant.class}, version = 1)
public abstract class FavoriteDatabaseRestaurant extends RoomDatabase {
    private static FavoriteDatabaseRestaurant favoriteDatabase;

    public static FavoriteDatabaseRestaurant database(Context context) {
        if (favoriteDatabase == null) {
            favoriteDatabase = Room.databaseBuilder(context, FavoriteDatabaseRestaurant.class, "favoriteDatabaseRestaurant").allowMainThreadQueries().build();
        }
        return favoriteDatabase;
    }

    public abstract FavoriteDAORestaurant favoriteDAORestaurant();
}
