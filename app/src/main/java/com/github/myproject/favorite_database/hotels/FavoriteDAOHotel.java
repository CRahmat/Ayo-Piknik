package com.github.myproject.favorite_database.hotels;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao

public interface FavoriteDAOHotel {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRepository(FavoriteDataHotel favoriteDataHotel);

    @Query("SELECT * FROM favorite_database_hotel")
    List<FavoriteDataHotel> getFavorite();

    @Delete
    void deleteRepositoryData(FavoriteDataHotel favoriteDataHotel);
}
