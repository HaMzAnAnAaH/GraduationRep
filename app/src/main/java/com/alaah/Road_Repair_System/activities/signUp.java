package com.alaah.Road_Repair_System.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alaah.Road_Repair_System.R;
import com.alaah.Road_Repair_System.apis.RetrofitSignUp;
import com.alaah.Road_Repair_System.model.Result;
import com.google.android.material.textfield.TextInputLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUp extends AppCompatActivity {

    public TextInputLayout name,password,email,phone;
    public Button RegBtn;
    public String Name,Pass,Email,Phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        name = (TextInputLayout) findViewById(R.id.userSignUp);
        password = (TextInputLayout) findViewById(R.id.passwordSignUp);
        email = (TextInputLayout) findViewById(R.id.emailSignUp);
        phone = (TextInputLayout) findViewById(R.id.phoneSignUp);

        RegBtn = (Button) findViewById(R.id.signupBtn);
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Call insertUser method
                insertUser();


            }
        });
    }//End onCreate method

    private void insertUser(){

        //retrieve data from Edit texts
        Name = name.getEditText().getText().toString().trim();
        Pass = password.getEditText().getText().toString().trim();
        Email = email.getEditText().getText().toString().trim();
        Phone = phone.getEditText().getText().toString().trim();

        //Here we will handle the http request to insert user to mysql db


        Call<Result> call = RetrofitSignUp.getInstance().getMyApi().insertUser(Name,Pass,Email,Phone);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {


                if(response.body().getError() == true){

                    Log.d("something goes wrong --- > ", response.body().getMessage());
                    Toast.makeText(signUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }else if(response.body().getError()==false){

                    Log.d("Response ---> ","User registered successfully");

                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(),SignIn.class));


                }



            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Log.d("Failed to Insert Data ---> ",t.getMessage());
                Toast.makeText(getApplicationContext(),"Failed to Insert Data --> "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }//End insertUser method




}