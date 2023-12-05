package com.alaah.Road_Repair_System.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroitAssignUser {

    private static RetroitAssignUser instance = null;
    private APIServices myAPIService;

    private RetroitAssignUser() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.ASSIGN_USER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(APIServices.class);

    }

    public static synchronized RetroitAssignUser getInstance() {
        if (instance == null) {
            instance = new RetroitAssignUser();
        }
        return instance;
    }

    public APIServices getMyApi() {
        return myAPIService;
    }
}
