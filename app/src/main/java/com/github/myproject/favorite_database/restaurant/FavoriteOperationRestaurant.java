package com.github.myproject.favorite_database.restaurant;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.github.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteOperationRestaurant implements FavoriteContractRestaurant.presenter {
    FavoriteDAORestaurant favoriteDAORestaurant;
    List<FavoriteDataRestaurant> favoriteDataRestaurantList = new ArrayList<>();
    private FavoriteContractRestaurant.view viewContract;
    private Context context;

    public FavoriteOperationRestaurant(FavoriteContractRestaurant.view viewContract) {
        this.viewContract = viewContract;
    }

    public FavoriteOperationRestaurant(Context context) {
        this.context = context;
    }

    public FavoriteOperationRestaurant() {

    }

    @Override
    public void insertFavoriteDataRestaurant(String identity, String name, String vicinity, Double rating, String image, final FavoriteDatabaseRestaurant favoriteDatabaseRestaurant) {
        if (favoriteDataRestaurantList.size() == 0) {
            final FavoriteDataRestaurant favoriteDataRestaurant = new FavoriteDataRestaurant();
            favoriteDataRestaurant.setName(name);
            favoriteDataRestaurant.setVicinity(vicinity);
            favoriteDataRestaurant.setRating(rating);
            favoriteDataRestaurant.setIdentity(identity);
            favoriteDataRestaurant.setImage(image);
            new InsertData(favoriteDatabaseRestaurant, favoriteDataRestaurant).execute();
        } else {
            for (int i = 0; i < favoriteDataRestaurantList.size(); i++) {
                if (favoriteDataRestaurantList.get(i).getName() == name) {
                    viewContract.dataAlready();
                } else {
                    final FavoriteDataRestaurant favoriteDataRestaurant = new FavoriteDataRestaurant();
                    favoriteDataRestaurant.setName(name);
                    favoriteDataRestaurant.setVicinity(vicinity);
                    favoriteDataRestaurant.setRating(rating);
                    favoriteDataRestaurant.setIdentity(identity);
                    favoriteDataRestaurant.setImage(image);
                    new InsertData(favoriteDatabaseRestaurant, favoriteDataRestaurant).execute();
                }
            }
        }
    }

    @Override
    public void readFavoriteDataRestaurant(FavoriteDatabaseRestaurant favoriteDatabaseRestaurant, Context context) {
        favoriteDatabaseRestaurant = FavoriteDatabaseRestaurant.database(context);
        favoriteDAORestaurant = favoriteDatabaseRestaurant.favoriteDAORestaurant();
        favoriteDataRestaurantList = favoriteDatabaseRestaurant.favoriteDAORestaurant().getFavorite();
        viewContract.getDataRestaurant(favoriteDataRestaurantList);
    }

    @Override
    public void deleteFavoriteDataRestaurant(final FavoriteDataRestaurant favoriteDataRestaurant,
                                             final FavoriteDatabaseRestaurant favoriteDatabaseRestaurant) {
        new DeleteRepositoryData(favoriteDatabaseRestaurant, favoriteDataRestaurant).execute();
    }

    class InsertData extends AsyncTask<Void, Void, Long> {
        private FavoriteDatabaseRestaurant favoriteDatabaseRestaurant;
        private FavoriteDataRestaurant favoriteDataRestaurant;

        public InsertData(FavoriteDatabaseRestaurant favoriteDatabaseRestaurant, FavoriteDataRestaurant favoriteDataRestaurant) {
            this.favoriteDatabaseRestaurant = favoriteDatabaseRestaurant;
            this.favoriteDataRestaurant = favoriteDataRestaurant;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return favoriteDatabaseRestaurant.favoriteDAORestaurant().insertRepository(favoriteDataRestaurant);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Toast.makeText(context, R.string.success_add_favorite, Toast.LENGTH_SHORT).show();
        }

    }

    class DeleteRepositoryData extends AsyncTask<Void, Void, Void> {
        private FavoriteDatabaseRestaurant favoriteDatabaseRestaurant;
        private FavoriteDataRestaurant favoriteDataRestaurant;

        public DeleteRepositoryData(FavoriteDatabaseRestaurant favoriteDatabaseRestaurant, FavoriteDataRestaurant favoriteDataRestaurant) {
            this.favoriteDatabaseRestaurant = favoriteDatabaseRestaurant;
            this.favoriteDataRestaurant = favoriteDataRestaurant;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDatabaseRestaurant.favoriteDAORestaurant().deleteRepositoryData(favoriteDataRestaurant);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewContract.successDelete();
        }

    }

}