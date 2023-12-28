package com.example.flightscheduling;

import com.example.flightscheduling.ui.Scene;
import com.example.flightscheduling.ui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.switchScene(Scene.MAIN);
    }

    public static void main(String[] args) {
        launch();
    }
}