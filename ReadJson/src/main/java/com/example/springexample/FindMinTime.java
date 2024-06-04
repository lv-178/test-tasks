package com.example.springexample;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.TreeMap;

public class FindMinTime {
    public static TreeMap<String, Long> find(ArrayList<Flight> flights) {
        TreeMap<String, Long> flightTimes = new TreeMap<>();

        flights.forEach(flight -> {
            if(flight.getOriginName().equals("Владивосток") &
                    flight.getDestinationName().equals("Тель-Авив")) {
                String carrier = flight.getCarrier();
                Long flightTime = flight.getArrivalTime().toEpochSecond(ZoneOffset.ofHours(3)) -
                        flight.getDepartureTime().toEpochSecond(ZoneOffset.ofHours(3));
                if(flightTimes.get(carrier) == null) {
                    flightTimes.put(carrier, flightTime);
                } else {
                    if(flightTime < flightTimes.get(carrier)) {
                        flightTimes.put(carrier, flightTime);
                    }
                }
            }
        });
        return flightTimes;
    }

    public static String toString(TreeMap<String, Long> flightTimes) {
        StringBuilder flightsString = new StringBuilder();
        flightTimes.keySet().forEach(key -> {
            flightsString.append("перевозчик: ").append(key)
                    .append("\nвремя полёта: ").append((int) (flightTimes.get(key) / 3600))
                    .append(" ч ").append((flightTimes.get(key) % 3600) / 60).append(" мин\n");
        });
        return flightsString.toString();
    }
}
