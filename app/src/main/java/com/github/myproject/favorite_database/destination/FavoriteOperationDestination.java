package com.github.myproject.favorite_database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteOperation implements FavoriteContract.presenter {
    private FavoriteContract.view viewContract;
    FavoriteDAO favoriteDAO;
    List<FavoriteData> favoriteData = new ArrayList<>();
    private Context context;
    public FavoriteOperation(FavoriteContract.view viewContract) {
        this.viewContract = viewContract;
    }
    public FavoriteOperation(Context context) {
        this.context = context;
    }

    public FavoriteOperation() {

    }

    class InsertData extends AsyncTask<Void, Void, Long>{
        private FavoriteDatabase favoriteDatabase;
        private FavoriteData favoriteData;

        public InsertData(FavoriteDatabase favoriteDatabase, FavoriteData favoriteData) {
            this.favoriteDatabase = favoriteDatabase;
            this.favoriteData = favoriteData;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return favoriteDatabase.favoriteDAO().insertRepository(favoriteData);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Toast.makeText(context, "Berhasil Menambahkan Ke Favorite", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void insertFavoriteData(String identity, String name, String vicinity, Double rating, final FavoriteDatabase favoriteDatabase) {
        if(favoriteData.size()==0){
            final FavoriteData favoriteData = new FavoriteData();
            favoriteData.setName(name);
            favoriteData.setVicinity(vicinity);
            favoriteData.setRating(rating);
            favoriteData.setIdentity(identity);
            new InsertData(favoriteDatabase, favoriteData).execute();
        }else{
            for(int i = 0; i < favoriteData.size() ; i++){
             if(favoriteData.get(i).getName() == name){
                 viewContract.dataAlready();
             }else {
                 final FavoriteData favoriteData = new FavoriteData();
                 favoriteData.setName(name);
                 favoriteData.setVicinity(vicinity);
                 favoriteData.setRating(rating);
                 favoriteData.setIdentity(identity);
                 new InsertData(favoriteDatabase, favoriteData).execute();
             }
            }
        }
    }

    @Override
    public void readFavoriteData(FavoriteDatabase favoriteDatabase, Context context) {
        favoriteDatabase = FavoriteDatabase.database(context);
        favoriteDAO = favoriteDatabase.favoriteDAO();
        favoriteData = favoriteDatabase.favoriteDAO().getFavorite();
        viewContract.getData(favoriteData);
    }

    class DeleteRepositoryData extends AsyncTask<Void, Void, Void>{
        private FavoriteDatabase favoriteDatabase;
        private FavoriteData favoriteData;

        public DeleteRepositoryData(FavoriteDatabase favoriteDatabase, FavoriteData favoriteData) {
            this.favoriteDatabase = favoriteDatabase;
            this.favoriteData = favoriteData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDatabase.favoriteDAO().deleteRepositoryData(favoriteData);
            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewContract.successDelete();
        }

    }

    @Override
    public void deleteFavoriteData(final FavoriteData favoriteData,
                           final FavoriteDatabase favoriteDatabase) {
        new DeleteRepositoryData(favoriteDatabase, favoriteData).execute();
    }

}