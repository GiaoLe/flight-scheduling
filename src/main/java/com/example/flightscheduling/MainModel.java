package com.example.flightscheduling;

import com.example.flightscheduling.demandGraph.DemandGraph;
import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.flightGraph.Graph;
import com.example.flightscheduling.main.FlightPath;
import com.example.flightscheduling.main.FlightSchedule;
import com.example.flightscheduling.main.Utils;
import com.example.flightscheduling.maxFlow.FlowNetwork;
import com.example.flightscheduling.maxFlow.FordFulkersonAlgorithm;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MainModel {

    @Getter
    private ArrayList<Flight> flights;

    private FlightSchedule flightSchedule;

    public MainModel() {
        try {
            flights = Utils.readFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        processFlights(flights);
    }

    public MainModel(ArrayList<Flight> flights) {
        processFlights(flights);
    }

    public void processFlights(ArrayList<Flight> flights) {
        this.flights = flights;
        saveFlights();
        Graph inputGraph = new Graph(flights);
        DemandGraph demandGraph = new DemandGraph(inputGraph.getGraph());
        if (!demandGraph.isSolvable()) {
            System.out.println("The problem is not solvable");
            return;
        }

        FlowNetwork flowNetwork = new FlowNetwork(demandGraph.getGraph());

        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm(flowNetwork, demandGraph.source(), demandGraph.sink());
        if (demandGraph.getTotalDemand() != fordFulkersonAlgorithm.maxFlow()) {
            System.out.println("More planes are needed");
        } else {
            flightSchedule = new FlightSchedule(flowNetwork);
        }
    }

    public List<FlightPath> getFlightPaths() {
        return flightSchedule.getFlightPaths();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void saveFlights() {
        try {
            Utils.saveToFile(flights);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
