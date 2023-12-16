package com.example.flightscheduling;

public enum Scene {

    MAIN("main-view.fxml"),
    FLIGHT_ADDITION("add-flight-view.fxml");
    private static final String FXML_PATH = "views/";
    private final String fileName;

    Scene(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocationInResources() {
        return FXML_PATH + fileName;
    }
}