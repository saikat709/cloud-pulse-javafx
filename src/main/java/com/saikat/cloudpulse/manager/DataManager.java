package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.api.LocationInfoAPI;
import com.saikat.cloudpulse.api.WeatherInfoAPI;
import com.saikat.cloudpulse.listeners.*;
import com.saikat.cloudpulse.models.ForecastModel;
import com.saikat.cloudpulse.models.LocationInfoModel;
import com.saikat.cloudpulse.models.WeatherInfoModel;
import com.saikat.cloudpulse.screens.ScreenName;

public class DataManager {
    private String cityName;
    private String userName;
    private LocationInfoModel currentLocation;
    private LocationInfoModel searchLocation;
    private String searchQuery;
    private String errorMessage;
    private WeatherInfoModel weatherInfo;
    private ForecastModel forecastModel;
    private ScreenName previousScreen;

    private boolean canGoHome = false;
    private final LocationInfoAPI locationInfoAPI;
    private final WeatherInfoAPI weatherInfoAPI;
    private final StateManager stateManager;

    private OnDataUploadedListener dataUploadedListener;
    private OnPreviousScreenChange onPreviousScreenChange;
    private OnUserNameChanged onUserNameChanged;

    public static DataManager dataManager;

    private DataManager() {
        locationInfoAPI = new LocationInfoAPI();
        weatherInfoAPI = new WeatherInfoAPI();
        stateManager = StateManager.getInstance();
    }

    public static DataManager getInstance() {
        if ( dataManager == null ) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public void loadDataFromInternet(CompleteOrFailureListener listener){
        System.out.println("Loading data from internet");
        final int[] completedCount = {0};

        Runnable checkAllDone = () -> {
            if (completedCount[0] == 3 ) {
                if (dataUploadedListener != null) dataUploadedListener.onDataUploaded();
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
                setErrorMessage(errorMessage);
                listener.onFailure();

            }
        });

        weatherInfoAPI.getWeather( new ApiCallListener<WeatherInfoModel>() {
            @Override
            public void onApiCallSuccess(WeatherInfoModel result) {
                System.out.println("Successfully loaded weather information from internet");
                setWeatherInfo(result);
                completedCount[0]++;
                checkAllDone.run();
                if( stateManager.getWeatherDataLoadedListener() != null ) stateManager.getWeatherDataLoadedListener().onWeatherDataLoaded();
                else System.out.println("SateManager.getWeatherDataLoadedListener() is null. No listener set for WeatherDataLoadedListener. Please set one before calling this method.");
            }
            @Override
            public void onApiCallFailure(String errorMessage) {
                setErrorMessage(errorMessage);
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
            }
            @Override
            public void onApiCallFailure(String errorMessage) {
                setErrorMessage(errorMessage);
                listener.onFailure();
            }
        });
    }

    public void setOnDataUploadedListener(OnDataUploadedListener listener) {
        this.dataUploadedListener = listener;
    }

    public ScreenName getPreviousScreen() {
        return previousScreen;
    }
    public void setPreviousScreen(ScreenName previousScreen) {
        this.previousScreen = previousScreen;
        if ( onPreviousScreenChange != null ) onPreviousScreenChange.onPreviousScreenChange();
    }

    public void setPreviousScreen(String previousScreen) {
        this.previousScreen = ScreenName.valueOf(previousScreen);
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName() {
        return cityName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
        if ( onUserNameChanged != null ) onUserNameChanged.onUserNameChanged();
        else System.out.println("No OnUserNameChanged listener set");
    }
    public String getUserName() {
        return userName;
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
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
    public String getSearchQuery() {
        return searchQuery;
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
    public void clearData() {
        this.cityName = null;
        this.userName = null;
        this.currentLocation = null;
        this.searchLocation = null;
        this.searchQuery = null;
        this.weatherInfo = null;
        this.forecastModel = null;
    }
    public void clearSearchData() {
        this.searchLocation = null;
        this.searchQuery = null;
    }
    public void clearWeatherData() {
        this.weatherInfo = null;
    }
    public void clearForecastData() {
        this.forecastModel = null;
    }
    public void clearAllData() {
        this.clearData();
        this.clearSearchData();
        this.clearWeatherData();
        this.clearForecastData();
    }
    public void clearUserData() {
        this.userName = null;
        this.currentLocation = null;
    }
    public void clearLocationData() {
        this.currentLocation = null;
        this.searchLocation = null;
    }
    public void clearSearchLocationData() {
        this.searchLocation = null;
    }
    public void clearSearchQueryData() {
        this.searchQuery = null;
    }

    public void clearCurrentLocationData() {
        this.currentLocation = null;
    }
    public void clearForecastModelData() {
        this.forecastModel = null;
    }
    public void clearWeatherModelData() {
        this.weatherInfo = null;
    }
    public void clearCityNameData() {
        this.cityName = null;
    }
    public void clearSearchCityNameData() {
        this.searchQuery = null;
    }
    public void clearSearchCityNameAndQueryData() {
        this.searchQuery = null;
        this.cityName = null;
    }
    public void clearSearchCityNameAndUserData() {
        this.searchQuery = null;
        this.cityName = null;
        this.userName = null;
        this.currentLocation = null;
    }
    public void clearAllUserData() {
        this.clearUserData();
        this.clearLocationData();
    }

    public boolean canGoHome() {
        return canGoHome;
    }

    public void setCanGoHome(boolean canGoHome) {
        this.canGoHome = canGoHome;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public void clearPreviousScreen() {
        this.previousScreen = null;
    }

    public OnUserNameChanged getOnUserNameChanged() {
        return onUserNameChanged;
    }

    public void setOnUserNameChanged(OnUserNameChanged onUserNameChanged) {
        this.onUserNameChanged = onUserNameChanged;
    }
}