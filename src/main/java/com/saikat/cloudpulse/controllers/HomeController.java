package com.saikat.cloudpulse.controllers;

import com.saikat.cloudpulse.components.AutoSuggestTextField;
import com.saikat.cloudpulse.listeners.CompleteOrFailureListener;
import com.saikat.cloudpulse.listeners.OnDataUploadedListener;
import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.manager.ScreenManager;
import com.saikat.cloudpulse.manager.StateManager;
import com.saikat.cloudpulse.models.LocationInfoModel;
import com.saikat.cloudpulse.models.WeatherInfoModel;
import com.saikat.cloudpulse.screens.ScreenName;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeController {
    @FXML public MenuButton menuButton;
    @FXML public Button refreshButton;
    @FXML public AutoSuggestTextField autoSuggestTextField;
    @FXML public HBox titleContainer;
    @FXML public Label weatherTypeIconLabel;
    @FXML public ScrollPane scrollPane;
    @FXML public Label nameLabel;
    @FXML public Label greetingTimeLabel;
    @FXML public Label sunnyOrRainyLabel;
    @FXML public Label temperatureLabel;
    @FXML public Label cityNameLabel;

    private final ScreenManager manager =  ScreenManager.getInstance();
    private final DataManager dataManager = DataManager.getInstance();
    private final StateManager stateManager = StateManager.getInstance();


    public void initialize(){

        autoSuggestTextField.setOnKeyReleased(event -> {
            System.out.println("AutoSuggestTextField: " + autoSuggestTextField.getText());
            if ( event.getCode() == KeyCode.ENTER ) {
                if ( autoSuggestTextField.getText().isEmpty()) {
                    showAlertTextEmpty();
                } else {
                    proceedSearch();
                }
            }
        });
        scrollPane.setFitToHeight(true);

        dataManager.setOnDataUploadedListener( () -> {
            System.out.println("Home: OnDataUploadedListener called");
        });

        dataManager.setOnUserNameChanged(()->{
            nameLabel.setText("Hello, " + dataManager.getUserName() + "!");
            System.out.println("Home: OnUserNameChanged called");
            updateGreetingTimeLabel();
            updateInformation();
        });

        autoSuggestTextField.setSuggestions(List.of("Java", "JavaFX", "Python", "JavaScript", "Rust"));

        System.out.println("Home screen initialized, added listener.");
        updateInformation();
        updateGreetingTimeLabel();

        stateManager.setWeatherDataLoadedListener(()->{
            System.out.println("State manager: Weather data loaded listener called");
        });
    }


    public void updateInformation(){
        LocationInfoModel locationInfoModel = dataManager.getCurrentLocation();
        WeatherInfoModel weatherInfoModel = dataManager.getWeatherInfo();

        cityNameLabel.setText(locationInfoModel.getCity());
        temperatureLabel.setText(weatherInfoModel.getMain().getTemp() + "  °C");
    }



    private void updateGreetingTimeLabel(){
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
        dataManager.setCanGoHome(true);
        manager.switchScreen(ScreenName.NAME_INPUT);
        System.out.println("Showing name change screen");
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