package com.alaah.Road_Repair_System.sharedPref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.alaah.Road_Repair_System.activities.SignIn;
import com.alaah.Road_Repair_System.model.User;

public class SharedPrefManager {


    //preferences info
    private static final String SHARED_PREF_NAME = "EStoreSharedPref";
    private static final String KEY_ID = "keyId";
    private static final String KEY_USERNAME = "keyUsername";
    private static final String KEY_PASSWORD = "keyPassword";
    private static final String KEY_PHONE = "keyPhone";



    private static SharedPrefManager mInstance;

    //context class connects Android OS with project's recources and it access to the activities info
    private static Context mCtx;

    //private constructor to apply singlton principle
    private SharedPrefManager(Context context){

        mCtx = context;
    }

    //synchronized : used to ensure that the info within shared Preferences use just one thread
    public static synchronized SharedPrefManager getInstance(Context context){

        if(mInstance == null){

            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public void userLogin(User user){

        //SharedPreferences is already identified in android as a class
        //MODE_PRIVE is used to prevent sharing data between apps because data in shared preferences should be secured
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //editor is used for edit info or receive and store info
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Using editor object we will store new info
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE,user.getPhone());
        //should apply to store all info successfully
        editor.apply();
    }

    public void userUpdate(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.apply();
    }

  

    public User getUsers(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return new User(
                sharedPreferences.getString(KEY_ID,null),
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString(KEY_PASSWORD,null),
                sharedPreferences.getString(KEY_PHONE,null)
        );
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_USERNAME,null) != null;
    }


    public void Logout(){

        //SharedPreferences is already identified in android as a class
        //MODE_PRIVE is used to prevent sharing data between apps because data in shared preferences should be secured
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //editor is used for edit info or receive and store info
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

        Intent i = new Intent(mCtx, SignIn.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(i);

    }
}
