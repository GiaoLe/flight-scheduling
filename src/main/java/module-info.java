module com.example.flightscheduling {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.controlsfx.controls;


    opens com.example.flightscheduling to javafx.fxml;
    exports com.example.flightscheduling;
    exports com.example.flightscheduling.flightGraph;
    exports com.example.flightscheduling.main;
    exports com.example.flightscheduling.maxFlow;
    exports com.example.flightscheduling.controllers;
    opens com.example.flightscheduling.controllers to javafx.fxml;
    exports com.example.flightscheduling.ui;
    opens com.example.flightscheduling.ui to javafx.fxml;
    exports com.example.flightscheduling.models;
    opens com.example.flightscheduling.models to javafx.fxml;
}