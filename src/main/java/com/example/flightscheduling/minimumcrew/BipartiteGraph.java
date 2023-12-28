package com.example.flightscheduling.minimumcrew;

import com.example.flightscheduling.flightGraph.Flight;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class BipartiteGraph {

    @Getter
    private int maxBPM = 0;

    private final Flight[] flightsArray;
    
    private final boolean[][] graph;

    public BipartiteGraph(List<Flight> flights) {
        try {
            flightsArray = flights.toArray(new Flight[0]);
            graph = new boolean[flightsArray.length][flightsArray.length];
            for (boolean[] row : graph) {
                Arrays.fill(row, false);
            }
            for (int i = 0; i < flightsArray.length; i++) {
                for (int j = 0; j < flightsArray.length; j++) {
                    if (i != j && (flightsArray[i].isReachableBy(flightsArray[j]) || flightsArray[i].isSameAirportAndReachableBy(flightsArray[j]))) {
                        graph[i][j] = true;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    boolean bpm(boolean[][] bpGraph, int u,
                boolean[] seen, int[] matchR) {
        for (int v = 0; v < flightsArray.length; v++) {
            if (bpGraph[u][v] && !seen[v]) {
                seen[v] = true;
                if (matchR[v] < 0 || bpm(bpGraph, matchR[v], seen, matchR)) {
                    matchR[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    private int calculateMinPlanes(boolean[][] bpGraph) {
        int[] matchR = new int[flightsArray.length];
        Arrays.fill(matchR, -1);
        for (int u = 0; u < flightsArray.length; u++) {
            boolean[] seen = new boolean[flightsArray.length];
            if (bpm(bpGraph, u, seen, matchR)) {
                maxBPM++;
            }
        }
        int count = 0;
        for (int i = 0; i < flightsArray.length; i++) {
            if (matchR[i] == -1) {
                count++;
            }
        }
        return count;
    }
    
    public int getMinPlanes() {
        return calculateMinPlanes(graph);
    }
}
