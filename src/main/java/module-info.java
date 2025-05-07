module com.saikat.cloudpulse {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.net.http;
    requires java.desktop;
    requires com.google.gson;
    requires jdk.compiler;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.smartcardio;
    requires io.github.cdimascio.dotenv.java;

    opens com.saikat.cloudpulse to javafx.fxml;
    exports com.saikat.cloudpulse;
    exports com.saikat.cloudpulse.controllers;
    exports com.saikat.cloudpulse.components;
    opens com.saikat.cloudpulse.controllers to javafx.fxml;
    opens com.saikat.cloudpulse.models to com.google.gson;
}