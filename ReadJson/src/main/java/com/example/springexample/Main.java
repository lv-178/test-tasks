package com.example.springexample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws java.io.IOException, org.json.simple.parser.ParseException {
        ArrayList<Flight> flights = new ArrayList<>();
        String directory = "data/test2.json";
        String file = Files.readString(Path.of(directory));
        JSONParser parser = new JSONParser();

        String content = new String(Files.readAllBytes(Paths.get(directory)));
//        System.out.println(content);

//        String content2 = "{ \"tickets\": [{ \"origin\": \"V\" }, { \"origin\": \"F\" }, { \"origin\": \"M\" }]}";

        JSONObject jsonObject = (JSONObject) parser.parse(content);

        jsonObject.keySet().forEach(object ->
        {
            JSONArray tickets = (JSONArray) jsonObject.get("tickets");
            tickets.forEach(object1 -> {
                try {
                    JSONObject jsonObjectFlight = (JSONObject) parser.parse(object1.toString());
                    Flight flight = new Flight((String) jsonObjectFlight.get("origin"));
                    flights.add(flight);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        System.out.println(flights);
    }
}