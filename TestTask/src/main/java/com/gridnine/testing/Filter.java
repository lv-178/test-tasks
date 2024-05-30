package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {

    public static List<Flight> fromNow(List<Flight> flights) {
        return flights.stream().filter(flight -> flight.getSegments()
                .getFirst()
                .getDepartureDate()
                .isAfter(LocalDateTime.now()))
            .collect(Collectors.toList());
    }

    public static List<Flight> withoutErrors(List<Flight> flights) {
        return flights.stream().filter(flight -> flight.getSegments()
                .stream()
                .noneMatch(segment -> segment.getArrivalDate()
                        .isBefore(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    public static List<Flight> longStay(List<Flight> flights) {
        List<Flight> flightsLongStay = new ArrayList<>();
        long timeOnEarth;
        for (Flight flight : flights) {
            timeOnEarth = 0;
            for (int i = 1; i < flight.getSegments().size(); i++) {
                timeOnEarth += (flight.getSegments().get(i).getDepartureDate()
                        .toEpochSecond(ZoneOffset.ofHours(3))
                        - flight.getSegments().get(i-1).getArrivalDate()
                        .toEpochSecond(ZoneOffset.ofHours(3)));
            }
            if (timeOnEarth < 2 * 3600) {
                flightsLongStay.add(flight);
            }
        }
        return flightsLongStay;
    }
}
