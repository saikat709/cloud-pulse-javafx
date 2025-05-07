package com.saikat.cloudpulse.screens;

import com.saikat.cloudpulse.CloudPulseApplication;
import com.saikat.cloudpulse.constants.ConstValues;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Screen {
    private final String fxml;
    private final String css;
    private Scene scene;

    public Screen(String fxml, String css) {
        this.fxml = fxml;
        this.css = css;
    }

    public Scene getScene() throws IOException {
        if ( scene == null ) {
            FXMLLoader fxmlLoader = new FXMLLoader(CloudPulseApplication.class.getResource(fxml));
            this.scene = new Scene(fxmlLoader.load(), ConstValues.SCREEN_WIDTH, ConstValues.SCREEN_HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull(
                    CloudPulseApplication.class.getResource(css))
                    .toExternalForm());

        }
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void show(Stage stage){
        try {
            stage.setScene(getScene());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading screen: " + e.getLocalizedMessage());
        }
    }

    public void setPosition(Number newX, Number newY) {
        scene.getWindow().setX(newX.doubleValue());
    }
}
