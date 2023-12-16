package com.example.flightscheduling.main;

import com.example.flightscheduling.maxFlow.FlowNetwork;
import lombok.Getter;

import java.util.List;

@Getter
public class FlightSchedule {
    private final List<FlightPath> flightPaths;

    public FlightSchedule(FlowNetwork flowNetwork) {
        Flights flights = new Flights(flowNetwork);
        flightPaths = flights.getFlightPaths();
    }

}
