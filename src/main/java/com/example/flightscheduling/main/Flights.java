package com.example.flightscheduling.main;


import com.example.flightscheduling.maxFlow.FlowEdge;
import com.example.flightscheduling.maxFlow.FlowNetwork;

import java.util.LinkedList;
import java.util.List;

public class Flights {

	List<List<String>> flights = new LinkedList<>();

	public Flights(FlowNetwork network) {
		Iterable<FlowEdge> adjS = network.adjacentList("S");

		for (FlowEdge edge : adjS) {
			String other = edge.other("S");
			if (!other.equals("S*") && edge.residualCapacityTo(other) == 0) {
				List<String> temp = new LinkedList<>();
				temp.add(other);
				flights.add(temp);
			}
		}
		for (List<String> list : flights) {
			expandFlightList(list, network);
		}
	}

	private void expandFlightList(List<String> list, FlowNetwork network) {
		String ID = list.get(0);
		while (!ID.equals("T")) {
			for (FlowEdge edge : network.adjacentList(ID)) {
				String other = edge.other(ID);
				if (edge.from().equals(ID)
						&& edge.residualCapacityTo(other) == 0) {
					ID = other;
					if(!ID.equals("T"))
						list.add(ID);
					break;
				}
			}
		}

	}
	
	public void printFlights(){
		System.out.println("Number of planes used: " + flights.size());
		
		for (List<String> path : flights){
			System.out.println("Flight path:");
			for (String id : path)
				System.out.print(id + " ");
			System.out.println();
		}
	}

	public List<FlightPath> getFlightPaths(){
		List<FlightPath> flightPaths = new LinkedList<>();
		for (List<String> path : flights){
			FlightPath flightPath = new FlightPath();
			for (String id : path)
				flightPath.addAirport(id);
			flightPaths.add(flightPath);
		}
		return flightPaths;
	}
}
