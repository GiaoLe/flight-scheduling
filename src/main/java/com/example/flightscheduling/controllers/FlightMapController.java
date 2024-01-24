package com.example.flightscheduling.controllers;

import com.example.flightscheduling.Airport;
import com.example.flightscheduling.Main;
import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.FlightPath;
import com.example.flightscheduling.main.Utils;
import com.example.flightscheduling.maxFlow.PathFindingAlgorithm;
import com.example.flightscheduling.models.MainModel;
import com.example.flightscheduling.ui.PopUpWindow;
import com.example.flightscheduling.ui.PopUpWindowManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

public class FlightMapController {
    private final MainModel mainModel = new MainModel();
    private final Image airplaneImage = new Image(
            Objects.requireNonNull(Main.class.getResourceAsStream("images/airplane.png")));
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
    public Label mouseLabel;
    public AnchorPane anchorPane;

    private final double clockRate = 0.02;
    public Label timeLabel;

    @FXML
    public void initialize() {
        anchorPane.setOnMouseMoved(event -> mouseLabel.setText("X: " + event.getX() + " Y: " + event.getY()));
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
        startAnimation();
    }

    private void startAnimation() {
        for (FlightPath flightPath : mainModel.getFlightPaths()) {
            for (int i = 0; i < flightPath.getAirports().size() - 1; i++) {
                Airport from = Airport.valueOf(flightPath.getAirports().get(i).substring(0, 3));
                Airport to = Airport.valueOf(flightPath.getAirports().get(i + 1).substring(0, 3));
                int departureHour = Integer.parseInt(flightPath.getAirports().get(i).substring(4, 6));
                int departureMinute = Integer.parseInt(flightPath.getAirports().get(i).substring(7, 9));
                int arrivalHour = Integer.parseInt(flightPath.getAirports().get(i + 1).substring(4, 6));
                int arrivalMinute = Integer.parseInt(flightPath.getAirports().get(i + 1).substring(7, 9));
                int departureTime = departureHour * 60 + departureMinute;
                int arrivalTime = arrivalHour * 60 + arrivalMinute;
                int duration = arrivalTime - departureTime;
                Line line = new Line(from.getPosition().getX(), from.getPosition().getY(), to.getPosition().getX(),
                                     to.getPosition().getY());
                Circle circle = new Circle(10);
                circle.setFill(new ImagePattern(airplaneImage));
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(departureTime * clockRate), event -> {
                            anchorPane.getChildren().add(circle);
                            anchorPane.getChildren().add(line);
                            TranslateTransition transition = new TranslateTransition();
                            transition.setDuration(Duration.seconds(duration * clockRate));
                            transition.setNode(circle);
                            transition.setFromX(from.getPosition().getX());
                            transition.setFromY(from.getPosition().getY());
                            transition.setToX(to.getPosition().getX());
                            transition.setToY(to.getPosition().getY());
                            transition.play();
                        }),
                        new KeyFrame(Duration.seconds(arrivalTime * clockRate), event -> {
                            anchorPane.getChildren().remove(circle);
                            anchorPane.getChildren().remove(line);
                        }));
                timeline.setCycleCount(1);
                timeline.play();
            }
        }
    }

    public void stopButtonOnAction() {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        flightPathsListView.getItems().clear();
        //TODO: Stop the air traffic animation

    }
}
