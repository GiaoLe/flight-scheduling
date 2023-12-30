package com.example.flightscheduling.maxFlow;

import java.util.*;

public class FordFulkersonAlgorithm {
    private final FlowNetwork network;
    private final String source;
    private final String sink;
    private final PathFindingAlgorithm pathFindingAlgorithm;
    private int maxFlow;
    private HashMap<String, FlowEdge> edgeTo;

    public FordFulkersonAlgorithm(FlowNetwork graph, String source, String sink, PathFindingAlgorithm algorithm) {
        network = graph;
        this.source = source;
        this.sink = sink;
        this.pathFindingAlgorithm = algorithm;
        maxFlow = 0;

        switch (pathFindingAlgorithm) {
            case DFS -> runDFS();
            case EDMONDS_KARP -> runEdmondsKarp();
            case CAPACITY_SCALING -> runCapacityScaling();
        };
    }

    private void runDFS() {
        while (existsPathDFS()) {
            handleFlow();
        }
    }

    private void runEdmondsKarp() {
        while (existsPathBFS()) {
            handleFlow();
        }
    }

    private void handleFlow() {
        int bottleneck = Integer.MAX_VALUE;
        for (String v = sink; !v.equals(source); v = edgeTo.get(v).other(v)) {
            bottleneck = Math.min(bottleneck, edgeTo.get(v).residualCapacityTo(v));
        }
        for (String vertex = sink; !vertex.equals(source); vertex = edgeTo.get(vertex).other(vertex)) {
            edgeTo.get(vertex).addResidualFlowTo(vertex, bottleneck);
        }
        maxFlow += bottleneck;
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

    private void runCapacityScaling() {
        Map<String, Integer> vertexIndex = new HashMap<>();
        int sourceIndex = 0;
        int sinkIndex = 0;
        int index = 0;
        for (String vertex : network.getAdjList().keySet()) {
            vertexIndex.put(vertex, index);
            index++;
            if (vertex.equals(source)) {
                sourceIndex = index;
            } else if (vertex.equals(sink)) {
                sinkIndex = index;
            }
        }

        CapacityScalingExample.CapacityScalingSolver solver = new CapacityScalingExample.CapacityScalingSolver(
                network.getAdjList().size(), sourceIndex, sinkIndex);
        for (String vertex : network.getAdjList().keySet()) {
            for (FlowEdge edge : network.adjacentList(vertex)) {
                solver.addEdge(vertexIndex.get(vertex), vertexIndex.get(edge.other(vertex)), edge.capacity());
            }
        }

        maxFlow = (int) solver.getMaxFlow();
        System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());
    }

    public int maxFlow() {
        return maxFlow;
    }

}
