package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.api.LocationInfoAPI;
import com.saikat.cloudpulse.api.WeatherInfoAPI;
import com.saikat.cloudpulse.listeners.*;
import com.saikat.cloudpulse.models.City;
import com.saikat.cloudpulse.models.ForecastModel;
import com.saikat.cloudpulse.models.LocationInfoModel;
import com.saikat.cloudpulse.models.WeatherInfoModel;
import com.saikat.cloudpulse.screens.ScreenName;
import com.saikat.cloudpulse.utils.APIUrlUtil;

public class DataManager {
    private WeatherInfoModel  weatherInfo;
    private ForecastModel     forecastModel;

    private final LocationInfoAPI locationInfoAPI;
    private final WeatherInfoAPI  weatherInfoAPI;

    private OnDataUploadedListener dataUploadedListener;
    private OnForecastDataLoaded   onForecastDataLoaded;

    private static DataManager dataManager;
    private final StateManager stateManager;
    private ScreenManager screenManager;

    private DataManager() {
        super();
        locationInfoAPI = new LocationInfoAPI();
        weatherInfoAPI = new WeatherInfoAPI();
        screenManager = ScreenManager.getInstance();
        stateManager = StateManager.getInstance();
    }

    public static DataManager getInstance() {
        if ( dataManager == null ) {
            dataManager = new DataManager();
        }
        return dataManager;
    }


    public void loadLocation(CompleteOrFailureListener listener){
        locationInfoAPI.getLocationInfo(new ApiCallListener<LocationInfoModel>() {
            @Override
            public void onApiCallSuccess(LocationInfoModel result) {
                stateManager.setLocationInfoModel(result);
                listener.onComplete();
            }

            @Override
            public void onApiCallFailure(String errorMessage) {
                stateManager.setErrorMessage(errorMessage);
                listener.onFailure();
            }
        });
    }

    public void loadDataFromInternet(ScreenName targetScreen){
        screenManager.switchScreen(ScreenName.LOADING);
        this.loadDataFromInternet(new CompleteOrFailureListener() {
            @Override
            public void onComplete() {
                screenManager.switchScreen(targetScreen);
            }

            @Override
            public void onFailure() {
                screenManager.switchScreen(ScreenName.ERROR);
            }
        });
    }

    public void loadDataFromInternet(CompleteOrFailureListener listener){
        final int[] completedCount = {0};

        Runnable checkAllDone = () -> {
            if (completedCount[0] == 2 ) {
                System.out.println("All data loaded from internet.");
                listener.onComplete();
                if ( dataUploadedListener != null  ) dataUploadedListener.onDataUploaded();
            }
        };


        weatherInfoAPI.getWeather( new ApiCallListener<WeatherInfoModel>() {
            @Override
            public void onApiCallSuccess(WeatherInfoModel result) {
                System.out.println("Successfully loaded weather information from internet");
                setWeatherInfo(result);
                completedCount[0]++;
                checkAllDone.run();
                // if( stateManager.getWeatherDataLoadedListener() != null ) stateManager.getWeatherDataLoadedListener().onWeatherDataLoaded();
                // else System.out.println("SateManager.getWeatherDataLoadedListener() is null. No listener set for WeatherDataLoadedListener. Please set one before calling this method.");
            }
            @Override
            public void onApiCallFailure(String errorMessage) {
                // TODO: setErrorMessage(errorMessage);
                listener.onFailure();
            }
        } );

        weatherInfoAPI.getForeCast(new ApiCallListener<ForecastModel>() {
            @Override
            public void onApiCallSuccess(ForecastModel result) {
                System.out.println("Successfully loaded forecast information from internet");
                setForecastModel(result);
                completedCount[0]++;
                checkAllDone.run();
                if ( onForecastDataLoaded != null ) onForecastDataLoaded.onForecastDataLoaded();
            }
            @Override
            public void onApiCallFailure(String errorMessage) {
                System.err.println(errorMessage);
                // TODO: setErrorMessage(errorMessage);
                listener.onFailure();
            }
        });
    }


    public void setOnDataUploadedListener(OnDataUploadedListener listener) {
        this.dataUploadedListener = listener;
    }

    public void setWeatherInfo(WeatherInfoModel weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public WeatherInfoModel getWeatherInfo() {
        return weatherInfo;
    }

    public void setForecastModel(ForecastModel forecastModel) {
        this.forecastModel = forecastModel;
    }

    public ForecastModel getForecastModel() {
        return forecastModel;
    }

    public OnDataUploadedListener getDataUploadedListener() {
        return dataUploadedListener;
    }

    public void setOnForecastDataLoaded(OnForecastDataLoaded onForecastDataLoaded) {
        this.onForecastDataLoaded = onForecastDataLoaded;
    }

    public OnForecastDataLoaded getOnForecastDataLoaded() {
        return onForecastDataLoaded;
    }
}