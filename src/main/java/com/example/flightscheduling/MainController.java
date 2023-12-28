package com.example.flightscheduling;

import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.FlightPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    public ListView<Flight> flightListView;
    public ListView<FlightPath> flightPathsListView;
    public TextField numberOfAvailablePlanes;

    private MainModel mainModel;

    @FXML
    public void initialize() {
        try {
            mainModel = new MainModel();
            flightListView.getItems().addAll(mainModel.getFlights());
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

    public void processButtonOnAction() {
        mainModel.processFlights(new ArrayList<>(flightListView.getItems()));
        flightPathsListView.getItems().setAll(mainModel.getFlightPaths());
    }
}