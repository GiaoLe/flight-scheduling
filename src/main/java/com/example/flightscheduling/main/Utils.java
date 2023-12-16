package com.example.flightscheduling.main;

import com.example.flightscheduling.flightGraph.Flight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static int PLANES_AVAILABLE;
	public static final String FILENAME = "flights.txt";

	public static ArrayList<Flight> readFile() throws Exception {
		ArrayList<Flight> flights = new ArrayList<Flight>();

		Pattern intPattern = Pattern.compile("\\d+");
		Pattern stringPattern = Pattern.compile("[a-zA-Z]+");
		Matcher m;

		String orig, dest;
		int deH, deM, arH, arM;
		BufferedReader in;
		String line = null;
		try {
			in = new BufferedReader(new FileReader(Utils.FILENAME));

			line = in.readLine();
			m = intPattern.matcher(line);
			m.find();
			Utils.PLANES_AVAILABLE = Integer.parseInt(m.group());
			while ((line = in.readLine()) != null) {
				m = intPattern.matcher(line);
				m.find();
				deH = Integer.parseInt(m.group());
				m.find();
				deM = Integer.parseInt(m.group());
				m.find();
				arH = Integer.parseInt(m.group());
				m.find();
				arM = Integer.parseInt(m.group());

				m = stringPattern.matcher(line);
				m.find();
				orig = m.group();
				m.find();
				dest = m.group();

				flights.add(new Flight(orig, deH, deM, dest, arH, arM));
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(Utils.FILENAME + " does not exist.");
		} catch (IOException e) {
			throw new IOException("File reading error: "+Utils.FILENAME);
		} catch (IllegalStateException e) {
			throw new IllegalStateException("File patter error");
		}

		if (!flights.isEmpty()) {
			return flights;
		} else {
			throw new NullPointerException("No flights given.");
		}
	}

}