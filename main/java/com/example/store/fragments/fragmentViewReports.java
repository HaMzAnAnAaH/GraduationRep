package com.example.store.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.activities.MainActivity;
import com.example.store.adapter.productAdapter;
import com.example.store.apis.RetrofitClient;
import com.example.store.apis.RetrofitReportData;
import com.example.store.model.Product;
import com.example.store.model.Report;
import com.example.store.model.User;
import com.example.store.sharedPref.SharedPrefManager;

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

        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        String Id;
        Id = String.valueOf(user.getId());
        int convertedId = Integer.parseInt(Id);

        Call<List<Product>> call = RetrofitClient.getInstance().getMyApi().getProduct(convertedId);

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

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Retrofit ERROR -->",t.getMessage());
            }
        });
    }

}
