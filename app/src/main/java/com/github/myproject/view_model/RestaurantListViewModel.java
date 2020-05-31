package com.github.myproject.view.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.myproject.base.ApiMain;
import com.github.myproject.model.PlacesResponse;
import com.github.myproject.model.PlacesResultsItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListViewModel extends ViewModel {

    private ApiMain apiMain;
    private MutableLiveData<ArrayList<PlacesResultsItem>> listRestaurants = new MutableLiveData<>();

    public void setModelRestaurants() {
        if (this.apiMain == null) {
            apiMain = new ApiMain();
        }

        apiMain.getListRestaurant().getRestaurant().enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                PlacesResponse responseRestaurantsModel = response.body();
                if (responseRestaurantsModel != null && responseRestaurantsModel.getResults() != null) {
                    ArrayList<PlacesResultsItem> resultsItemsRestaurantsModel = (ArrayList<PlacesResultsItem>) responseRestaurantsModel.getResults();
                    listRestaurants.postValue(resultsItemsRestaurantsModel);
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<PlacesResultsItem>> getModelRestaurants() {
        return listRestaurants;
    }

}
