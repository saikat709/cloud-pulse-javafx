package com.saikat.cloudpulse.components;

import com.saikat.cloudpulse.CloudPulseApplication;
import com.saikat.cloudpulse.models.Forecast;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

public class CustomCard extends AnchorPane {

    @FXML Label day;
    @FXML Label temp;
    @FXML Label cloud;

    public CustomCard() {
        try {
            URL fxmlLocation = CloudPulseApplication.class.getResource("components/card.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found at components/card.fxml");
                throw new RuntimeException("Card fxml ( components/card.fxml ) not found.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setController(this);
            Parent card = loader.load();
            card.getStylesheets().add(Objects.requireNonNull(CloudPulseApplication.class.getResource("css/comp/card.css")).toExternalForm());
            this.getChildren().add(card);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("Failed to load CustomCard FXML", e);
        }
    }

    public void setDay(LocalDateTime date) {
        day.setText(String.valueOf(date.toString()));
    }

    public void setForeCast(Forecast foreCast){
        System.out.println(foreCast);
    }
}