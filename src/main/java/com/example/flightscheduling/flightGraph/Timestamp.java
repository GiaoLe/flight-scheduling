package com.example.flightscheduling.flightGraph;

import lombok.Getter;

//TODO: Change Timestamp to LocalTime
@Getter
public class Timestamp implements Comparable<Timestamp> {

    private final int hour;
    private final int minutes;
    private final int totalMinutes;

    public Timestamp(int hour, int minutes) throws ArithmeticException {
        if ((hour < 0 || hour > 23) || minutes < 0 || minutes > 59) {
            throw new ArithmeticException();
        } else {
            this.hour = hour;
            this.minutes = minutes;
            totalMinutes = hour * 60 + minutes;
        }
    }

    public boolean deferByMinutes(Timestamp t, int min) {
        return this.totalMinutes - t.totalMinutes >= min;
    }

    @Override
    public int compareTo(Timestamp t) {
        return Integer.compare(this.totalMinutes, t.totalMinutes);
    }

    @Override
    public String toString() {
        return String.format("%02d %02d", hour, minutes);
    }
}
