package com.saikat.cloudpulse.controllers;

import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.manager.ScreenManager;
import com.saikat.cloudpulse.manager.StateManager;
import com.saikat.cloudpulse.models.LocationInfoModel;
import com.saikat.cloudpulse.screens.ScreenName;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LocationDetailsController {
    @FXML private Label ipLabel;
    @FXML private Label cityLabel;
    @FXML private Label regionLabel;
    @FXML private Label countryLabel;
    @FXML private Label locLabel;
    @FXML private Label orgLabel;
    @FXML private Label postalLabel;
    @FXML private Label timezoneLabel;
    @FXML private Label readmeLabel;

    private DataManager dataManager;
    private ScreenManager sm;
    private StateManager stateManager;

    public void initialize() {
        this.dataManager = DataManager.getInstance();
        this.stateManager = StateManager.getInstance();
        this.sm = ScreenManager.getInstance();

        sm.addOnScreenChangeListener( screenName -> {
            if ( screenName == ScreenName.LOCATION_INFO && stateManager.getLocationInfoModel() != null ){
                setLocationInfo(stateManager.getLocationInfoModel());
            }
        });
    }

    public void setLocationInfo(LocationInfoModel info) {
        ipLabel.setText(info.getIp());
        cityLabel.setText(info.getCity());
        regionLabel.setText(info.getRegion());
        countryLabel.setText(info.getCountry());
        locLabel.setText(info.getLoc());
        orgLabel.setText(info.getOrg());
        postalLabel.setText(String.valueOf(1092));
        timezoneLabel.setText(info.getTimezone());
        // readmeLabel.setText(info.getReadme());
    }

    @FXML
    public void onBackButtonClick(MouseEvent actionEvent) {
        sm.switchScreen(ScreenName.HOME);
    }
}
