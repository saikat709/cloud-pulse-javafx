package com.saikat.cloudpulse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saikat.cloudpulse.listeners.CompleteOrFailureListener;
import com.saikat.cloudpulse.manager.CityListManager;
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

    ScreenManager sm           = ScreenManager.getInstance();
    DataManager   dm           = DataManager.getInstance();
    StateManager  stateManager = StateManager.getInstance();
    AppStorage    storage      = AppStorage.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        // stage.setResizable(false);

        try {
            storage.loadFromStorage();

            String savedName = storage.getSavedName();
            stateManager.setUserName(savedName);

            sm.initialize(stage);
            sm.enterApplication();

            loadCityNames();
            ScreenName targetScreen = savedName == null ? ScreenName.NAME_INPUT : ScreenName.HOME;

            dm.loadLocation(new CompleteOrFailureListener() {
                @Override
                public void onComplete() {
                    dm.loadDataFromInternet(ScreenName.HOME);
                }

                @Override
                public void onFailure() {
                    dm.loadDataFromInternet( ScreenName.ERROR );
                }
            });

            System.out.println("Application started");

        } catch (Exception e) {
            System.out.println("Error loading application: " + e.getLocalizedMessage() );
        }

    }


    public void loadCityNames(){
        Type listType = new TypeToken<List<City>>() {}.getType();
        Gson gson = new Gson();
        CityListManager cityList = CityListManager.getInstance();

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