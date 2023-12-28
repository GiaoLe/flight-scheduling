package com.example.flightscheduling.minimumcrew;

import com.example.flightscheduling.flightGraph.Flight;
import com.example.flightscheduling.main.Utils;

import java.util.Arrays;

class GFG {
    // M is number of applicants
    // and N is number of jobs
    static final int M = 6;
    static final int N = 6;

    // A DFS based recursive function that
    // returns true if a matching for
    // vertex u is possible
    boolean bpm(boolean[][] bpGraph, int u,
                boolean[] seen, int[] matchR) {
        // Try every job one by one
        for (int v = 0; v < N; v++) {
            // If applicant u is interested
            // in job v and v is not visited
            if (bpGraph[u][v] && !seen[v]) {

                // Mark v as visited
                seen[v] = true;

                // If job 'v' is not assigned to
                // an applicant OR previously
                // assigned applicant for job v (which
                // is matchR[v]) has an alternate job available.
                // Since v is marked as visited in the
                // above line, matchR[v] in the following
                // recursive call will not get job 'v' again
                if (matchR[v] < 0 || bpm(bpGraph, matchR[v],
                        seen, matchR)) {
                    matchR[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    // Returns maximum number
    // of matching from M to N
    int maxBPM(boolean[][] bpGraph) {
        // An array to keep track of the
        // applicants assigned to jobs.
        // The value of matchR[i] is the
        // applicant number assigned to job i,
        // the value -1 indicates nobody is assigned.
        int[] matchR = new int[N];

        // Initially all jobs are available
        Arrays.fill(matchR, -1);

        // Count of jobs assigned to applicants
        int result = 0;
        for (int u = 0; u < M; u++) {
            // Mark all jobs as not seen
            // for next applicant.
            boolean[] seen = new boolean[N];

            // Find if the applicant 'u' can get a job
            if (bpm(bpGraph, u, seen, matchR))
                result++;
        }
        return result;
    }
}

public class BipartiteGraph {
    public BipartiteGraph() {
        try {
            Flight[] flights = Utils.readFile().toArray(new Flight[0]);
            boolean[][] graph = new boolean[flights.length][flights.length];
            for (int i = 0; i < flights.length; i++) {
                for (int j = 0; j < flights.length; j++) {
                    if (i != j && (flights[i].isReachableBy(flights[j]) || flights[i].isSameAirportAndReachableBy(flights[j]))) {
                        graph[i][j] = true;
                    }
                }
            }
            System.out.println(new GFG().maxBPM(graph));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
