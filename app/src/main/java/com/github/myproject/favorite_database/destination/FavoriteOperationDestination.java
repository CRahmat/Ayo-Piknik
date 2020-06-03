package com.github.myproject.favorite_database.destination;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.github.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteOperationDestination implements FavoriteContractDestination.presenter {
    FavoriteDAODestination favoriteDAODestination;
    List<FavoriteDataDestination> favoriteDatumDestinations = new ArrayList<>();
    private FavoriteContractDestination.view viewContract;
    private Context context;

    public FavoriteOperationDestination(FavoriteContractDestination.view viewContract) {
        this.viewContract = viewContract;
    }

    public FavoriteOperationDestination(Context context) {
        this.context = context;
    }

    public FavoriteOperationDestination() {

    }

    @Override
    public void insertFavoriteData(String identity, String name, String vicinity, Double rating, String image, final FavoriteDatabaseDestination favoriteDatabaseDestination) {
        if (favoriteDatumDestinations.size() == 0) {
            final FavoriteDataDestination favoriteDataDestination = new FavoriteDataDestination();
            favoriteDataDestination.setName(name);
            favoriteDataDestination.setVicinity(vicinity);
            favoriteDataDestination.setRating(rating);
            favoriteDataDestination.setIdentity(identity);
            favoriteDataDestination.setImage(image);
            new InsertData(favoriteDatabaseDestination, favoriteDataDestination).execute();
        } else {
            for (int i = 0; i < favoriteDatumDestinations.size(); i++) {
                if (favoriteDatumDestinations.get(i).getName() == name) {
                    viewContract.dataAlready();
                } else {
                    final FavoriteDataDestination favoriteDataDestination = new FavoriteDataDestination();
                    favoriteDataDestination.setName(name);
                    favoriteDataDestination.setVicinity(vicinity);
                    favoriteDataDestination.setRating(rating);
                    favoriteDataDestination.setIdentity(identity);
                    favoriteDataDestination.setImage(image);
                    new InsertData(favoriteDatabaseDestination, favoriteDataDestination).execute();
                }
            }
        }
    }

    @Override
    public void readFavoriteData(FavoriteDatabaseDestination favoriteDatabaseDestination, Context context) {
        favoriteDatabaseDestination = FavoriteDatabaseDestination.database(context);
        favoriteDAODestination = favoriteDatabaseDestination.favoriteDAO();
        favoriteDatumDestinations = favoriteDatabaseDestination.favoriteDAO().getFavorite();
        viewContract.getData(favoriteDatumDestinations);
    }

    @Override
    public void deleteFavoriteData(final FavoriteDataDestination favoriteDataDestination,
                                   final FavoriteDatabaseDestination favoriteDatabaseDestination) {
        new DeleteRepositoryData(favoriteDatabaseDestination, favoriteDataDestination).execute();
    }

    class InsertData extends AsyncTask<Void, Void, Long> {
        private FavoriteDatabaseDestination favoriteDatabaseDestination;
        private FavoriteDataDestination favoriteDataDestination;

        public InsertData(FavoriteDatabaseDestination favoriteDatabaseDestination, FavoriteDataDestination favoriteDataDestination) {
            this.favoriteDatabaseDestination = favoriteDatabaseDestination;
            this.favoriteDataDestination = favoriteDataDestination;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return favoriteDatabaseDestination.favoriteDAO().insertRepository(favoriteDataDestination);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Toast.makeText(context, R.string.success_add_favorite, Toast.LENGTH_SHORT).show();
        }

    }

    class DeleteRepositoryData extends AsyncTask<Void, Void, Void> {
        private FavoriteDatabaseDestination favoriteDatabaseDestination;
        private FavoriteDataDestination favoriteDataDestination;

        public DeleteRepositoryData(FavoriteDatabaseDestination favoriteDatabaseDestination, FavoriteDataDestination favoriteDataDestination) {
            this.favoriteDatabaseDestination = favoriteDatabaseDestination;
            this.favoriteDataDestination = favoriteDataDestination;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDatabaseDestination.favoriteDAO().deleteRepositoryData(favoriteDataDestination);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

}