package com.example.flightscheduling;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.switchScene(com.example.flightscheduling.Scene.MAIN);
    }

    public static void main(String[] args) {
        launch();
    }
}