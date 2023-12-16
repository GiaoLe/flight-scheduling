package com.example.flightscheduling;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    public static void switchScene(Scene scene) {
        try {
            stage.setScene(new javafx.scene.Scene(FXMLLoader.load(Objects.requireNonNull(FlightSchedulingApplication.class.getResource(scene.getFileLocationInResources())))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
