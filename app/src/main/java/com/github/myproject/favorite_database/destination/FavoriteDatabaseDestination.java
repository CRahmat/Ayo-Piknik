package com.github.myproject.favorite_database.destination;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteDataDestination.class}, version = 1)
public abstract class FavoriteDatabaseDestination extends RoomDatabase {
    private static FavoriteDatabaseDestination favoriteDatabaseDestination;

    public static FavoriteDatabaseDestination database(Context context) {
        if (favoriteDatabaseDestination == null) {
            favoriteDatabaseDestination = Room.databaseBuilder(context, FavoriteDatabaseDestination.class, "favoriteDatabaseDestination").allowMainThreadQueries().build();
        }
        return favoriteDatabaseDestination;
    }

    public abstract FavoriteDAODestination favoriteDAO();
}
