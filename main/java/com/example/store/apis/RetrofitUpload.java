package com.example.store.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUpload {

    private static RetrofitUpload instance = null;
    private APIServices myAPIServices;

    private RetrofitUpload() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.UP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIServices = retrofit.create(APIServices.class);

    }

    public static synchronized RetrofitUpload getInstance() {
        if (instance == null) {
            instance = new RetrofitUpload();
        }
        return instance;
    }

    public APIServices getMyApi() {
        return myAPIServices;
    }
}

