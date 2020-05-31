package com.github.myproject.favorite_database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRepository(FavoriteData favoriteData);

    @Query("SELECT * FROM favorite_database")
    List<FavoriteData> getFavorite();

    @Delete
    void deleteRepositoryData(FavoriteData favoriteData);
}
