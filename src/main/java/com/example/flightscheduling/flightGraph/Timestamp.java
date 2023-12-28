package com.example.flightscheduling.flightGraph;
public class Timestamp implements Comparable<Timestamp> {

	private int hour, minutes;
	private int totalMins;

	/**
	 * Timestamp constructor.
	 * @param hour - hours in 24h form (0-23)
	 * @param minutes - minutes (0-59)
	 * @throws ArithmeticException in case time is out of bounds
	 */
	public Timestamp(int hour, int minutes) throws ArithmeticException {
		if ((hour < 0 || hour > 23) || minutes < 0 || minutes > 59) {
			throw new ArithmeticException();
		} else {
			this.hour = hour;
			this.minutes = minutes;
			totalMins = hour * 60 + minutes;
		}
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}


	/**
	 * Checks if two timestamps defer by "min" minutes
	 * @param t - the timestamp to check
	 * @param min - gap in minutes
	 * @return true if the first is "min" minutes ahead
	 */
	public boolean deferByMinutes(Timestamp t, int min) {
		return this.totalMins - t.totalMins >= min;
	}

	@Override
	public int compareTo(Timestamp t) {
		if (this.totalMins > t.totalMins) {
			return 1;
		} else if (this.totalMins < t.totalMins) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return String.format("%02d %02d", hour, minutes);
	}

	public int getMinute() {
		return minutes;
	}
}
