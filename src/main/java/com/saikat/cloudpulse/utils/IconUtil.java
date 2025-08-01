package com.saikat.cloudpulse.utils;

import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconUtil {
    public static FontIcon getWeatherIcon(String openWeatherIcon, int size) {
        String iconLiteral;
        String color;

        switch (openWeatherIcon) {
            case "01d" -> {
                iconLiteral = "fas-sun";
                color = "#FDB813";
            }
            case "01n" -> {
                iconLiteral = "fas-moon";
                color = "#C0C0C0";
            }
            case "02d" -> {
                iconLiteral = "fas-cloud-sun";
                color = "#F7C873";
            }
            case "02n" -> {
                iconLiteral = "fas-cloud-moon";
                color = "#A0A0A0";
            }
            case "03d", "03n", "04d", "04n" -> {
                iconLiteral = "fas-cloud";
                color = "#B0B0B0";
            }
            case "09d", "09n" -> {
                iconLiteral = "fas-cloud-showers-heavy";
                color = "#4A90E2";
            }
            case "10d" -> {
                iconLiteral = "fas-cloud-sun-rain";
                color = "#6CB4EE";
            }
            case "10n" -> {
                iconLiteral = "fas-cloud-moon-rain";
                color = "#4C6C88";
            }
            case "11d", "11n" -> {
                iconLiteral = "fas-bolt";
                color = "#FFD700";
            }
            case "13d", "13n" -> {
                iconLiteral = "fas-snowflake";
                color = "#00FFFF";
            }
            case "50d", "50n" -> {
                iconLiteral = "fas-smog";
                color = "#999999";
            }
            default -> {
                iconLiteral = "fas-question-circle";
                color = "#FF0000";
            }
        }

        FontIcon icon = new FontIcon(iconLiteral);
        icon.setIconSize(size);
        icon.setIconColor(Paint.valueOf(color));
        return icon;
    }
}
