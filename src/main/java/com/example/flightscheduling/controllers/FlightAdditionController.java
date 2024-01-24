package com.example.flightscheduling.controllers;

import com.example.flightscheduling.Airport;
import com.example.flightscheduling.ui.PopUpWindowManager;
import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.controlsfx.control.PrefixSelectionChoiceBox;

public class FlightAdditionController {
    public PrefixSelectionChoiceBox<Airport> originChoiceBox;
    public PrefixSelectionChoiceBox<Airport> destinationChoiceBox;
    public TextField departureHourTextField;
    public TextField arrivalHourTextField;
    public TextField arrivalMinuteTextField;
    public TextField departureMinuteTextField;

    @FXML
    public void initialize() {
        originChoiceBox.getItems().addAll(Airport.values());
        destinationChoiceBox.getItems().addAll(Airport.values());
    }

    public void submitButtonOnAction() {
        Flight flight = new Flight(originChoiceBox.getValue().toString(),
                Integer.parseInt(departureHourTextField.getText()),
                Integer.parseInt(departureMinuteTextField.getText()),
                destinationChoiceBox.getValue().toString(),
                Integer.parseInt(arrivalHourTextField.getText()),
                Integer.parseInt(arrivalMinuteTextField.getText()));
        try {
            Utils.appendToFile(flight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PopUpWindowManager.closeCurrentWindow();
    }
}
