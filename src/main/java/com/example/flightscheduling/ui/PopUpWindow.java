package com.example.flightscheduling.ui;

public enum PopUpWindow {

    FLIGHT_ADDITION("flight-addition-view.fxml");

    private static final String FXML_PATH = "views/";

    private final String fileName;

    PopUpWindow(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return FXML_PATH + fileName;
    }

}
