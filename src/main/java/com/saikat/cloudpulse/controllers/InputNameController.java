package com.saikat.cloudpulse.controllers;

import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.manager.ScreenManager;
import com.saikat.cloudpulse.manager.StateManager;
import com.saikat.cloudpulse.screens.ScreenName;
import com.saikat.cloudpulse.storage.AppStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;

public class InputNameController {
    @FXML public TextField textField;
    @FXML public Button saveButton;
    @FXML public Button cancelButton;

    private ScreenManager screenManager;
    private DataManager   dataManager;
    private StateManager stateManager;

    public void initialize(){
        this.screenManager = ScreenManager.getInstance();
        this.dataManager = DataManager.getInstance();
        this.stateManager = StateManager.getInstance();
        System.out.println("Input name screen initialized: " + stateManager.getUserName() + " " + (dataManager == null) + " " + (screenManager == null) );

        if( this.dataManager != null)  textField.setText(this.stateManager.getUserName());
        textField.setOnKeyPressed( k -> {
            if ( k.getCode().equals(KeyCode.ENTER) ) {
                saveAndChange();
            }
        });

        screenManager.addOnScreenChangeListener( screenName -> {
            if ( screenName == ScreenName.NAME_INPUT && stateManager.isCanGoBack() ){
                cancelButton.setText("Go Back");
                cancelButton.setVisible(true);
            }
        });

    }

    @FXML
    public  void onSaveButtonClicked(ActionEvent actionEvent) {
        saveAndChange();
    }

    private void saveAndChange(){
       if ( validateName() != null ) {
            System.out.println("Saving name: " + textField.getText());
            stateManager.setUserName(textField.getText());
            AppStorage.getInstance().saveUserName(textField.getText());
            screenManager.switchScreen(ScreenName.HOME);
        }
    }

    private String validateName(){
        boolean isValid = textField.getText().trim().length() < 5;
        for ( char c : textField.getText().toCharArray() ) {
            if ( !Character.isAlphabetic(c) ) isValid = false;
        }
        if (  isValid ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Name");
            alert.setHeaderText(null);
            alert.setContentText("Name must be at least 5 characters long");
            alert.showAndWait();
            return null;
        }
        return textField.getText();
    }

    @FXML
    public void onCancelButtonClicked(ActionEvent actionEvent) {
        screenManager.switchScreen(ScreenName.HOME);
    }
}
