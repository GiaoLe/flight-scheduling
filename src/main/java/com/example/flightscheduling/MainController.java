package com.example.flightscheduling;

import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.FlightPath;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainController {
    public ListView<Flight> flightListView;
    public ListView<FlightPath> flightPathsListView;
    public TextField numberOfAvailablePlanes;

    @FXML
    public void initialize() {
        try {
            MainModel mainModel = new MainModel();
            flightListView.getItems().addAll(mainModel.getFlights());
            flightPathsListView.getItems().addAll(mainModel.getFlightPaths());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addFlightButtonOnAction() {
        SceneManager.switchScene(Scene.FLIGHT_ADDITION);
    }

    public void removeFlightButtonOnAction() {
        Flight selectedFlight = flightListView.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            flightListView.getItems().remove(selectedFlight);
        }
    }
}