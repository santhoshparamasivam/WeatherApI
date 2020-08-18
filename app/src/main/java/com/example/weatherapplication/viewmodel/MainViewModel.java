package com.example.weatherapplication.viewmodel;

import android.util.Log;

import com.example.weatherapplication.model.WeatherModel;
import com.example.weatherapplication.retrofitcall.ApiManager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    MutableLiveData<ArrayList<WeatherModel>> userLiveData;

    public MainViewModel() {
        userLiveData = new MutableLiveData<>();

        init();
    }

    public MutableLiveData<ArrayList<WeatherModel>> getUserMutableLiveData() {
        return userLiveData;
    }

    public void init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiManager apiManager = retrofit.create(ApiManager.class);

        Call<WeatherModel> call = apiManager.getWetherDetails();

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                ArrayList<WeatherModel>data=new ArrayList<>();

                if (response.code()==200) {
                    if (response.body() != null) {
                        data.add(response.body());
                        userLiveData.setValue(data);
                    }
                }

            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }


}