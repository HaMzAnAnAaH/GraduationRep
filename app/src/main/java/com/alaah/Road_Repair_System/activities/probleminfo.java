package com.alaah.Road_Repair_System.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.alaah.Road_Repair_System.R;
import com.alaah.Road_Repair_System.apis.Retrofitretrieve;
import com.alaah.Road_Repair_System.apis.RetroitAssignUser;
import com.alaah.Road_Repair_System.model.Product;
import com.alaah.Road_Repair_System.model.Result;
import com.alaah.Road_Repair_System.model.User;
import com.alaah.Road_Repair_System.sharedPref.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class probleminfo extends AppCompatActivity implements View.OnClickListener {
    private Button assign;
    private ImageView img;
   private TextView Txt1;
   private TextView Txt2;
   private TextView Txt3;
   private TextView Txt4;
   private TextView txt5;
   private TextView txt7;
   private int id;
   private Context context;
  // private String Userid;
  FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        assign = (Button) findViewById(R.id.button);
        assign.setOnClickListener(this);
        Txt1 = (TextView) findViewById(R.id.txt3);
        Txt2 = (TextView) findViewById(R.id.txt4);
        Txt3 = (TextView) findViewById(R.id.txt5);
        Txt4 = (TextView) findViewById(R.id.txt6);
        img = (ImageView)  findViewById(R.id.image);
        txt5 = (TextView)  findViewById(R.id.txtType);
        txt7 = (TextView) findViewById(R.id.open);
        id = getIntent().getIntExtra("ProductId", 0);

        Call<Product> call = Retrofitretrieve.getInstance().getmyApi().getProduct(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                   Product product = response.body();

                    Glide.with(getApplicationContext())
                            .load("http://10.3.147.201/store/images/" +product.getImg())
                            .apply(new RequestOptions().override(600, 600))
                            .into(img);

                   int id = product.getID();
                    String idString = String.valueOf(id);
                    Txt1.setText(idString);
                    Txt2.setText(product.getDescrip());
                    Txt3.setText(product.getLoc());
                    User user = SharedPrefManager.getInstance(getApplicationContext()).getUsers();
                    Txt4.setText(user.getId());
                    txt5.setText(product.getName());
                    }
                }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
           }
        });
        txt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
    }

    private void openMap() {
        String location = Txt3.getText().toString();
        Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }


    public void assign () {



        String Userid = Txt4.getText().toString();
           String gg = Txt1.getText().toString();
        int value = Integer.parseInt(gg);
            Call<Result> call = RetroitAssignUser.getInstance().getMyApi().AssignUser(Userid,value);
            call.enqueue(new Callback<Result>() {
                @Override
               public void onResponse(Call<Result> call, Response<Result> response) {


                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });
}
    @Override
    public void onClick(View view) {
        if (view == assign) {
          assign();
        }//else if(view == assign){
           // assignp();
        //  startActivity(new Intent(getApplicationContext(),signUp.class));
        // }
    }
    }
