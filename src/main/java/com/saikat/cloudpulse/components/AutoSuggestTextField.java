package com.saikat.cloudpulse.components;

import com.saikat.cloudpulse.manager.CityListManager;
import com.saikat.cloudpulse.models.City;
import com.saikat.cloudpulse.utils.FuzzySearchUtil;
import javafx.animation.PauseTransition;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AutoSuggestTextField extends TextField {

    private final ContextMenu suggestionMenu = new ContextMenu();
    private final PauseTransition debounce   = new PauseTransition(Duration.millis(300));
    private List<City> suggestions           = new ArrayList<>();

    private City selectedCity;

    public AutoSuggestTextField() {
        setupListeners();
        this.setPromptText("Enter City Name");
        this.suggestionMenu.setWidth(this.getWidth() - 10);

        CityListManager cityList = CityListManager.getInstance();
        this.setSuggestions(cityList.getCities());
    }

    private void setupListeners() {
        this.textProperty().addListener((obs, oldVal, newVal) -> {
            debounce.setOnFinished(e -> showSuggestions(newVal));
            debounce.playFromStart();
        });
    }

    private void showSuggestions(String input) {
        if (input == null || input.isEmpty()) {
            suggestionMenu.hide();
            return;
        }

        List<City> filtered = FuzzySearchUtil.getFuzzyCityNames(suggestions, input);

        System.out.println("filtered list: " + filtered.size());

        if (filtered.isEmpty()) {
            suggestionMenu.hide();
            return;
        }

        List<CustomMenuItem> items = getCustomMenuItems(filtered);

        suggestionMenu.getItems().setAll(items);
        if (!suggestionMenu.isShowing()) {
            suggestionMenu.show(this, Side.BOTTOM, 0, 0);
        }
    }

    private List<CustomMenuItem> getCustomMenuItems(List<City> filtered) {
        List<CustomMenuItem> items = new ArrayList<>();
        for (City match : filtered) {
            Label label = new Label(match.getName());
            label.setPrefWidth(this.getWidth());
            CustomMenuItem item = new CustomMenuItem(label, true);
            item.setOnAction(e -> {
                setText(match.getName());
                suggestionMenu.hide();
                positionCaret(match.getName().length());
                this.selectedCity = match;
            });
            items.add(item);
        }
        return items;
    }

    public void setSuggestions(List<City> suggestions) {
        this.suggestions = suggestions;
    }


    public City getSelectedCity() {
        return this.selectedCity;
    }


    public void clearSelection() {
        this.selectedCity = null;
        this.setText("");
    }
}
