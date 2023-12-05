package com.alaah.Road_Repair_System.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitupdatePro {

    private static RetrofitupdatePro instance = null;
    private APIServices myAPIService;

    private RetrofitupdatePro() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.UPDATE_PRODUCT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(APIServices.class);

    }

    public static synchronized RetrofitupdatePro getInstance() {
        if (instance == null) {
            instance = new RetrofitupdatePro();
        }
        return instance;
    }

    public APIServices getMyApi() {
        return myAPIService;
    }
}
