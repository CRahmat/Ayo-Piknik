package com.github.myproject.favorite_database.restaurant;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao

public interface FavoriteDAORestaurant {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRepository(FavoriteDataRestaurant favoriteDataRestaurant);

    @Query("SELECT * FROM favorite_database_restaurant")
    List<FavoriteDataRestaurant> getFavorite();

    @Delete
    void deleteRepositoryData(FavoriteDataRestaurant favoriteDataRestaurant);
}
