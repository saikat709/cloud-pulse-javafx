package com.saikat.cloudpulse.api;

import com.google.gson.Gson;
import com.saikat.cloudpulse.listeners.ApiCallListener;
import com.saikat.cloudpulse.listeners.ApiResponseListener;
import com.saikat.cloudpulse.models.ForecastModel;
import com.saikat.cloudpulse.models.WeatherInfoModel;

public class WeatherInfoAPI extends RetrieveFromApi{
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static String API_KEY = "772ac6cab78f5ea6c606e2bb13741221";
    public final Gson gson = new Gson();

    public void getWeather(ApiCallListener<WeatherInfoModel> listener){
        this.callAPI(getWeatherUrl(), new ApiResponseListener() {
            @Override
            public void onFailure(String message) {
                listener.onApiCallFailure(message);
            }

            @Override
            public void onSuccess(String message) {
                try {
                    WeatherInfoModel weather = gson.fromJson(message, WeatherInfoModel.class);
                    listener.onApiCallSuccess(weather);
                } catch (Exception ex) {
                    listener.onApiCallFailure(ex.getLocalizedMessage());
                }
            }
        });
    }


    public void getForeCast(ApiCallListener<ForecastModel> listener){
        this.callAPI(getWeatherUrl(), new ApiResponseListener() {
            @Override
            public void onFailure(String message) {
                listener.onApiCallFailure(message);
            }

            @Override
            public void onSuccess(String message) {
                try{
                    ForecastModel forecast = gson.fromJson(message, ForecastModel.class);
                    listener.onApiCallSuccess(forecast);
                } catch (Exception ex) {
                    listener.onApiCallFailure(ex.getLocalizedMessage());
                }
            }
        });
    }

    private String getForeCastUrl(){
        return BASE_URL + "forecast?q=" + "London,uk" + "&format=json&APPID=" + API_KEY;
    }

    private String getWeatherUrl(){
        return BASE_URL + "weather?q=" + "London,uk" + "&format=json&APPID=" + API_KEY;
    }
}


// http://api.openweathermap.org/data/2.5/forecast?id=524901&appid={API key}.
// http://api.openweathermap.org/data/2.5/weather?q=Ramna,Dhaka&appid=772ac6cab78f5ea6c606e2bb13741221&units=metric
// https://api.openweathermap.org/data/2.5/weather?q=London,uk&format=json&APPID=72d2a9a649aceea1f85604cac7372f5d
// https://api.openweathermap.org/data/2.5/forecast?q=London,uk&format=json&APPID=72d2a9a649aceea1f85604cac7372f5d