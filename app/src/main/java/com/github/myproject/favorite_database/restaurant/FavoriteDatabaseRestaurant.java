package com.github.myproject.favorite_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteData.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDAO favoriteDAO();
    private static FavoriteDatabase favoriteDatabase;

    public static FavoriteDatabase database(Context context){
        if (favoriteDatabase == null){
            favoriteDatabase = Room.databaseBuilder(context, FavoriteDatabase.class, "favoriteDatabase").allowMainThreadQueries().build();
        }
        return favoriteDatabase;
    }
}
