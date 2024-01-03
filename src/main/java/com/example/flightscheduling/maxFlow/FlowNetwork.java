package com.example.flightscheduling.maxFlow;

import com.example.flightscheduling.demandGraph.Edge;
import com.example.flightscheduling.demandGraph.Vertex;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Getter
public class FlowNetwork {

    private final HashMap<String, List<FlowEdge>> adjacentList;

    public FlowNetwork(HashMap<Vertex, List<Edge>> graph) {
        adjacentList = new HashMap<>();
        for (Vertex v : graph.keySet()) {
            LinkedList<FlowEdge> list = new LinkedList<>();
            adjacentList.put(v.getId(), list);
        }

        for (Vertex v : graph.keySet()) {
            List<Edge> list = graph.get(v);
            for (Edge edge : list) {
                addEdge(new FlowEdge(v.getId(), edge.getNext(), edge.getCapacity()));
            }
        }
    }

    public void addEdge(FlowEdge e) {
        String from = e.from();
        String to = e.to();
        adjacentList.get(from).add(e);
        adjacentList.get(to).add(e);
    }

    public List<FlowEdge> adjacentList(String v) {
        return adjacentList.get(v);
    }
}
