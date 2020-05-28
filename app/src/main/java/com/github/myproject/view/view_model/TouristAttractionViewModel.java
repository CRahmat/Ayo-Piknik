package com.github.myproject.view.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.myproject.api_services.ApiMain;
import com.github.myproject.model.PlacesResponse;
import com.github.myproject.model.PlacesResultsItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TouristAttractionViewModel extends ViewModel {

    private ApiMain apiMain;
    private MutableLiveData<ArrayList<PlacesResultsItem>> listTouristAttractions = new MutableLiveData<>();

    public void setModelTouristAttractions() {
        if (this.apiMain == null) {
            apiMain = new ApiMain();
        }

        apiMain.getListTouristAttraction().getTouristAttraction().enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                PlacesResponse responseTouristAttractionsModel = response.body();
                if (responseTouristAttractionsModel != null && responseTouristAttractionsModel.getResults() != null) {
                    ArrayList<PlacesResultsItem> resultsItemsTouristAttractionsModel = (ArrayList<PlacesResultsItem>) responseTouristAttractionsModel.getResults();
                    listTouristAttractions.postValue(resultsItemsTouristAttractionsModel);
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<PlacesResultsItem>> getModelTouristAttractions() {
        return listTouristAttractions;
    }
}
