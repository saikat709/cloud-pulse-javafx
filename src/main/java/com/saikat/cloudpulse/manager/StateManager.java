package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.models.City;
import com.saikat.cloudpulse.models.LocationInfoModel;

public class StateManager {
    public static StateManager instance;

    private String  userName;
    private boolean canGoBack;
    private String errorMessage;
    private City cityToBeSearched;
    private LocationInfoModel locationInfoModel;


    private StateManager() {
        super();
        canGoBack = false;
    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public boolean isCanGoBack() {
        return canGoBack;
    }

    public void setCanGoBack(boolean canGoBack) {
        this.canGoBack = canGoBack;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

    public City getCityToBeSearched() {
        return cityToBeSearched;
    }

    public void setCityToBeSearched(City cityToBeSearched) {
        this.cityToBeSearched = cityToBeSearched;
    }

    public LocationInfoModel getLocationInfoModel() {
        return locationInfoModel;
    }

    public void setLocationInfoModel(LocationInfoModel locationInfoModel) {
        this.locationInfoModel = locationInfoModel;
    }

    public void clearCityToSearch() {
        this.cityToBeSearched = null;
    }
}
