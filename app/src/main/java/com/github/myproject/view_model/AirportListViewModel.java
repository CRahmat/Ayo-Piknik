package com.github.myproject.view_model;

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

public class AirportListViewModel extends ViewModel {

    private ApiMain apiMain;
    private MutableLiveData<ArrayList<PlacesResultsItem>> listAirport = new MutableLiveData<>();

    public void setModelHotel() {
        if (this.apiMain == null) {
            apiMain = new ApiMain();
        }
        apiMain.getListAirport().getAirport().enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                PlacesResponse responseAirportModel = response.body();
                if (responseAirportModel != null && responseAirportModel.getResults() != null) {
                    ArrayList<PlacesResultsItem> resultsItemsAirportModel = (ArrayList<PlacesResultsItem>) responseAirportModel.getResults();
                    listAirport.postValue(resultsItemsAirportModel);
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<PlacesResultsItem>> getModelHotel() {
        return listAirport;
    }
}
