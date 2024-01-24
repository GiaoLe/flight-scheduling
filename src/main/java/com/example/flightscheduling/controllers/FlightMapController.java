package com.example.flightscheduling.controllers;

import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.FlightPath;
import com.example.flightscheduling.main.Utils;
import com.example.flightscheduling.maxFlow.PathFindingAlgorithm;
import com.example.flightscheduling.models.MainModel;
import com.example.flightscheduling.ui.PopUpWindow;
import com.example.flightscheduling.ui.PopUpWindowManager;
import com.example.flightscheduling.ui.Scene;
import com.example.flightscheduling.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class FlightMapController {
    private final MainModel mainModel = new MainModel();
    public ChoiceBox<PathFindingAlgorithm> pathFindingAlgorithmChoiceBox;
    public ListView<FlightPath> flightPathsListView;
    public ListView<Flight> flightListView;
    public Button changingNumberOfPlanesButton;
    public TextField numberOfAvailablePlanes;
    public Label numberOfPlanesErrorLabel;
    public Button startButton;
    public Button stopButton;
    public Label numberOfPlanesLabel;
    public Label minPlanesLabel;

    @FXML
    public void initialize() {
        initializeMinPlanesLabel();
        initializePathFindingAlgorithmChoiceBox();
        numberOfAvailablePlanes.setText(String.valueOf(Utils.PLANES_AVAILABLE));
        mainModel.processFlightsWithSpecifiedPathFindingAlgorithm(pathFindingAlgorithmChoiceBox.getValue());
        flightListView.getItems().addAll(mainModel.getFlights());
    }

    private void initializeMinPlanesLabel() {
        minPlanesLabel.setText("(Min: " + mainModel.getMinimumRequiredPlanes() + ")");
    }

        private void initializePathFindingAlgorithmChoiceBox() {
        pathFindingAlgorithmChoiceBox.getItems().addAll(PathFindingAlgorithm.values());
        pathFindingAlgorithmChoiceBox.setValue(PathFindingAlgorithm.DFS);
    }

    public void addFlightButtonOnAction() {
        PopUpWindowManager.openPopUpWindow(PopUpWindow.FLIGHT_ADDITION);
        processChangedFlights();
    }

    public void removeFlightButtonOnAction() {
        Flight selectedFlight = flightListView.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            flightListView.getItems().remove(selectedFlight);
        }
        processChangedFlights();
        try {
            Utils.saveToFile(mainModel.getFlights());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processChangedFlights() {
        mainModel.setFlights(new ArrayList<>(flightListView.getItems()));
        initializeMinPlanesLabel();
    }

    public void changingNumberOfPlanesButtonOnAction() throws Exception {
        if (Integer.parseInt(numberOfAvailablePlanes.getText()) < mainModel.getMinimumRequiredPlanes()) {
            numberOfPlanesErrorLabel.setVisible(true);
        } else {
            numberOfPlanesErrorLabel.setVisible(false);
            Utils.PLANES_AVAILABLE = Integer.parseInt(numberOfAvailablePlanes.getText());
            Utils.saveToFile(mainModel.getFlights());
        }
    }

    public void startButtonOnAction() {
        stopButton.setDisable(false);
        startButton.setDisable(true);
        mainModel.processFlightsWithSpecifiedPathFindingAlgorithm(pathFindingAlgorithmChoiceBox.getValue(),
                                                                  new ArrayList<>(flightListView.getItems()));
        if (mainModel.isSolvable()) {
            flightPathsListView.getItems().setAll(mainModel.getFlightPaths());
        }
    }

    public void stopButtonOnAction() {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        flightPathsListView.getItems().clear();
        //TODO: Stop the air traffic animation
    }
}
