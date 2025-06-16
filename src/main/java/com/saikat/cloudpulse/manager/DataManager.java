package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.api.LocationInfoAPI;
import com.saikat.cloudpulse.api.WeatherInfoAPI;
import com.saikat.cloudpulse.listeners.*;
import com.saikat.cloudpulse.models.ForecastModel;
import com.saikat.cloudpulse.models.LocationInfoModel;
import com.saikat.cloudpulse.models.WeatherInfoModel;

public class DataManager {
    private LocationInfoModel currentLocation;
    private LocationInfoModel searchLocation;
    private WeatherInfoModel  weatherInfo;
    private ForecastModel     forecastModel;

    private final LocationInfoAPI locationInfoAPI;
    private final WeatherInfoAPI weatherInfoAPI;

    private OnDataUploadedListener dataUploadedListener;
    private OnPreviousScreenChange onPreviousScreenChange;
    private OnForecastDataLoaded   onForecastDataLoaded;

    public static DataManager dataManager;

    private DataManager() {
        super();
        locationInfoAPI = new LocationInfoAPI();
        weatherInfoAPI = new WeatherInfoAPI();
        // stateManager = StateManager.getInstance();
    }

    public static DataManager getInstance() {
        if ( dataManager == null ) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public void loadDataFromInternet(CompleteOrFailureListener listener){
        final int[] completedCount = {0};

        Runnable checkAllDone = () -> {
            if (completedCount[0] == 3 ) {
                System.out.println("✅ All data loaded from internet.");
                listener.onComplete();
                if ( dataUploadedListener != null  ) dataUploadedListener.onDataUploaded();
            }
        };

        locationInfoAPI.getLocationInfo(new ApiCallListener<LocationInfoModel>() {
            @Override
            public void onApiCallSuccess(LocationInfoModel result) {
                setCurrentLocation(result);
                System.out.println("Successfully loaded Location from internet");
                completedCount[0]++;
                checkAllDone.run();
            }

            @Override
            public void onApiCallFailure(String errorMessage) {
                listener.onFailure();
                // TODO: setErrorMessage
            }
        });

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

    public void setCurrentLocation(LocationInfoModel currentLocation) {
        this.currentLocation = currentLocation;
    }
    public LocationInfoModel getCurrentLocation() {
        return currentLocation;
    }
    public void setSearchLocation(LocationInfoModel searchLocation) {
        this.searchLocation = searchLocation;
    }
    public LocationInfoModel getSearchLocation() {
        return searchLocation;
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

    public OnPreviousScreenChange getOnPreviousScreenChange() {
        return onPreviousScreenChange;
    }

    public void setOnPreviousScreenChange(OnPreviousScreenChange onPreviousScreenChange) {
        this.onPreviousScreenChange = onPreviousScreenChange;
    }


    public void setOnForecastDataLoaded(OnForecastDataLoaded onForecastDataLoaded) {
        this.onForecastDataLoaded = onForecastDataLoaded;
    }

    public OnForecastDataLoaded getOnForecastDataLoaded() {
        return onForecastDataLoaded;
    }
}