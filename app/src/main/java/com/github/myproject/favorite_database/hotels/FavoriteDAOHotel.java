package com.github.myproject.favorite_database.restaurant;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.myproject.favorite_database.FavoriteData;

import java.util.List;

@Dao

public interface FavoriteDAORestaurant {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRepository(FavoriteData favoriteData);

    @Query("SELECT * FROM favorite_database")
    List<FavoriteData> getFavorite();

    @Delete
    void deleteRepositoryData(FavoriteData favoriteData);
}
