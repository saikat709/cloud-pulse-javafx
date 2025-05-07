package com.saikat.cloudpulse.components;

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
    private final PauseTransition debounce = new PauseTransition(Duration.millis(300));
    private List<String> suggestions = new ArrayList<>();

    public AutoSuggestTextField() {
        setupListeners();
        suggestions.add("London");
        suggestions.add("Paris");
        suggestions.add("New York");
        suggestions.add("Tokyo");
        suggestions.add("Sydney");
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

        List<String> filtered = suggestions.stream()
                .filter(item -> item.toLowerCase().startsWith(input.toLowerCase()))
                .toList();

        if (filtered.isEmpty()) {
            suggestionMenu.hide();
            return;
        }

        List<CustomMenuItem> items = new ArrayList<>();
        for (String match : filtered) {
            Label label = new Label(match);
            CustomMenuItem item = new CustomMenuItem(label, true);
            item.setOnAction(e -> {
                setText(match);
                suggestionMenu.hide();
                positionCaret(match.length());
            });
            items.add(item);
        }

        suggestionMenu.getItems().setAll(items);
        if (!suggestionMenu.isShowing()) {
            suggestionMenu.show(this, Side.BOTTOM, 0, 0);
        }
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
