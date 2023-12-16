package com.example.flightscheduling;

import com.example.flightscheduling.demandGraph.DemandGraph;
import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.flightGraph.Graph;
import com.example.flightscheduling.main.Flights;
import com.example.flightscheduling.main.Utils;
import com.example.flightscheduling.maxFlow.FlowNetwork;
import com.example.flightscheduling.maxFlow.FordFulkersonAlgorithm;
import lombok.Getter;

import java.util.ArrayList;

public class MainModel {

    @Getter
    private ArrayList<Flight> flights;

    @Getter
    private Flights flightPaths;

    public MainModel() {
        try {
            flights = Utils.readFile();
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
                flightPaths = new Flights(flowNetwork);
                flightPaths.printFlights();
            }
        } catch (Exception e) {
            System.err.println("Something went wrong");
        }
    }
}
