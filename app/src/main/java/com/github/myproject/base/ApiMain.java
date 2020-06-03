package com.github.myproject.base;

import com.github.myproject.api_services.ApiATMClient;
import com.github.myproject.api_services.ApiAirportClient;
import com.github.myproject.api_services.ApiBankClient;
import com.github.myproject.api_services.ApiChurchClient;
import com.github.myproject.api_services.ApiHospitalClient;
import com.github.myproject.api_services.ApiHotelClient;
import com.github.myproject.api_services.ApiMosqueClient;
import com.github.myproject.api_services.ApiRestaurantClient;
import com.github.myproject.api_services.ApiStationClient;
import com.github.myproject.api_services.ApiSuperMarketClient;
import com.github.myproject.api_services.ApiTourismAttractionClient;
import com.github.myproject.api_services.ApiViharaClient;

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

    public ApiMosqueClient getListMosque() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiMosqueClient.class);
    }

    public ApiChurchClient getListChurch() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiChurchClient.class);
    }

    public ApiViharaClient getListVihara() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiViharaClient.class);
    }

    public ApiBankClient getListBank() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiBankClient.class);
    }

    public ApiATMClient getListATM() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiATMClient.class);
    }

    public ApiHospitalClient getListHospital() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiHospitalClient.class);
    }

    public ApiSuperMarketClient getListSuperMarket() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiSuperMarketClient.class);
    }

    public ApiStationClient getListStation() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiStationClient.class);
    }

    public ApiAirportClient getListAirport() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiAirportClient.class);
    }

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
