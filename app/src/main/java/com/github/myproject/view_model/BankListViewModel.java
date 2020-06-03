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

public class BankListViewModel extends ViewModel {

    private ApiMain apiMain;
    private MutableLiveData<ArrayList<PlacesResultsItem>> listBank = new MutableLiveData<>();


    public void setModelHotel() {
        if (this.apiMain == null) {
            apiMain = new ApiMain();
        }
        apiMain.getListBank().getBank().enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                PlacesResponse responseBankModel = response.body();
                if (responseBankModel != null && responseBankModel.getResults() != null) {
                    ArrayList<PlacesResultsItem> resultsItemsBankModel = (ArrayList<PlacesResultsItem>) responseBankModel.getResults();
                    listBank.postValue(resultsItemsBankModel);
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<PlacesResultsItem>> getModelHotel() {
        return listBank;
    }
}
