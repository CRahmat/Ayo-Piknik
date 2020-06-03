package com.github.myproject.favorite_database.destination;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao

public interface FavoriteDAODestination {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRepository(FavoriteDataDestination favoriteDataDestination);

    @Query("SELECT * FROM favorite_database_destination")
    List<FavoriteDataDestination> getFavorite();

    @Delete
    void deleteRepositoryData(FavoriteDataDestination favoriteDataDestination);
}
