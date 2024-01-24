package com.example.flightscheduling.ui;

import com.example.flightscheduling.Main;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class PopUpWindowManager {

    private static Stage stage;

    public static void openPopUpWindow(PopUpWindow popUpWindow) {
        stage = new Stage();
        try {
            stage.setScene(new javafx.scene.Scene(FXMLLoader.load(Objects.requireNonNull(
                    Main.class.getResource(popUpWindow.getFileLocation())))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setResizable(false);
        stage.setIconified(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.centerOnScreen();
        stage.showAndWait();
    }

    public static void closeCurrentWindow() {
        stage.close();
        SceneManager.refreshView();
    }
}