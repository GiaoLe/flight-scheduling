package com.example.flightscheduling.flightGraph;

import com.example.flightscheduling.main.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

	private HashMap<String, ArrayList<Route>> graph;
	private ArrayList<Flight> flights;
	private ArrayList<Route> routes;

	public Graph(ArrayList<Flight> flights) {
		this.flights = flights;
		graph = new HashMap<>();

		initializeGraph();
		extendGraph();
	}

	private void initializeGraph() {
		ArrayList<Route> sRoutes = new ArrayList<>();

		for (Flight f : flights) {
			String origKey = f.getOrigin() + "_" + f.getDepartureTime();
			String destKey = f.getDestination() + "_" + f.getArrivalTime();
			routes = new ArrayList<>();
			
			if (graph.containsKey(origKey)) {
				routes.addAll(graph.get(origKey));
			}
			routes.add(new Route(destKey, 1, 1));
			graph.put(origKey, routes);

			Route sRoute = new Route(origKey, 0, 1);
			if (!sRoutes.contains(sRoute)) {
				sRoutes.add(sRoute);
			}
			
			ArrayList<Route> tRoutes = new ArrayList<>();
			tRoutes.add(new Route("T", 0, 1));
			graph.put(destKey, tRoutes);	
		}

		sRoutes.add(new Route("T", 0, Utils.PLANES_AVAILABLE));
		graph.put("S", sRoutes);
		

	}

	private void extendGraph() {
		for (Flight fi : flights) {
			for (Flight fj : flights) {
				if (fi != fj) {
					if (fj.isSameAirportAndReachableBy(fi) || fj.isReachableBy(fi)) {
						String key = fi.getDestination() + "_" + fi.getArrivalTime();
						routes = new ArrayList<>();
						
						if (graph.containsKey(key)) {
							routes.addAll(graph.get(key));
						}
						routes.add(new Route(fj.getOrigin() + "_" + fj.getDepartureTime(), 0, 1));
						graph.put(key, routes);
					}
				}
			}
		}
	}

	public HashMap<String, ArrayList<Route>> getGraph() {
		return graph;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : graph.keySet()) {
			sb.append(key).append(" -> ").append(graph.get(key)).append("\n");
		}
		return sb.toString();
	}
}
