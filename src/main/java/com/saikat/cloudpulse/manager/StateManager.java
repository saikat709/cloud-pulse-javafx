package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.listeners.OnWeatherDataLoaded;

public class StateManager {
    public static StateManager instance;
    private OnWeatherDataLoaded weatherDataLoadedListener;

    private StateManager() { }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }


    public OnWeatherDataLoaded getWeatherDataLoadedListener() {
        return weatherDataLoadedListener;
    }

    public void setWeatherDataLoadedListener(OnWeatherDataLoaded weatherDataLoadedListener) {
        this.weatherDataLoadedListener = weatherDataLoadedListener;
    }
}
