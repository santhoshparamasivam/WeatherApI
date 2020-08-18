package com.example.weatherapplication;
import com.example.weatherapplication.MainContract;
import com.example.weatherapplication.model.WeatherModel;

import java.util.ArrayList;

public class MainPresenterImpl implements MainContract.presenter,MainContract.GetWeatherIntractor.OnFinishedListener {

    private MainContract.MainView mainView;
    private MainContract.GetWeatherIntractor getNoticeIntractor;

    public MainPresenterImpl(MainContract.MainView mainView, MainContract.GetWeatherIntractor getNoticeIntractor) {
        this.mainView = mainView;
        this.getNoticeIntractor = getNoticeIntractor;
    }


    @Override
    public void requestDataFromServer() {
        getNoticeIntractor.getNoticeArrayList(this);
    }

    @Override
    public void onFinished(ArrayList<WeatherModel> weatherArrayList) {
        if(mainView != null){
            mainView.setDataToRecyclerView(weatherArrayList);
        }
    }

    @Override
    public void onFailure(String t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
        }
    }
}