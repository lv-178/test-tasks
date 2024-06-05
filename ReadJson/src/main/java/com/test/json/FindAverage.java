package com.test.json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FindAverage {
    public static Double findDifference(ArrayList<Flight> flights) {
        double average = flights.stream().filter(flight ->
                flight.getOriginName().equals("Владивосток") & flight.getDestinationName().equals("Тель-Авив"))
                .mapToDouble(Flight::getPrice).average().orElse(0.0);

        List<Flight> rightFlightsSorted = flights.stream().filter(flight ->
                        flight.getOriginName().equals("Владивосток") & flight.getDestinationName().equals("Тель-Авив"))
                .sorted(Comparator.comparing(Flight::getPrice)).toList();

        double median = 0;
        if(rightFlightsSorted.size() % 2 == 1) {
            median = (double) rightFlightsSorted.get(rightFlightsSorted.size() / 2).getPrice();
        } else if(!rightFlightsSorted.isEmpty()) {
            median = (double) ((rightFlightsSorted.get(rightFlightsSorted.size() / 2).getPrice() +
                    rightFlightsSorted.get(rightFlightsSorted.size() / 2 - 1).getPrice()) / 2);
        }
        return Math.abs(average - median);
    }
}
