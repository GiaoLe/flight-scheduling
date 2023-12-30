package com.example.flightscheduling.minimumcrew;

import com.example.flightscheduling.flightGraph.Flight;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class MaximumBipartiteMatching {

    private static final int NONE = -1;
    private Flight[] flightsArray;
    private boolean[][] graph;
    @Getter
    private int maximumBipartiteMatching = 0;

    public MaximumBipartiteMatching(List<Flight> flights) {
        initializeEdmondsMatrix(flights);
    }

    private void initializeEdmondsMatrix(List<Flight> flights) {
        flightsArray = flights.toArray(new Flight[0]);
        graph = new boolean[flightsArray.length][flightsArray.length];
        for (boolean[] row : graph) {
            Arrays.fill(row, false);
        }
        for (int i = 0; i < flightsArray.length; i++) {
            for (int j = 0; j < flightsArray.length; j++) {
                if (i != j && (flightsArray[i].isReachableBy(
                        flightsArray[j]) || flightsArray[i].isSameAirportAndReachableBy(flightsArray[j]))) {
                    graph[i][j] = true;
                }
            }
        }
    }

    boolean foundMatchInBipartiteGraph(boolean[][] bipartiteGraph, int u, boolean[] matched, int[] matchingVertexOf) {
        for (int v = 0; v < flightsArray.length; v++) {
            if (bipartiteGraph[u][v] && !matched[v]) {
                matched[v] = true;
                if (matchingVertexOf[v] < 0 || foundMatchInBipartiteGraph(bipartiteGraph, matchingVertexOf[v], matched,
                                                                          matchingVertexOf)) {
                    matchingVertexOf[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    private int calculateMinPlanes(boolean[][] bpGraph) {
        int[] matchingVertexOf = new int[flightsArray.length];
        Arrays.fill(matchingVertexOf, NONE);
        for (int vertexIndex = 0; vertexIndex < flightsArray.length; vertexIndex++) {
            boolean[] matchedVertices = new boolean[flightsArray.length];
            if (foundMatchInBipartiteGraph(bpGraph, vertexIndex, matchedVertices, matchingVertexOf)) {
                maximumBipartiteMatching++;
            }
        }
        int countFlightCannotBeContinued = 0;
        for (int vertexIndex = 0; vertexIndex < flightsArray.length; vertexIndex++) {
            if (matchingVertexOf[vertexIndex] == NONE) {
                countFlightCannotBeContinued++;
            }
        }
        return countFlightCannotBeContinued;
    }

    public int getMinPlanes() {
        return calculateMinPlanes(graph);
    }
}
