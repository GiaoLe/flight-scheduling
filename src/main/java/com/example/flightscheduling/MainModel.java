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
    private boolean solvable;

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

    public void processFlights(ArrayList<Flight> flights) {
        saveFlights(flights);
        Graph inputGraph = new Graph(flights);
        DemandGraph demandGraph = new DemandGraph(inputGraph.getGraph());
        if (!demandGraph.isSolvable()) {
            System.out.println("The problem is not solvable");
            return;
        }

        FlowNetwork flowNetwork = new FlowNetwork(demandGraph.getGraph());

        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm(flowNetwork, demandGraph.source(), demandGraph.sink());
        if (demandGraph.getTotalDemand() != fordFulkersonAlgorithm.maxFlow()) {
            solvable = false;
        } else {
            solvable = true;
            flightSchedule = new FlightSchedule(flowNetwork);
        }
    }

    public List<FlightPath> getFlightPaths() {
        return flightSchedule.getFlightPaths();
    }

    public void saveFlights(ArrayList<Flight> flights) {
        this.flights = flights;
        try {
            Utils.saveToFile(flights);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
