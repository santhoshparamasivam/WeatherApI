package com.example.weatherapplication.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapplication.mvvmpattern.MainhomeActivity;
import com.example.weatherapplication.MainContract;

import com.example.weatherapplication.MainPresenterImpl;
import com.example.weatherapplication.R;
import com.example.weatherapplication.RecyclerAdapter;
import com.example.weatherapplication.retrofitcall.RetrofitApiCall;
import com.example.weatherapplication.model.WeatherModel;
import com.example.weatherapplication.sessionmanager.SessionKeys;
import com.example.weatherapplication.sessionmanager.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainContract.MainView {

    SessionManager sessionManager;
    RecyclerView recyclerview;
    public MainContract.presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar =  findViewById(R.id.toolbar_main);
        recyclerview=findViewById(R.id.recyclerview);
        setSupportActionBar(toolbar);
        sessionManager=new SessionManager(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_textView);

        String FirstName=sessionManager.getStringKey(SessionKeys.FIRST_NAME);
        String Email=sessionManager.getStringKey(SessionKeys.EMAIL);
        if (FirstName!=null){
            navUsername.setText(FirstName+"\n"+Email);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerview.setLayoutManager(layoutManager);
        presenter = new MainPresenterImpl(this, new RetrofitApiCall());
        presenter.requestDataFromServer();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displaySelectedScreen(int itemId) {

        switch (itemId) {
            case R.id.nav_item_two:
                Intent intent=new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);

                break;
                case R.id.nav_item_three:
                Intent intenthome=new Intent(HomeActivity.this, MainhomeActivity.class);
                startActivity(intenthome);

                break;
                case R.id.nav_logout:
                sessionManager.logoutUser();
                Intent logintent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(logintent);
                finish();

                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    @Override
    public void setDataToRecyclerView(ArrayList<WeatherModel> weatherArrayList) {
            ArrayList<WeatherModel.List>weatherList=new ArrayList<>();
            for (int i=0;i<weatherArrayList.size();i++){
                weatherList.addAll(weatherArrayList.get(i).getList());
            }
        Log.e("weather list",weatherList.size()+" ");

        RecyclerAdapter adapter = new RecyclerAdapter(this,weatherList);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void onResponseFailure(String throwable) {
        Toast.makeText(HomeActivity.this,
                "Something went wrong...Error message: " + throwable,
                Toast.LENGTH_LONG).show();
    }
}
