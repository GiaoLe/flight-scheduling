package com.example.flightscheduling.ui;

import com.example.flightscheduling.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
        stage.setTitle("Flight Scheduling");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/plane-icon.png"))));
    }

    public static void switchScene(Scene scene) {
        try {
            stage.setScene(new javafx.scene.Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(scene.getFileLocationInResources())))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
