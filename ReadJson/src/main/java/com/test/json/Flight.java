package com.test.json;

import lombok.Data;

import java.time.LocalDateTime;

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
