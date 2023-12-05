package com.alaah.Road_Repair_System.fragments;


import com.alaah.Road_Repair_System.activities.MainActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alaah.Road_Repair_System.R;
import com.alaah.Road_Repair_System.apis.RetrofitUpdate;
import com.alaah.Road_Repair_System.model.Result;
import com.alaah.Road_Repair_System.model.User;
import com.alaah.Road_Repair_System.sharedPref.SharedPrefManager;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextInputLayout name,password,id,phone;
    Button updateBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        name = (TextInputLayout) getView().findViewById(R.id.TextInputUsername);
        id = getView().findViewById(R.id.TextInputEmail);
        password = getView().findViewById(R.id.TextInputPassword);
        phone = getView().findViewById(R.id.TextInputPhone);


        //get the Info of current user
        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        name.getEditText().setText(user.getName());
        password.getEditText().setText(user.getPassword());
        id.getEditText().setText(user.getId());
        phone.getEditText().setText(user.getPhone());

        updateBtn = getView().findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Call<Result> call = RetrofitUpdate.getInstance().getMyApi().updateUser(id.getEditText().getText().toString(),name.getEditText().getText().toString(),password.getEditText().getText().toString(),phone.getEditText().getText().toString());
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {

                        if (response.body().getError() == true || response.body().getUser().getName() == null) {
                            Toast.makeText(getContext(), "response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        } else {


                            User user = new User(response.body().getUser().getId(), response.body().getUser().getName(), response.body().getUser().getPassword(), response.body().getUser().getPhone());
                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getContext()).userUpdate(user);
                            //reload the info of navigation drawer header
                            MainActivity object = new MainActivity();
                            object.setHeaderInfo();

                            Toast.makeText(getContext(), "response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getContext(), "something goes wrong!! ===> "+t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("Error 102 --> ",t.getMessage());

                    }
                });



            }
        });

    }
}


