package com.example.store.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitReportData {
    private static RetrofitReportData instance = null;
    private APIServices myAPIService;

    private RetrofitReportData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.REPORT_INFO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPIService = retrofit.create(APIServices.class);

    }

    public static synchronized RetrofitReportData getInstance() {
        if (instance == null) {
            instance = new RetrofitReportData();
        }
        return instance;
    }

    public APIServices getMyApi() {
        return myAPIService;
    }

}

/*
*
* Retrofit retrofit = new Retrofit.Builder(): This line creates a new instance of
* the Retrofit.Builder class, which is responsible
* for building and configuring Retrofit instances.

.baseUrl(APIUrl.SIGN_UP): The baseUrl()
*  method sets the base URL for all the API requests.
*  In this case, the base URL is provided through the APIUrl.SIGN_UP
*  constant. The base URL represents the common part of the API endpoints.

.addConverterFactory(GsonConverterFactory.create()):
*  The addConverterFactory() method adds a converter factory
*
* to handle serialization and deserialization of data between Java objects and
*  the JSON format. In this code, it uses GsonConverterFactory, which is a converter factory that uses Gson library for JSON conversion.
*
*
*  */