package com.alaah.Road_Repair_System.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alaah.Road_Repair_System.R;
import com.alaah.Road_Repair_System.adapter.productAdapter;
import com.alaah.Road_Repair_System.apis.RetrofitClient;
import com.alaah.Road_Repair_System.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentViewReports extends Fragment {

    ListView listView;

    //we should bind fragment class with xml file
    //fragment class should contain two overriden methods
    // 1- onCreateView method is used for binding xml file with activity or java class
    // and 2- onViewCreated method is used for executing command or operation within the activity

    //first step : we want to make override for onCreateView method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_view_reports,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        listView = (ListView) view.findViewById(R.id.listView);

    }

    @Override
    public void onResume() {
        super.onResume();
        Call<List<Product>> call = RetrofitClient.getInstance().getMyApi().getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> productList = response.body();
                productAdapter adapter = new productAdapter(getContext(),productList);
                listView.setAdapter(adapter);
                Toast.makeText(getContext(), "connected successfully :",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "No Problems", Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
             //   Log.d("Retrofit ERROR -->",t.getMessage());
            }
        });
    }
}
