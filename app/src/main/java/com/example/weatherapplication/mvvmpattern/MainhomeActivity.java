package com.example.weatherapplication.mvvmpattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weatherapplication.R;
import com.example.weatherapplication.RecyclerAdapter;
import com.example.weatherapplication.model.WeatherModel;
import com.example.weatherapplication.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainhomeActivity extends AppCompatActivity  implements LifecycleOwner {
    MainhomeActivity context;
    MainViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        context = this;
        recyclerView = findViewById(R.id.rv_main);
        viewModel = ViewModelProviders.of(context).get(MainViewModel.class);
        viewModel.getUserMutableLiveData().observe(context, userListUpdateObserver);
    }
    Observer<ArrayList<WeatherModel>> userListUpdateObserver = new Observer<ArrayList<WeatherModel>>() {
        @Override
        public void onChanged(ArrayList<WeatherModel> userArrayList) {
            ArrayList<WeatherModel.List>weatherList=new ArrayList<>();
            for (int i=0;i<userArrayList.size();i++){
                weatherList.addAll(userArrayList.get(i).getList());
            }
            recyclerViewAdapter = new RecyclerAdapter(context, weatherList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };
}