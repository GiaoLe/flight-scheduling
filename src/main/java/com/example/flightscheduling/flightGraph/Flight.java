package com.example.flightscheduling.flightGraph;

import lombok.Data;

@Data
public class Flight {

    private String origin;
    private String destination;
    private Timestamp departureTime;
    private Timestamp arrivalTime;

    public Flight(String origin, int depHour, int depMin, String destination, int arrHour, int arrMin) {
        this.origin = origin;
        this.destination = destination;
        departureTime = new Timestamp(depHour, depMin);
        arrivalTime = new Timestamp(arrHour, arrMin);
    }

    public boolean isReachableBy(Flight fi) {
        return this.getDepartureTime().deferByMinutes(fi.getArrivalTime(), 180);
    }

    public boolean isSameAirportAndReachableBy(Flight fi) {
        return this.getOrigin().equals(fi.getDestination())
                && this.getDepartureTime().deferByMinutes(fi.getArrivalTime(), 60);
    }

    @Override
    public String toString() {
        return "Origin: " + origin + "\n"
                + "Destination: " + destination + "\n"
                + "Departure Time: " + departureTime + "\n"
                + "Arrival Time: " + arrivalTime + "\n";
    }

    public String toFileString() {
        return origin + " " + departureTime + " " + destination + " " + arrivalTime;
    }
}
