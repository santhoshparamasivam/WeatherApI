package com.example.weatherapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.weatherapplication.model.WeatherModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.WeatherViewHolder> {



     List<WeatherModel.List> Data_list;
    Context context;

    List<WeatherModel.List> filter_list;
    public RecyclerAdapter(Context context, ArrayList<WeatherModel.List> Data_list) {
        this.context=context;
        this.Data_list=Data_list;
        this.filter_list=Data_list;

    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_list_items, parent,false);
        WeatherViewHolder usersViewHolder = new WeatherViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, final int position) {
        if (filter_list.get(position).getMain().getHumidity()!=null){
            holder.tv_hum.setText(("Humidity "+filter_list.get(position).getMain().getHumidity()));
        }
        if (filter_list.get(position).getMain().getTemp()!=null){
            holder.tv_temp.setText("Temperature "+ filter_list.get(position).getMain().getTemp());
        }
        if (filter_list.get(position).getWind().getSpeed()!=null){
            holder.tv_wind.setText("Wind Speed "+ filter_list.get(position).getWind().getSpeed());
        }
        if (filter_list.get(position).getWeather()!=null){
            holder.tv_rain.setText(filter_list.get(position).getWeather().get(0).getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return filter_list.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tv_wind,tv_hum,tv_temp,tv_rain ;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            tv_wind =  itemView.findViewById(R.id.tv_wind);
            tv_hum =  itemView.findViewById(R.id.tv_humidty);
            tv_temp =  itemView.findViewById(R.id.tv_temp);
            tv_rain =  itemView.findViewById(R.id.tv_rain);



        }
    }




}
