package com.example.store.apis;

import com.example.store.model.Product;
import com.example.store.model.Report;
import com.example.store.model.Result;
import com.example.store.model.modelUpload;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIServices {

    String BASE_URL= "http://192.168.178.154/store/retrieve.php/";

    @GET("retrieve.php")
    Call<List<Product>> getProduct(@Query("id") int convertedId);

    /* the SignIn call */
    @FormUrlEncoded
    @POST("checkUser.php")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    /*The SignUp Call */
    @FormUrlEncoded
    @POST("InsertUser.php")
    Call<Result>  insertUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone

    );

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<Result> updateUser(
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("deleteUser.php")
    Call<Result> deleteUser(
            @Field("id") int id

    );

    @Multipart
    @POST("uploadImg.php")
    Call<modelUpload> uploadFile(@Part MultipartBody.Part file,@Field("image")String image);

    @FormUrlEncoded
    @POST("InsertInfo.php")
    Call<Report> insertInfo(
            @Field("description") String description,
            @Field("Date") String Date,
            @Field("IMAGE") String requestBody,
            @Field("LOCATION") String latLong,
            @Field("Address") String lo,
            @Field("ID") int convertedId,
            @Field("currentTime") String currentTime,
            @Field("result") String result);
}
