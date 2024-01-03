package com.example.flightscheduling.maxFlow;

import lombok.Getter;

public class FlowEdge {

    private final String from;
    private final String to;
    @Getter
    private final int capacity;
    private int flow;

    public FlowEdge(String from, String to, int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("The capacity can't be negative");
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        flow = 0;
    }

    public String from() {
        return from;
    }

    public String to() {
        return to;
    }

    public String other(String v) {
        if (v.equals(from)) {
            return to;
        }
        if (v.equals(to)) {
            return from;
        }
        throw new IllegalArgumentException("The given vertex is not connected to this edge");
    }

    public int residualCapacityTo(String v) {
        if (v.equals(from)) {
            return flow;
        }
        if (v.equals(to)) {
            return capacity - flow;
        }
        throw new IllegalArgumentException("The given vertex is not connected to this edge");
    }

    public void addResidualFlowTo(String v, int amount) {
        if (v.equals(from)) {
            flow -= amount;
        } else if (v.equals(to)) {
            flow += amount;
        } else {
            throw new IllegalArgumentException("The given vertex is not connected to this edge");
        }
    }
}
