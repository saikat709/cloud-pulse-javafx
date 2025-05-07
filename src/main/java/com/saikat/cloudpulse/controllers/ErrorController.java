package com.saikat.cloudpulse.controllers;

import com.saikat.cloudpulse.listeners.CompleteOrFailureListener;
import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.manager.ScreenManager;
import com.saikat.cloudpulse.screens.ScreenName;
import javafx.event.ActionEvent;

public class ErrorController {

    private final ScreenManager screenManager = ScreenManager.getInstance();
    private final DataManager dataManager = DataManager.getInstance();

    public void retryButtonClick(ActionEvent actionEvent) {
        screenManager.switchScreen(ScreenName.LOADING);
        dataManager.loadDataFromInternet(new CompleteOrFailureListener() {
            @Override
            public void onComplete() {
                screenManager.switchScreen(ScreenName.HOME);
            }

            @Override
            public void onFailure() {
                screenManager.switchScreen(ScreenName.ERROR);
            }
        });
    }
}
