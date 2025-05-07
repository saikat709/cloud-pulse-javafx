package com.saikat.cloudpulse.components;

import com.saikat.cloudpulse.CloudPulseApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CustomCard extends AnchorPane {
    public CustomCard() {
        try {
            URL fxmlLocation = CloudPulseApplication.class.getResource("components/card.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found at components/card.fxml");
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
}