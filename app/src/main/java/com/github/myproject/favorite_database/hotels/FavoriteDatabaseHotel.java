package com.github.myproject.favorite_database.restaurant;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.github.myproject.favorite_database.FavoriteDAO;
import com.github.myproject.favorite_database.FavoriteData;

@Database(entities = {FavoriteData.class}, version = 1)
public abstract class FavoriteDatabaseRestaurant extends RoomDatabase {
    public abstract FavoriteDAO favoriteDAO();
    private static FavoriteDatabaseRestaurant favoriteDatabase;

    public static FavoriteDatabaseRestaurant database(Context context){
        if (favoriteDatabase == null){
            favoriteDatabase = Room.databaseBuilder(context, FavoriteDatabaseRestaurant.class, "favoriteDatabase").allowMainThreadQueries().build();
        }
        return favoriteDatabase;
    }
}
