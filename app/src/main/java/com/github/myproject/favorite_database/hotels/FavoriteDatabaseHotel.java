package com.github.myproject.favorite_database.hotels;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteDataHotel.class}, version = 1)
public abstract class FavoriteDatabaseHotel extends RoomDatabase {
    private static FavoriteDatabaseHotel favoriteDatabase;

    public static FavoriteDatabaseHotel database(Context context) {
        if (favoriteDatabase == null) {
            favoriteDatabase = Room.databaseBuilder(context, FavoriteDatabaseHotel.class, "favoriteDatabaseHotel").allowMainThreadQueries().build();
        }
        return favoriteDatabase;
    }

    public abstract FavoriteDAOHotel favoriteDAOHotels();
}
