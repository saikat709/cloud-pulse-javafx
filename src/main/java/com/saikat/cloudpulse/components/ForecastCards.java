package com.saikat.cloudpulse.components;

import com.saikat.cloudpulse.manager.DataManager;
import com.saikat.cloudpulse.models.Forecast;
import javafx.scene.layout.HBox;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class ForecastCards extends HBox {
    private final DataManager dataManager;

    public ForecastCards() {
        super();
        this.dataManager = DataManager.getInstance();
        this.setSpacing(14);
        dataManager.setOnForecastDataLoaded(this::updateCards);
        updateCards();
    }

    public void updateCards() {

        this.getChildren().clear();
        List<Forecast> list = dataManager.getForecastModel().getList();
        for (int i = 0; i < list.size(); i++) {
            try {
                CustomCard card = new CustomCard();
                LocalDateTime dateTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(list.get(i).getDt()),
                        ZoneId.of("UTC")  // or "Asia/Dhaka" or your local zone
                );
                // String formatted = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                card.setDay(dateTime);
                this.getChildren().add(card);
                i += 8;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
