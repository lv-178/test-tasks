package com.example.springexample;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String directory = "data/tickets.json";
        ArrayList<Flight> flights = JsonToObject.readJson(directory);
        System.out.println("самые короткие перелёты каждого перевозчика");
        System.out.println(FindMinTime.toString(FindMinTime.find(flights)));
        System.out.println("разница между средней ценой и медианой для полёта между городами Владивосток и Тель-Авив");
        System.out.println(FindAverage.findDifference(flights));
    }
}