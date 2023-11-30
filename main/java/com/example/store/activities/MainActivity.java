package com.example.store.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.adapter.productAdapter;
import com.example.store.apis.RetrofitClient;
import com.example.store.fragments.ContactUs;
import com.example.store.fragments.ProfileFragment;
import com.example.store.fragments.SettingsFragment;
import com.example.store.fragments.addReportFragment;
import com.example.store.fragments.fragmentViewReports;
import com.example.store.model.Product;
import com.example.store.sharedPref.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentTransaction mFragmentTransaction;
    public static TextView navUsername,navEmail,navPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check if user logsIn or not
        if (!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), SignIn.class));
        }


        drawerLayout = (DrawerLayout) findViewById(R.id.navLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //define the header of navigation view
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.user_name_nav);
        navEmail = (TextView) headerView.findViewById(R.id.Email_nav);
        navPhone = (TextView) headerView.findViewById(R.id.Phone_nav);

        //Receive User's Info (SignIn)
        //getIntent receives Info from SignIn activity
      /*  Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");

        navUsername.setText(name);
        navPhone.setText(phone);
        navEmail.setText(email);*/


        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.container,new fragmentViewReports());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();


        setHeaderInfo();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Map<Integer, String> idToStringMap = new HashMap<>();
                idToStringMap.put(R.id.nav_log_out, "logOutButton");
                idToStringMap.put(R.id.nav_home, "HOME");
                idToStringMap.put(R.id.nav_profile, "profile");
                idToStringMap.put(R.id.nav_settings, "settings");
                idToStringMap.put(R.id.nav_contact_us, "contactUs");
                idToStringMap.put(R.id.nav_fav, "fav");
// ...

// ...

                String idString = idToStringMap.get(item.getItemId());

                switch (idString) {
                    case "logOutButton":
                        // handle button click
                        finish();
                        SharedPrefManager.getInstance(getApplicationContext()).Logout();
                        break;
                    case "HOME":
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.container,new fragmentViewReports());
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();

                        break;
                    // ...
                    case "profile":
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.container,new ProfileFragment());
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();
                        break;

                    case "settings":
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.container,new SettingsFragment());
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();
                        break;

                    case "contactUs":
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.container,new ContactUs());
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();
                        break;

                    case "fav":
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.container,new addReportFragment());
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();
                        break;


                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });



       /* navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_log_out:
                    finish();
                    SharedPrefManager.getInstance(getApplicationContext()).Logout();
                }


                return true;
            }
        });*/


        //actionable items: onOptionsMenu method then use getMenuInflater.infalte(R.menu.menusName)
        //app:showasAction(ifRoom,always,never)
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void setHeaderInfo(){
        String name = SharedPrefManager.getInstance(MainActivity.this).getUsers().getName();
        String email = SharedPrefManager.getInstance(MainActivity.this).getUsers().getEmail();
        String phone = SharedPrefManager.getInstance(MainActivity.this).getUsers().getPhone();

        navUsername.setText(name);
        navEmail.setText(email);
        navPhone.setText(phone);


    }
}