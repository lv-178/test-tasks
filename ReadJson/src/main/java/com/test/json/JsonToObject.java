package com.test.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JsonToObject {

    public static ArrayList<Flight> readJson(String directory) {

        ArrayList<Flight> flights = new ArrayList<>();
        JSONParser parser = new JSONParser();

        String content = "";

        try {
            ClassLoader classLoader = JsonToObject.class.getClassLoader();
            String path = classLoader.getResource(directory).getPath();
            content = Files.readString(Paths.get(path));

            JSONObject jsonObject = (JSONObject) parser.parse(content);

            jsonObject.keySet().forEach(object ->
            {
                JSONArray tickets = (JSONArray) jsonObject.get("tickets");
                tickets.forEach(object1 -> {
                    try {
                        JSONObject jsonObjectFlight = (JSONObject) parser.parse(object1.toString());
                        Flight flight = new Flight();
                        flight.setOrigin((String) jsonObjectFlight.get("origin"));
                        flight.setOriginName((String) jsonObjectFlight.get("origin_name"));
                        flight.setDestination((String) jsonObjectFlight.get("destination"));
                        flight.setDestinationName((String) jsonObjectFlight.get("destination_name"));
                        flight.setDepartureTime(LocalDateTime.of(LocalDate.parse(((String) jsonObjectFlight.get("departure_date")), DateTimeFormatter.ofPattern("dd.MM.yy")), LocalTime.parse(((String) jsonObjectFlight.get("departure_time")), DateTimeFormatter.ofPattern("[HH:mm][H:mm]"))));
                        flight.setArrivalTime(LocalDateTime.of(LocalDate.parse(((String) jsonObjectFlight.get("arrival_date")), DateTimeFormatter.ofPattern("dd.MM.yy")), LocalTime.parse(((String) jsonObjectFlight.get("arrival_time")), DateTimeFormatter.ofPattern("[HH:mm][H:mm]"))));
                        flight.setCarrier((String) jsonObjectFlight.get("carrier"));
                        flight.setStops((long) jsonObjectFlight.get("stops"));
                        flight.setPrice((long) jsonObjectFlight.get("price"));
                        flights.add(flight);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        } catch(Exception e) {
            System.out.println("error in JsonToObject");
            e.printStackTrace();
        }
        return flights;
    }
}
