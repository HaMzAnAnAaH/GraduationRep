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
import com.alaah.Road_Repair_System.adapter.productadapter2;
import com.alaah.Road_Repair_System.apis.RetrfoitMyinfo;
import com.alaah.Road_Repair_System.model.Product;
import com.alaah.Road_Repair_System.model.User;
import com.alaah.Road_Repair_System.sharedPref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentViewMylist extends Fragment{

    ListView listView1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_mylist,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        listView1 = (ListView) view.findViewById(R.id.listView1);

    }

    @Override
    public void onResume() {
        super.onResume();

        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        String Id;
        Id = user.getId();
        int convertedId = Integer.parseInt(Id);
        Call<List<Product>> call = RetrfoitMyinfo.getInstance().getmyApi().getProductinfo(convertedId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> productList = response.body();
                productadapter2 adapter = new productadapter2(getContext(), productList);
                listView1.setAdapter(adapter);
                Toast.makeText(getContext(), "connected successfully :", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "No Problems Assigned", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
               // Log.d("Retrofit ERROR -->", t.getMessage());
            }
        });
    }
}
