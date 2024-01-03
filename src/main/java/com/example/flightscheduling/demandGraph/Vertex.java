package com.example.flightscheduling.demandGraph;


import lombok.Getter;

@Getter
public class Vertex {


	private int demand;
	private final String id;

	public Vertex(String id, int demand) {
		this.id = id;
		this.demand = demand;
	}

	public void changeDemand(int offset) {
		demand += offset;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "ID: "+id+" D: "+demand;
	}
	
	

}
