package com.example.flightscheduling.flightGraph;

public record Route(String dest, int lowerBound, int capacity) {


	@Override
	public boolean equals(Object o) {
		if (getClass() != o.getClass()) {
			return false;
		}
		Route other = (Route) o;
		return dest.equals(other.dest) && lowerBound == other.lowerBound && capacity == other.capacity;
	}

	@Override
	public String toString() {
		return dest + "|L:" + lowerBound + " " + "C:" + capacity;
	}
}