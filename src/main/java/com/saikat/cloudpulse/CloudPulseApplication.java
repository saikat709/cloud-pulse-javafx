package com.saikat.cloudpulse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saikat.cloudpulse.listeners.CompleteOrFailureListener;
import com.saikat.cloudpulse.manager.CityList;
import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.manager.ScreenManager;
import com.saikat.cloudpulse.manager.StateManager;
import com.saikat.cloudpulse.models.City;
import com.saikat.cloudpulse.screens.ScreenName;
import com.saikat.cloudpulse.storage.AppStorage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class CloudPulseApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // stage.setResizable(false);
        try {
            ScreenManager sm = ScreenManager.getInstance();
            DataManager dm = DataManager.getInstance();
            StateManager stateManager = StateManager.getInstance();
            AppStorage storage = AppStorage.getInstance();

            storage.loadFromStorage();
            stateManager.setUserName(storage.getSavedName());

            sm.initialize(stage);
            sm.enterApplication();
            sm.switchScreen(ScreenName.LOADING);

            System.out.println("Application started");
            dm.loadDataFromInternet(new CompleteOrFailureListener() {
                @Override public void onComplete() {
                    if ( storage.getSavedName() == null ) {
                        sm.switchScreen(ScreenName.NAME_INPUT);
                    } else {
                        stateManager.setUserName(storage.getSavedName());
                        sm.switchScreen(ScreenName.HOME);
                    }
                }
                @Override public void onFailure() {
                    sm.switchScreen(ScreenName.ERROR);
                }
            });
        } catch (Exception e) {
            System.out.println("Error loading application: " + e.getLocalizedMessage() );
            e.printStackTrace();
        }
        loadCityNames();
    }


    public void loadCityNames(){
        Type listType = new TypeToken<List<City>>() {}.getType();
        Gson gson = new Gson();
        CityList cityList = CityList.getInstance();

        InputStream inputStream = CloudPulseApplication.class.getResourceAsStream("/files/city.list.json");
        if (inputStream == null) {
            System.out.println("File not found in resources");
            return;
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            String content = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));
            List<City> objects = gson.fromJson(content, listType);
            cityList.setCities(objects);
        } catch (Exception e) {
            System.out.println("$$$: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}