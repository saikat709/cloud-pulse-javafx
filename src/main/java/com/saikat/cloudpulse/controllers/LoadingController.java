package com.saikat.cloudpulse.controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

public class LoadingController {

    @FXML
    public AnchorPane loadingIndicator;
    @FXML
    public Arc spinnerArc;

    public void initialize() {
        startRotateAnimation();
    }

    private void startRotateAnimation() {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(spinnerArc);
        rotateTransition.setDuration(Duration.seconds(3));
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setAxis(javafx.geometry.Point3D.ZERO.add(0, 0, 1));
        rotateTransition.setByAngle(360);
        rotateTransition.play();
    }
}
