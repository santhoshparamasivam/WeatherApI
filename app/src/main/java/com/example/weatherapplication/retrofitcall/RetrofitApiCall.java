package com.example.weatherapplication.retrofitcall;

import android.util.Log;

import com.example.weatherapplication.MainContract;
import com.example.weatherapplication.model.WeatherModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiCall implements MainContract.GetWeatherIntractor {
    @Override
    public void getNoticeArrayList(final OnFinishedListener onFinishedListener) {

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
                        onFinishedListener.onFinished(data);
                    }
                }else{
                    onFinishedListener.onFailure("exception");
                }

            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                t.printStackTrace();
                onFinishedListener.onFailure("please try again later");
            }
        });

    }
}