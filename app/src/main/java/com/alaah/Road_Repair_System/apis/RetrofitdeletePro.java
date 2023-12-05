package com.alaah.Road_Repair_System.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitdeletePro {

    private static RetrofitdeletePro instance = null;
    private APIServices myAPIService;

    private RetrofitdeletePro() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.DELETE_PRODUCT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(APIServices.class);

    }

    public static synchronized RetrofitdeletePro getInstance() {
        if (instance == null) {
            instance = new RetrofitdeletePro();
        }
        return instance;
    }

    public APIServices getMyApi() {
        return myAPIService;
    }
}
