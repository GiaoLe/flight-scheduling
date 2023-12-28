package com.example.flightscheduling.maxFlow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class FordFulkersonAlgorithm {
    private int maxFlow;
    private final FlowNetwork network;
    private final String source;
    private final String sink;
    private HashMap<String, FlowEdge> edgeTo;


    /**
     * Runs the algorithm
     *
     * @param G      The Flow Network
     * @param source The source from which the algorithm searches
     * @param sink   The sink to which the algorithm searches to
     */
    public FordFulkersonAlgorithm(FlowNetwork G, String source, String sink) {
        network = G;
        this.source = source;
        this.sink = sink;
        maxFlow = 0;

        while (hasAugmentingPath()) {
            int bottleneck = Integer.MAX_VALUE;
            for (String v = sink; !v.equals(source); v = edgeTo.get(v).other(v)) {
                bottleneck = Math.min(bottleneck, edgeTo.get(v)
                        .residualCapacityTo(v));
            }
            for (String v = sink; !v.equals(source); v = edgeTo.get(v).other(v)) {
                edgeTo.get(v).addResidualFlowTo(v, bottleneck);
            }

            maxFlow += bottleneck;
        }

    }

    /**
     * Searches whether there exists an augmenting path from the source to the sink
     * and the set of vertices that consist this path are mapped to the edges that connect
     * them with the previous vertex on that path.
     *
     * @return if there exists an augmenting path (boolean)
     */
    public boolean hasAugmentingPath() {
        edgeTo = new HashMap<>();
        HashSet<String> marked = new HashSet<>();
        Queue<String> q;
		q = new LinkedList<>();

        q.add(source);
        marked.add(source);
        while (!q.isEmpty()) {
            String v = q.remove();
            for (FlowEdge e : network.adjacentList(v)) {
                String other = e.other(v);
                if (e.residualCapacityTo(other) > 0) {
                    if (!marked.contains(other)) {
                        edgeTo.put(other, e);
                        marked.add(other);
                        q.add(other);
                    }
                }
            }
        }
        return marked.contains(sink);
    }

    public int maxFlow() {
        return maxFlow;
    }

}
