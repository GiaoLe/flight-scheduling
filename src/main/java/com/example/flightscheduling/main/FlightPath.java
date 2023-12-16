package com.example.flightscheduling.main;

import java.util.ArrayList;
import java.util.List;

public class FlightPath {
    List<String> airports = new ArrayList<>();

    public void addAirport(String airport){
        airports.add(airport);
    }

    @Override
    public String toString() {
        return "FlightPath{" +
                "airports=" + airports +
                '}';
    }
}
