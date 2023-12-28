package com.example.flightscheduling;

import javafx.fxml.FXML;
import org.controlsfx.control.PrefixSelectionChoiceBox;

public class FlightAdditionController {

    public PrefixSelectionChoiceBox<Airport> originChoiceBox;
    public PrefixSelectionChoiceBox<Airport> destinationChoiceBox;

    @FXML
    public void initialize() {
        originChoiceBox.getItems().addAll(Airport.values());
        destinationChoiceBox.getItems().addAll(Airport.values());
    }

    public void submitButtonOnAction() {
        //TODO Submit the flight to the database
    }

    public void backButtonOnAction() {
        SceneManager.switchScene(Scene.MAIN);
    }
}
