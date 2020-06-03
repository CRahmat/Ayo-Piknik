package com.github.myproject.api_services;

import com.github.myproject.model.PlacesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiMosqueClient {
    @GET("json?location=-7.797068,110.370529&radius=50000&type=mosque&key=AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14")
    Call<PlacesResponse> getMosque();

}
