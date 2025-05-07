package com.saikat.cloudpulse;

import com.saikat.cloudpulse.listeners.CompleteOrFailureListener;
import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.manager.ScreenManager;
import com.saikat.cloudpulse.screens.ScreenName;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class CloudPulseApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        ScreenManager sm = ScreenManager.getInstance();
        DataManager dm = DataManager.getInstance();
        try {
            sm.initialize(stage);
            sm.enterApplication();
            sm.switchScreen(ScreenName.LOADING);
            System.out.println("Application started");
            dm.loadDataFromInternet(new CompleteOrFailureListener() {
                @Override public void onComplete() {
                    sm.switchScreen(ScreenName.NAME_INPUT);
                }
                @Override public void onFailure() {
                    sm.switchScreen(ScreenName.ERROR);
                }
            });
        } catch (Exception e) {
            System.out.println("Error loading application: " + e.getLocalizedMessage() );
        }
    }

    public static void main(String[] args) {
        launch();
    }
}