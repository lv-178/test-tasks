package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("предстоящие вылеты:");
        System.out.println(Filter.fromNow(flights).stream().map(Object::toString)
                .collect(Collectors.joining("\n")));
        System.out.println("вылеты, в информации о которых нет ошибок:");
        System.out.println(Filter.withoutErrors(flights).stream().map(Object::toString)
                .collect(Collectors.joining("\n")));
        System.out.println("вылеты с временем на земле меньше 2 часов:");
        System.out.println(Filter.longStay(flights).stream().map(Object::toString)
                .collect(Collectors.joining("\n")));
    }
}