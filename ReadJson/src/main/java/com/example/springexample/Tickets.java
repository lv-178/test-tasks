package com.example.springexample;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Tickets {
    private ArrayList<Flight> flights;
}
