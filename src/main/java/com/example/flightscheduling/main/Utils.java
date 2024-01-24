package com.example.flightscheduling.main;

import com.example.flightscheduling.flightGraph.Flight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Utils {

    public static int PLANES_AVAILABLE;
    public static final String FILENAME = "flights.txt";

    public static ArrayList<Flight> readFile() throws Exception {
        ArrayList<Flight> flights = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(Utils.FILENAME));
        Utils.PLANES_AVAILABLE = Integer.parseInt(bufferedReader.readLine());
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] code = line.split(" ");
            flights.add(new Flight(code[0],
                    Integer.parseInt(code[1]),
                    Integer.parseInt(code[2]),
                    code[3],
                    Integer.parseInt(code[4]),
                    Integer.parseInt(code[5])));
        }
        return flights;
    }

    public static void saveToFile(ArrayList<Flight> flights) throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Utils.FILENAME, false));
        bufferedWriter.write(Utils.PLANES_AVAILABLE + "\n");
        for (Flight flight : flights) {
            bufferedWriter.write(flight.toFileString() + "\n");
        }
        bufferedWriter.close();
    }

    public static void appendToFile(Flight flight) throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Utils.FILENAME, true));
        bufferedWriter.write(flight.toFileString() + "\n");
        bufferedWriter.close();
    }
}