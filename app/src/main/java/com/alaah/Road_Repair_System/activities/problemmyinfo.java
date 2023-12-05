package com.alaah.Road_Repair_System.activities;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alaah.Road_Repair_System.R;
import com.alaah.Road_Repair_System.apis.RetrofitReportData;
import com.alaah.Road_Repair_System.apis.Retrofitretrieve;
import com.alaah.Road_Repair_System.apis.RetrofitupdatePro;
import com.alaah.Road_Repair_System.model.Product;
import com.alaah.Road_Repair_System.model.Result;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class problemmyinfo extends AppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private TextView Txt1;
    private TextView Txt2;
    private TextView Txt3;
    private TextView Txt4;
    private TextView Txt5;
    private TextView txt7;
    private Button unassign;
    private Button repaired;
    private Button CamBtn;
    private Bitmap photo;
    private TextView rejText;
    private TextInputLayout date;
    private final static int REQUEST_CODE = 100;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_mylist);
        Txt1 = (TextView) findViewById(R.id.txt31);
        Txt2 = (TextView) findViewById(R.id.txt41);
        Txt3 = (TextView) findViewById(R.id.txt51);
        Txt4 = (TextView) findViewById(R.id.txt61);
        Txt5 = (TextView) findViewById(R.id.txtType);
        txt7 = (TextView) findViewById(R.id.open2);
        img = (ImageView) findViewById(R.id.imageview1);
       date = (TextInputLayout) findViewById(R.id.dateentry);
        unassign = (Button) findViewById(R.id.button);
        unassign.setOnClickListener(this);
        repaired =(Button)  findViewById(R.id.button24);
        repaired.setOnClickListener(this);
        CamBtn = (Button) findViewById(R.id.button2);

        rejText = (TextView) findViewById(R.id.reject);

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
                    Txt4.setText(product.getAss());
                    Txt5.setText(product.getName());
                    rejText.setText(product.getStat());
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

        CamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(),"Cam Button",Toast.LENGTH_SHORT).show();

                checkCameraHardware(getApplicationContext());
            }
        });

    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Toast.makeText(getApplicationContext(),"this device has a camera :) ",Toast.LENGTH_SHORT).show();

            try {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent,101);

            }

            catch (Exception e){

                // Camera is not available (in use or does not exist)
                Toast.makeText(getApplicationContext(),"Camera is not available (in use or does not exist) ",Toast.LENGTH_LONG).show();
            }



            return true;
        } else {
            // no camera on this device
            Toast.makeText(getApplicationContext(),"no camera on this device ",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    @CallSuper
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == 101 && resultCode == Activity.RESULT_OK)
        {
            super.onActivityResult(requestCode, resultCode, data);
            photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
        }
    }

    public void unassign(){
        final String assignValue = "Unassigned";
        String gg = Txt1.getText().toString();
        int value = Integer.parseInt(gg);
        Call<Result> call = RetrofitupdatePro.getInstance().getMyApi().updatepro(value);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update the TextView and any other relevant UI elements
                    Txt4.setText(assignValue);
                    // Show a success message or perform any additional actions
                    Toast.makeText(getApplicationContext(), "UnAssigned successful", Toast.LENGTH_SHORT).show();
                }  else {
                    // Handle unsuccessful response
                    Toast.makeText(getApplicationContext(), "UnAssign failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void repair(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        RequestBody requestBody = null;
        String base64Image = null;
        if (photo != null) {
            photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
            requestBody = RequestBody.create(MediaType.parse("image/jpeg"), base64Image); }
        String datee= date.getEditText().getText().toString().trim();

        String text = Txt1.getText().toString();
        int value = Integer.parseInt(text);
        Call<Result> call = RetrofitReportData.getInstance().getMyApi().insertInfo(value,datee,base64Image);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Toast.makeText(getApplicationContext(), "Repaired Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable To Repair", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View view) {
        if (view == unassign) {
           unassign();
        }else if(view == repaired){
         repair();
         }
    }


}
