package com.example.flightscheduling;

import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MainController {
    public ListView<Flight> flightListView;

    private final MainModel model = new MainModel();
    public ListView flightPathsListView;

    @FXML
    public void initialize() {
        try {
            flightListView.getItems().addAll(model.getFlights());
            flightPathsListView.getItems().addAll(model.getFlightPaths());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}