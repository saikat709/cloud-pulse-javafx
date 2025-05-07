package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.CloudPulseApplication;
import com.saikat.cloudpulse.screens.Screen;
import com.saikat.cloudpulse.screens.ScreenName;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.HashMap;

public class ScreenManager {
    private final HashMap<ScreenName, Screen> screens = new HashMap<>();
    public static ScreenManager screenManager;
    public static ScreenName currentScreen = ScreenName.HOME;
    private Stage stage;

    private Number newX, newY;

    private ScreenManager() {
        screens.put(ScreenName.NAME_INPUT, new Screen("input-name.fxml", "css/input-name.css"));
        screens.put(ScreenName.HOME,       new Screen("home.fxml", "css/home.css"));
        screens.put(ScreenName.LOADING,    new Screen("loading.fxml", "css/loading.css"));
        screens.put(ScreenName.ERROR,      new Screen("error.fxml", "css/error.css"));
    }

    public void initialize(Stage stage) {
        this.stage = stage;
        stage.xProperty().addListener((obs, oldX, newX) -> {
            this.newX = newX;
        });

        stage.yProperty().addListener((obs, oldY, newY) -> {
            this.newY = newY;
        });
    }

    public static ScreenManager getInstance() {
        if ( screenManager == null ) {
            screenManager = new ScreenManager();
        }
        return screenManager;
    }

    public void enterApplication(){
        if ( stage == null ) {
            System.out.println("Stage is null. Maybe you forgot to call setStage()");
        };
        switchScreen(ScreenName.NAME_INPUT);
    }

    public void addScreen(ScreenName name, Screen screen) {
        screens.put(name, screen);
    }

    public Screen getScreen(ScreenName name) {
        return screens.get(name);
    }

    public void switchScreen(ScreenName screenName) {
        if ( !screens.containsKey(screenName) ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such screen exists");
            alert.showAndWait();
            return;
        }
        if ( stage == null ) {
            System.out.println("Stage is null. Maybe you forgot to call setStage()");
        } else {
            try {
                stage.hide();
                currentScreen = screenName;
                Screen screen = screens.get(screenName);
                if ( newX != null && newY != null ) {
                    System.out.println("Moving window to: " + newX.doubleValue() + ", " + newY.doubleValue());
                    stage.setX(newX.doubleValue());
                    stage.setY(newY.doubleValue());
                }
                screen.show(stage);
                stage.show();
            } catch (Exception e) {
                System.out.println("Error switching screen to + (" +  screenName.toString() + "): " + e.getLocalizedMessage());
            }
        }
    }
}
