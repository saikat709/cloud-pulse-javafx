package com.saikat.cloudpulse.controllers;

import com.saikat.cloudpulse.components.AutoSuggestTextField;
import com.saikat.cloudpulse.listeners.CompleteOrFailureListener;
import com.saikat.cloudpulse.manager.CityListManager;
import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.manager.ScreenManager;
import com.saikat.cloudpulse.manager.StateManager;
import com.saikat.cloudpulse.models.ForecastModel;
import com.saikat.cloudpulse.models.LocationInfoModel;
import com.saikat.cloudpulse.models.WeatherInfoModel;
import com.saikat.cloudpulse.screens.ScreenName;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Calendar;

public class HomeController {
    @FXML public MenuButton menuButton;
    @FXML public Button    refreshButton;
    @FXML public AutoSuggestTextField autoSuggestTextField;
    @FXML public HBox  titleContainer;
    @FXML public Label weatherTypeIconLabel;
    @FXML public ScrollPane scrollPane;
    @FXML public Label nameLabel;
    @FXML public Label greetingTimeLabel;
    @FXML public Label sunnyOrRainyLabel;
    @FXML public Label temperatureLabel;
    @FXML public Label cityNameLabel;
    @FXML public Label pressureLabel;
    @FXML public Label windSpeedLabel;
    @FXML public Label humidityLabel;
    @FXML public Button searchOrCancelSearchedBtn;

    private final ScreenManager manager =  ScreenManager.getInstance();
    private final DataManager   dataManager = DataManager.getInstance();
    private final StateManager  stateManager = StateManager.getInstance();
    private final CityListManager cityList = CityListManager.getInstance();

    private WeatherInfoModel previousWeatherInfo;
    private ForecastModel previousForecastInfo;
    private boolean hasSearchedWeather;

    public void initialize(){
        this.hasSearchedWeather = false;

        autoSuggestTextField.setOnKeyReleased(event -> {
            if ( event.getCode() == KeyCode.ENTER ) {
                if ( autoSuggestTextField.getSelectedCity() != null ) proceedSearch();
            } else {
                if ( hasSearchedWeather ) {
                    cancelExistingSearch();
                }
            }
        });

        manager.addOnScreenChangeListener(screenName -> {
            if ( screenName == ScreenName.HOME ) updateGreetingTimeLabel();
        });

        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        dataManager.setOnDataUploadedListener(this::updateInformation);
        nameLabel.setText("Hello, " + stateManager.getUserName() + "!");

        updateInformation();
        updateGreetingTimeLabel();
    }

    private void updateInformation() {
        LocationInfoModel locationInfoModel = stateManager.getLocationInfoModel();
        cityNameLabel.setText(locationInfoModel.getCity());

        WeatherInfoModel weatherInfoModel = dataManager.getWeatherInfo();
        temperatureLabel.setText( (int) ( weatherInfoModel.getMain().getTemp() - 273 ) + "°C");
        windSpeedLabel.setText(weatherInfoModel.getWind().getSpeed() + " m/s");
        humidityLabel.setText(weatherInfoModel.getMain().getHumidity() + "%");
        pressureLabel.setText(weatherInfoModel.getMain().getPressure() + " hPa");
    }

    private void updateGreetingTimeLabel(){
        nameLabel.setText("Hello, " + stateManager.getUserName() + "!");

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if ( hour < 12 ) {
            greetingTimeLabel.setText("Good morning...!");
        } else if ( hour < 18 ) {
            greetingTimeLabel.setText("Good afternoon...!");
        } else if ( hour < 20 ) {
            greetingTimeLabel.setText("Good evening...!");
        }  else {
            greetingTimeLabel.setText("Good night...!");
        }
    }

    @FXML
    public void showNameChangeScreen(ActionEvent actionEvent) {
        stateManager.setCanGoBack(true);
        manager.switchScreen(ScreenName.NAME_INPUT);
    }

    @FXML
    public void appExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void gotoLocationDetailsScreen(ActionEvent actionEvent) {
        manager.switchScreen(ScreenName.LOCATION_INFO);
    }

    @FXML
    public void searchButtonClicked(ActionEvent actionEvent) {
        if ( autoSuggestTextField.getText().isEmpty() ) {
            showAlertTextEmpty();
            return;
        }
        if ( hasSearchedWeather ) {
            cancelExistingSearch();
            dataManager.loadDataFromInternet(ScreenName.HOME);
        } else {
            proceedSearch();
        }
        System.out.println(autoSuggestTextField.getText());
    }

    private void showAlertTextEmpty(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a search term");
        alert.showAndWait();
    }

    private void proceedSearch(){
        FontIcon icon = new FontIcon();
        icon.setIconColor(Color.WHITE);

        icon.setIconCode(FontAwesomeSolid.CODE); // TODO: cancel icon
        icon.setIconColor(Color.WHITE);
        searchOrCancelSearchedBtn.setGraphic(icon);
        manager.switchScreen(ScreenName.LOADING);
        hasSearchedWeather = true;

        stateManager.setCityToBeSearched(autoSuggestTextField.getSelectedCity());
        dataManager.loadDataFromInternet(ScreenName.HOME);
    }

    private void cancelExistingSearch(){
        hasSearchedWeather = false;
        FontIcon icon = new FontIcon();
        icon.setIconCode(FontAwesomeSolid.SEARCH);
        icon.setIconColor(Color.WHITE);
        searchOrCancelSearchedBtn.setGraphic(icon);

        stateManager.clearCityToSearch();
        autoSuggestTextField.clearSelection();
    }

    public void refreshButtonClicked(ActionEvent actionEvent) {
        manager.switchScreen(ScreenName.LOADING);
        dataManager.loadDataFromInternet(new CompleteOrFailureListener() {
            @Override
            public void onComplete() {
                manager.switchScreen(ScreenName.HOME);
            }

            @Override
            public void onFailure() {
                manager.switchScreen(ScreenName.ERROR);
            }
        });
    }
}

 /*
    FontIcon icon = new FontIcon(FontAwesomeSolid.BARS);  // 'fas' = FontAwesome Solid
    icon.setIconColor(Color.WHITE);
    menuButton.setGraphic(icon);
    System.out.println("Home screen initialized: " + FontAwesomeSolid.ARROW_CIRCLE_DOWN);
*/