package com.alaah.Road_Repair_System.apis;

import com.alaah.Road_Repair_System.model.Product;
import com.alaah.Road_Repair_System.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIServices {

    String BASE_URL= "http://10.3.147.201/store/API2.php/";
    String RETRIEVE_URL = "http://10.3.147.201/store/APIID.php/";
    String RETRIEVEInfo_URl = "http://10.3.147.201/store/Myinfo.php/";
    @GET("API")
    Call<List<Product>> getProducts();


    @GET("APIID")
    Call<Product> getProduct(@Query("id") int id);

    @GET("Myinfo")
    Call<List<Product>> getProductinfo(@Query("id") int id);

    /* the SignIn call */
    @FormUrlEncoded
    @POST("checkUser")
    Call<Result> userLogin(
            @Field("id") String id,
            @Field("password") String password
    );

    /*The SignUp Call */
    @FormUrlEncoded
    @POST("InsertUser")
    Call<Result>  insertUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("ID") String id,
            @Field("phone") String phone

    );



    @FormUrlEncoded
    @POST("updateUser")
    Call<Result> updateUser(
            @Field("id") String id,
            @Field("name") String name,
            @Field("password") String password,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("deleteUser")
    Call<Result> deleteUser(
            @Field("id") String id

    );

    @FormUrlEncoded
    @POST("AssignUser")
    Call<Result> AssignUser(
            @Field("id") String Userid,
            @Field("idd") int productid
    );

    @FormUrlEncoded
    @POST("InsertInfo")
    Call<Result> insertInfo(
            @Field("id") int id,
            @Field("Date") String date,
            @Field("imagee") String name



    );

    @FormUrlEncoded
    @POST("deletePro")
    Call<Result> deletepro(
            @Field("id") int id

    );

    @FormUrlEncoded
    @POST("updatePro")
    Call<Result> updatepro(
            @Field("id") int id

    );
}
