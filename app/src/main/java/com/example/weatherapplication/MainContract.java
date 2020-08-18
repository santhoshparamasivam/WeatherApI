package com.example.weatherapplication;

import com.example.weatherapplication.model.WeatherModel;

import java.util.ArrayList;

public interface MainContract {



    interface presenter{

        void requestDataFromServer();

    }

    interface MainView {

        void setDataToRecyclerView(ArrayList<WeatherModel> weatherArrayList);

        void onResponseFailure(String throwable);

    }

    interface GetWeatherIntractor {

        interface OnFinishedListener {
            void onFinished(ArrayList<WeatherModel> weatherArrayList);
            void onFailure(String t);
        }

        void getNoticeArrayList(OnFinishedListener onFinishedListener);
    }

}