package com.example.flightscheduling.maxFlow;

public class CapacityScaling {
    private long delta = 0;

    public CapacityScaling(FlowNetwork network, String sink, String source) {
        for (String vertex : network.getAdjacentList().keySet()) {
            for (FlowEdge edge : network.getAdjacentList().get(vertex)) {
                delta = Math.max(delta, edge.getCapacity());
            }
        }
        delta = Long.highestOneBit(delta);
    }
}
