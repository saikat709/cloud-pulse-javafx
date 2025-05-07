package com.saikat.cloudpulse.components;

import com.saikat.cloudpulse.manager.DataManager;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import java.awt.*;

public class ForecastCards extends HBox {
    private final DataManager dataManager;

    public ForecastCards() {
        super();
        this.dataManager = DataManager.getInstance();
        this.setSpacing(14);
        updateCards();
    }

    public void updateCards() {
        this.getChildren().clear();
        for (int i = 0; i < 5; i++) {
            try {
               CustomCard card = new CustomCard();
               this.getChildren().add(card);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
