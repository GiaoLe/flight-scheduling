package com.example.flightscheduling.main;


import com.example.flightscheduling.maxFlow.FlowEdge;
import com.example.flightscheduling.maxFlow.FlowNetwork;

import java.util.LinkedList;
import java.util.List;

public class Flights {

    List<List<String>> flights = new LinkedList<>();

    public Flights(FlowNetwork network) {
        List<FlowEdge> adjacentVerticesOfSource = network.adjacentList("S");

        for (FlowEdge edge : adjacentVerticesOfSource) {
            String other = edge.other("S");
            if (!other.equals("S*") && edge.residualCapacityTo(other) == 0) {
                List<String> temp = new LinkedList<>();
                temp.add(other);
                flights.add(temp);
            }
        }
        for (List<String> list : flights) {
            expandFlightList(list, network);
        }
    }

    private void expandFlightList(List<String> list, FlowNetwork network) {
        String ID = list.get(0);
        while (!ID.equals("T")) {
            for (FlowEdge edge : network.adjacentList(ID)) {
                String other = edge.other(ID);
                if (edge.from().equals(ID) && edge.residualCapacityTo(other) == 0) {
                    ID = other;
                    if (!ID.equals("T"))
                        list.add(ID);
                    break;
                }
            }
        }

    }

    public List<FlightPath> getFlightPaths() {
        List<FlightPath> flightPaths = new LinkedList<>();
        for (List<String> path : flights) {
            FlightPath flightPath = new FlightPath();
            for (String id : path)
                flightPath.addAirport(id);
            flightPaths.add(flightPath);
        }
        return flightPaths;
    }
}
