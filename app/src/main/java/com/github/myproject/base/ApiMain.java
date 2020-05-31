package com.github.myproject.api_services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiMain {

    private final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private Retrofit retrofit;

    public ApiRestaurantClient getListRestaurant() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiRestaurantClient.class);
    }

    ;

    public ApiHotelClient getListHotel() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiHotelClient.class);
    }

    public ApiTourismAttractionClient getListTouristAttraction() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiTourismAttractionClient.class);
    }
}
