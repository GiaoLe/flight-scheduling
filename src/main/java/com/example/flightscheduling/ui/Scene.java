package com.example.flightscheduling.ui;

public enum Scene {

    FLIGHT_MAP("flight-map-view.fxml");
    private static final String FXML_PATH = "views/";
    private final String fileName;

    Scene(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocationInResources() {
        return FXML_PATH + fileName;
    }
}
