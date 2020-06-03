package com.github.myproject.favorite_database.hotels;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.github.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteOperationHotel implements FavoriteContractHotel.presenter {
    FavoriteDAOHotel favoriteDAOHotel;
    List<FavoriteDataHotel> favoriteDataHotelList = new ArrayList<>();
    private FavoriteContractHotel.view viewContract;
    private Context context;

    public FavoriteOperationHotel(FavoriteContractHotel.view viewContract) {
        this.viewContract = viewContract;
    }

    public FavoriteOperationHotel(Context context) {
        this.context = context;
    }

    public FavoriteOperationHotel() {

    }

    @Override
    public void insertFavoriteDataHotel(String identity, String name, String vicinity, Double rating, String image, final FavoriteDatabaseHotel favoriteDatabaseHotel) {
        if (favoriteDataHotelList.size() == 0) {
            final FavoriteDataHotel favoriteDataHotel = new FavoriteDataHotel();
            favoriteDataHotel.setName(name);
            favoriteDataHotel.setVicinity(vicinity);
            favoriteDataHotel.setRating(rating);
            favoriteDataHotel.setIdentity(identity);
            favoriteDataHotel.setImage(image);
            new InsertData(favoriteDatabaseHotel, favoriteDataHotel).execute();
        } else {
            for (int i = 0; i < favoriteDataHotelList.size(); i++) {
                if (favoriteDataHotelList.get(i).getName() == name) {
                    viewContract.dataAlready();
                } else {
                    final FavoriteDataHotel favoriteDataHotel = new FavoriteDataHotel();
                    favoriteDataHotel.setName(name);
                    favoriteDataHotel.setVicinity(vicinity);
                    favoriteDataHotel.setRating(rating);
                    favoriteDataHotel.setIdentity(identity);
                    favoriteDataHotel.setImage(image);
                    new InsertData(favoriteDatabaseHotel, favoriteDataHotel).execute();
                }
            }
        }
    }

    @Override
    public void readFavoriteDataHotel(FavoriteDatabaseHotel favoriteDatabaseHotel, Context context) {
        favoriteDatabaseHotel = FavoriteDatabaseHotel.database(context);
        favoriteDAOHotel = favoriteDatabaseHotel.favoriteDAOHotels();
        favoriteDataHotelList = favoriteDatabaseHotel.favoriteDAOHotels().getFavorite();
        viewContract.getDataHotel(favoriteDataHotelList);
    }

    @Override
    public void deleteFavoriteDataHotel(final FavoriteDataHotel favoriteDataHotel,
                                        final FavoriteDatabaseHotel favoriteDatabaseHotel) {
        new DeleteRepositoryData(favoriteDatabaseHotel, favoriteDataHotel).execute();
    }

    class InsertData extends AsyncTask<Void, Void, Long> {
        private FavoriteDatabaseHotel favoriteDatabaseHotel;
        private FavoriteDataHotel favoriteDataHotel;

        public InsertData(FavoriteDatabaseHotel favoriteDatabaseHotel, FavoriteDataHotel favoriteDataHotel) {
            this.favoriteDatabaseHotel = favoriteDatabaseHotel;
            this.favoriteDataHotel = favoriteDataHotel;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return favoriteDatabaseHotel.favoriteDAOHotels().insertRepository(favoriteDataHotel);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Toast.makeText(context, R.string.success_add_favorite, Toast.LENGTH_SHORT).show();
        }

    }

    class DeleteRepositoryData extends AsyncTask<Void, Void, Void> {
        private FavoriteDatabaseHotel favoriteDatabaseHotel;
        private FavoriteDataHotel favoriteDataHotel;

        public DeleteRepositoryData(FavoriteDatabaseHotel favoriteDatabaseHotel, FavoriteDataHotel favoriteDataHotel) {
            this.favoriteDatabaseHotel = favoriteDatabaseHotel;
            this.favoriteDataHotel = favoriteDataHotel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDatabaseHotel.favoriteDAOHotels().deleteRepositoryData(favoriteDataHotel);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewContract.successDelete();
        }

    }

}