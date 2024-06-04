package com.example.springexample;

import java.util.ArrayList;

public class FindAverage {
    public static Double findDifference(ArrayList<Flight> flights) {
        Double average = flights.stream().filter(flight ->
                flight.getOriginName().equals("Владивосток") & flight.getDestinationName().equals("Тель-Авив"))
                .mapToDouble(Flight::getPrice).average().orElse(0.0);
        int numberOfRightFlights = (int) flights.stream().filter(flight ->
                flight.getOriginName().equals("Владивосток") & flight.getDestinationName().equals("Тель-Авив")).count();
        Double median = (double) flights.get(numberOfRightFlights / 2 - 1).getPrice();
        return Math.abs(average - median);
    }
}
