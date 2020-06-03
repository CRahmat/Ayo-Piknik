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

public class MosqueListViewModel extends ViewModel {

    private ApiMain apiMain;
    private MutableLiveData<ArrayList<PlacesResultsItem>> listMosque = new MutableLiveData<>();

    public void setModelHotel() {
        if (this.apiMain == null) {
            apiMain = new ApiMain();
        }
        apiMain.getListMosque().getMosque().enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                PlacesResponse responseMosqueModel = response.body();
                if (responseMosqueModel != null && responseMosqueModel.getResults() != null) {
                    ArrayList<PlacesResultsItem> resultsItemsMosqueModel = (ArrayList<PlacesResultsItem>) responseMosqueModel.getResults();
                    listMosque.postValue(resultsItemsMosqueModel);
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<PlacesResultsItem>> getModelHotel() {
        return listMosque;
    }
}
