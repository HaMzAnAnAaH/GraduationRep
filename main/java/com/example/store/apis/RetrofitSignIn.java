package com.example.store.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSignIn {
    private static RetrofitSignIn instance = null;
    private APIServices myAPIServices;

    private RetrofitSignIn() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.SIGN_IN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIServices = retrofit.create(APIServices.class);

    }

    public static synchronized RetrofitSignIn getInstance() {
        if (instance == null) {
            instance = new RetrofitSignIn();
        }
        return instance;
    }

    public APIServices getMyApi() {
        return myAPIServices;
    }
}