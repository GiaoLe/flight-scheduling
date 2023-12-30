package com.example.flightscheduling.controllers;

import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.FlightPath;
import com.example.flightscheduling.main.Utils;
import com.example.flightscheduling.minimumcrew.MaximumBipartiteMatching;
import com.example.flightscheduling.models.MainModel;
import com.example.flightscheduling.ui.Scene;
import com.example.flightscheduling.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class MainController {
    public ListView<Flight> flightListView;
    public ListView<FlightPath> flightPathsListView;
    public TextField numberOfAvailablePlanes;
    public Label morePlanesRequiredLabel;
    public Label minimumPlanesLabel;
    public Button changingNumberOfPlanesButton ;

    private MainModel mainModel;

    @FXML
    public void initialize() {
        morePlanesRequiredLabel.setVisible(false);
        try {
            mainModel = new MainModel();
            displayAvailablePlanesCount();
            flightListView.getItems().addAll(mainModel.getFlights());
            processFlightsData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        displayMinimumPlanesRequired();
    }

    private void displayAvailablePlanesCount() {
        numberOfAvailablePlanes.setText(String.valueOf(Utils.PLANES_AVAILABLE));
    }

    private void displayMinimumPlanesRequired() {
        minimumPlanesLabel.setText("Minimum planes required: " + new MaximumBipartiteMatching(mainModel.getFlights()).getMinPlanes());
    }

    public void addFlightButtonOnAction() {
        SceneManager.switchScene(Scene.FLIGHT_ADDITION);
    }

    public void removeFlightButtonOnAction() {
        Flight selectedFlight = flightListView.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            flightListView.getItems().remove(selectedFlight);
        }
        processFlightsData();
    }

    private void processFlightsData() {
        displayMinimumPlanesRequired();
        displayAvailablePlanesCount();
        mainModel.processFlights(new ArrayList<>(flightListView.getItems()));
        flightPathsListView.getItems().clear();
        if (mainModel.isSolvable()) {
            morePlanesRequiredLabel.setVisible(false);
            flightPathsListView.getItems().setAll(mainModel.getFlightPaths());
        } else {
            morePlanesRequiredLabel.setVisible(true);
        }
    }

    public void changingNumberOfPlanesButtonOnAction() {
        try {
            Utils.PLANES_AVAILABLE = Integer.parseInt(numberOfAvailablePlanes.getText());
            Utils.saveToFile(mainModel.getFlights());
            processFlightsData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}