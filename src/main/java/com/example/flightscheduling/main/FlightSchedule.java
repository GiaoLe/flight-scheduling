package com.example.flightscheduling.main;

import com.example.flightscheduling.maxFlow.FlowNetwork;
import lombok.Getter;

import java.util.List;

@Getter
public class FlightSchedule {
    private final List<FlightPath> flightPaths;

    public FlightSchedule(FlowNetwork flowNetwork) {
        flightPaths = new Flights(flowNetwork).getFlightPaths();
    }

}
