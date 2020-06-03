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

public class ChurchListViewModel extends ViewModel {

    private ApiMain apiMain;
    private MutableLiveData<ArrayList<PlacesResultsItem>> listChurch = new MutableLiveData<>();

    public void setModelHotel() {
        if (this.apiMain == null) {
            apiMain = new ApiMain();
        }
        apiMain.getListChurch().getChurch().enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                PlacesResponse responseCurchModel = response.body();
                if (responseCurchModel != null && responseCurchModel.getResults() != null) {
                    ArrayList<PlacesResultsItem> resultsItemsChurchModel = (ArrayList<PlacesResultsItem>) responseCurchModel.getResults();
                    listChurch.postValue(resultsItemsChurchModel);
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<PlacesResultsItem>> getModelHotel() {
        return listChurch;
    }
}
