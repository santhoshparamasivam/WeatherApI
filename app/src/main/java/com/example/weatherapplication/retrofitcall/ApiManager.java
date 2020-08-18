package com.example.weatherapplication.retrofitcall;

import com.example.weatherapplication.model.WeatherModel;

import java.util.List;
 
import retrofit2.Call;
import retrofit2.http.GET;
 
public interface ApiManager {
 
    String BASE_URL = "api.openweathermap.org/data/2.5/forecast?lat=35&lon=139";

//    @GET("/data/2.5/forecast?lat=35&lon=139&appid=8282bdb983dda4f4355026544c574ca4")
    @GET("data/2.5/forecast?lat=11.1085&lon=77.3411&appid=8282bdb983dda4f4355026544c574ca4")
    Call<WeatherModel> getWetherDetails();
}