package com.example.flightscheduling.main;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FlightPath {
    List<String> airports = new ArrayList<>();

    public void addAirport(String airport) {
        airports.add(airport);
    }

    @Override
    public String toString() {
        return "Flight path: " + airports;
    }
}
