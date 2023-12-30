package com.example.flightscheduling.maxFlow;

import java.util.*;

public class FordFulkersonAlgorithm {
    private final FlowNetwork network;
    private final String source;
    private final String sink;
    private int maxFlow;
    private HashMap<String, FlowEdge> edgeTo;

    private final PathFindingAlgorithm pathFindingAlgorithm;

    public FordFulkersonAlgorithm(FlowNetwork graph, String source, String sink, PathFindingAlgorithm algorithm) {
        network = graph;
        this.source = source;
        this.sink = sink;
        this.pathFindingAlgorithm = algorithm;
        maxFlow = 0;

        while (hasAugmentingPath()) {
            int bottleneck = Integer.MAX_VALUE;
            for (String v = sink; !v.equals(source); v = edgeTo.get(v).other(v)) {
                bottleneck = Math.min(bottleneck, edgeTo.get(v)
                        .residualCapacityTo(v));
            }
            for (String vertex = sink; !vertex.equals(source); vertex = edgeTo.get(vertex).other(vertex)) {
                edgeTo.get(vertex).addResidualFlowTo(vertex, bottleneck);
            }
            maxFlow += bottleneck;
        }
    }

    public boolean hasAugmentingPath() {
        return switch (pathFindingAlgorithm) {
            case DFS -> existsPathDFS();
            case EDMONDS_KARP -> existsPathBFS();
        };
    }

    private boolean existsPathBFS() {
        edgeTo = new HashMap<>();
        HashSet<String> visitedVertices = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(source);
        visitedVertices.add(source);
        while (!queue.isEmpty()) {
            String v = queue.remove();
            for (FlowEdge e : network.adjacentList(v)) {
                String other = e.other(v);
                if (e.residualCapacityTo(other) > 0) {
                    if (!visitedVertices.contains(other)) {
                        edgeTo.put(other, e);
                        visitedVertices.add(other);
                        queue.add(other);
                    }
                }
            }
        }
        return visitedVertices.contains(sink);
    }

    private boolean existsPathDFS() {
        edgeTo = new HashMap<>();
        HashSet<String> visitedVertices = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.add(source);
        visitedVertices.add(source);
        while (!stack.isEmpty()) {
            String v = stack.pop();
            for (FlowEdge e : network.adjacentList(v)) {
                String other = e.other(v);
                if (e.residualCapacityTo(other) > 0) {
                    if (!visitedVertices.contains(other)) {
                        edgeTo.put(other, e);
                        visitedVertices.add(other);
                        stack.add(other);
                    }
                }
            }
        }
        return visitedVertices.contains(sink);
    }

    public int maxFlow() {
        return maxFlow;
    }

}
