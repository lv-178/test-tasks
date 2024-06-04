package com.example.springexample;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Flight {
    private String origin;
    private String originName;
    private String destination;
    private String destinationName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String carrier;
    private long stops;
    private long price;
}
