package com.example.springexample;

import java.util.Scanner;
import java.util.TreeSet;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int commandLength = parseInt(scanner.nextLine());
        String command = scanner.nextLine();
        TreeSet<Integer> possiblePlaces = new TreeSet<>();
        for (int i = 0; i < commandLength; i++) {
            StringBuilder newCommand = new StringBuilder(command);
            newCommand = change(newCommand, i);
            possiblePlaces.add(possiblePlace(newCommand));
            newCommand = change(newCommand, i);
            possiblePlaces.add(possiblePlace(newCommand));
        }
        System.out.println(possiblePlaces.size());
    }

    public static StringBuilder change(StringBuilder newCommand, int index){
        switch(newCommand.charAt(index)) {
            case 'F' -> newCommand.setCharAt(index, 'R');
            case 'R' -> newCommand.setCharAt(index, 'L');
            case 'L' -> newCommand.setCharAt(index, 'F');
        }
        return newCommand;
    }

    public static int possiblePlace(StringBuilder newCommand){
        int step = 1; // 1: turned right, -1: left
        int place = 0;
        for (int i = 0; i < newCommand.length(); i++) {
            switch(newCommand.charAt(i)) {
                case 'F' -> place += step;
                case 'R' -> step = 1;
                case 'L' -> step = -1;
            }
        }
        return place;
    }
}