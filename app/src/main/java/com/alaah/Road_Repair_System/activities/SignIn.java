package com.alaah.Road_Repair_System.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alaah.Road_Repair_System.R;
import com.alaah.Road_Repair_System.apis.RetrofitSignIn;
import com.alaah.Road_Repair_System.model.Result;
import com.alaah.Road_Repair_System.model.User;
import com.alaah.Road_Repair_System.sharedPref.SharedPrefManager;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity implements View.OnClickListener {


    private TextInputLayout EmailTextInputLayout, PasswordTextInputLayout;
    private Button buttonSignIn;
    //private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        EmailTextInputLayout = (TextInputLayout) findViewById(R.id.TextInputEmailLayout);
        PasswordTextInputLayout = (TextInputLayout) findViewById(R.id.TextInputPassword);

        buttonSignIn = (Button) findViewById(R.id.signInBtn);
      //  signUp = (TextView) findViewById(R.id.signUpActivity);


        buttonSignIn.setOnClickListener(this);
       // signUp.setOnClickListener(this);
    }

    private void userSignIn() {

        String id = EmailTextInputLayout.getEditText().getText().toString().trim();
        String password = PasswordTextInputLayout.getEditText().getText().toString().trim();

        Call<Result> call = RetrofitSignIn.getInstance().getMyApi().userLogin(id,password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (!response.body().getError()) {

                    Intent intent = new Intent(SignIn.this,MainActivity.class);
                    //last method is the getter method which is inside User model class
                    //getUser exist inside Result model class
                   /* intent.putExtra("name",response.body().getUser().getName());
                    intent.putExtra("phone",response.body().getUser().getPhone());
                    intent.putExtra("email",response.body().getUser().getEmail());
*/
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Welcome "+response.body().getUser().getName(),Toast.LENGTH_LONG).show();


                    User user = new User(response.body().getUser().getId(),response.body().getUser().getName(),response.body().getUser().getPassword(),(String)response.body().getUser().getPhone());

                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid ID or password ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {


                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Retrofit ERROR -->",t.getMessage());

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == buttonSignIn) {
            userSignIn();
        }//else if(view == signUp){

          //  startActivity(new Intent(getApplicationContext(),signUp.class));
       // }
    }
}