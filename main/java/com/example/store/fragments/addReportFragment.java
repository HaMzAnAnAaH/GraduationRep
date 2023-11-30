package com.example.store.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.activities.SignIn;
import com.example.store.activities.signUp;
import com.example.store.apis.RetrofitReportData;
import com.example.store.apis.RetrofitSignIn;
import com.example.store.apis.RetrofitUpload;
import com.example.store.model.Report;
import com.example.store.model.Result;
import com.example.store.model.User;
import com.example.store.sharedPref.SharedPrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
public class addReportFragment extends Fragment  {

    Button CamBtn, send;
    TextInputLayout Pdescription, Date;
    ImageView imageView;
    Bitmap photo;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView lattitude,longi,getmap;
    Button getLocation;

     Spinner spinner;
     String result; // Variable to store the selected item
    private final static int REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.addreport, null);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Pdescription = (TextInputLayout) getView().findViewById(R.id.ProblemType);
        Date = (TextInputLayout) getView().findViewById(R.id.TextInputDateLayout);
        send= (Button) getView().findViewById(R.id.sendBtn);
        CamBtn = (Button) getView().findViewById(R.id.buttonCam);
        imageView = (ImageView) getView().findViewById(R.id.imageView);
        lattitude = (TextView) getView().findViewById(R.id.lattitude);
        longi = (TextView) getView().findViewById(R.id.lattitude1);
        getLocation = (Button) getView().findViewById(R.id.getLocation);
        spinner = (Spinner) getView().findViewById(R.id.spinner);
        getmap = (TextView) getView().findViewById(R.id.getmap);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        // Create an ArrayAdapter using a string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Add an item selected listener to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result = parent.getItemAtPosition(position).toString();
                // You can use the "result" variable here or pass it to another method
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocation();

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              /*  //compress the image
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if(photo != null){


                    photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    RequestQueue queue = Volley.newRequestQueue(getContext());
                   // String url ="";
*/



                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                RequestBody requestBody = null;
                String base64Image = null;

                if (photo != null) {
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);



                    // Create a RequestBody from the base64 image string
                    requestBody = RequestBody.create(MediaType.parse("image/jpeg"), base64Image);


                    // Make the API call using Retrofit


                    /*StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                  //  textView.setText("Response is: "+ response);

                                    if(response.equals("success")){
                                        Toast.makeText(getContext(),"image uploaded", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(),"Failed uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                          //  textView.setText("That didn't work!");
                            Toast.makeText(getContext(),error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("image", base64Image);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }else{
                    Toast.makeText(getContext(),"Select the image first", Toast.LENGTH_SHORT).show();*/
                }


                String DesText = Pdescription.getEditText().getText().toString().trim();
                String date = Date.getEditText().getText().toString().trim();
                String lat = lattitude.getText().toString().trim();
                String lo = longi.getText().toString().trim();

                //String output = lat.replace(" - ", "");
                //String lat = lattitude.getText().toString().trim();
                String output = lat.replaceAll("\\s*-\\s*", " ");








                User user = SharedPrefManager.getInstance(getContext()).getUsers();
                String Id;
                Id = String.valueOf(user.getId());
                int convertedId = Integer.parseInt(Id);

                // Get the current time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String currentTime = sdf.format(calendar.getTime());

                Call<Report> call = RetrofitReportData.getInstance().getMyApi().insertInfo(DesText, date, base64Image, output, lo, convertedId, currentTime, result);

                call.enqueue(new Callback<Report>() {
                    @Override
                    public void onResponse(Call<Report> call, Response<Report> response) {


                        Toast.makeText(getContext(), "inserted", Toast.LENGTH_SHORT).show();

                        // Clear the input fields after successful insertion
                        TextInputEditText des = (TextInputEditText) Pdescription.getEditText();
                        TextInputEditText dat = (TextInputEditText) Date.getEditText();

                        if (des != null && dat != null && lattitude != null && longi != null && getmap != null) {
                            des.setText("");
                            dat.setText("");
                            lattitude.setText("");
                            longi.setText("");
                            getmap.setText("");

                        }


                    }

                    @Override
                    public void onFailure(Call<Report> call, Throwable t) {

                        //Toast.makeText(addReportFragment.this,"inserted", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Failed to Insert Data --> " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }



        });



        CamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(),"Cam Button",Toast.LENGTH_SHORT).show();

                checkCameraHardware(getContext());
            }
        });


    }





    //Check if this device has a camera
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Toast.makeText(getContext(),"this device has a camera :) ",Toast.LENGTH_SHORT).show();

            try {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent,101);

            }

            catch (Exception e){

                // Camera is not available (in use or does not exist)
                Toast.makeText(getContext(),"Camera is not available (in use or does not exist) ",Toast.LENGTH_LONG).show();
            }



            return true;
        } else {
            // no camera on this device
            Toast.makeText(getContext(),"no camera on this device ",Toast.LENGTH_LONG).show();
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
            imageView.setImageBitmap(photo);
        }
    }
   /* @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mFragments.noteStateNotSaved();
        super.onActivityResult(requestCode, resultCode, data);
    }*/


    //get the current location method
    private void getLastLocation(){

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){



                                try {
                                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    lattitude.setText(""
                                            +addresses.get(0).getLatitude()+" "+addresses.get(0).getLongitude()

                                            );


                                    longi.setText("Address: "+addresses.get(0).getAddressLine(0)+"|| City: "+addresses.get(0).getLocality()+"|| Country: "+addresses.get(0).getCountryName());



                                    String mapsLink = "https://www.google.com/maps?q=" +addresses.get(0).getLatitude()+" "+addresses.get(0).getLongitude();

                                    // Set the link text as "Show location" for better user experience
                                    getmap.setText("Show location");

                                    // If you want to open the map when the user clicks on the button, you can add an OnClickListener to 'getmap'.
                                    getmap.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // Open the map link in a browser or a map app
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapsLink));
                                            startActivity(intent);
                                        }
                                    });
                                    /*List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    lattitude.setText("Lattitude: "+addresses.get(0).getLatitude());
                                    longitude.setText("Longitude: "+addresses.get(0).getLongitude());
                                    address.setText("Address: "+addresses.get(0).getAddressLine(0));
                                    city.setText("City: "+addresses.get(0).getLocality());
                                    country.setText("Country: "+addresses.get(0).getCountryName());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/





                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    });


        }else {

            askPermission();


        }


    }

    private void askPermission() {

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                getLastLocation();

            }else {


                Toast.makeText(getContext(),"Please provide the required permission",Toast.LENGTH_SHORT).show();

            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}
