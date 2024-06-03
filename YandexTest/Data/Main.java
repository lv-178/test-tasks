package com.example.springexample;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String command = scanner.nextLine();
        int numberOfLines = scanner.nextInt();
        int[] coordinatesA = new int[numberOfLines];
        int[] coordinatesB = new int[numberOfLines];
        String l = scanner.nextLine();
        for (int i = 0; i < numberOfLines; i++) {
            String newCoordinates = scanner.nextLine();
            String[] parts = newCoordinates.split(" ");
            coordinatesA[i] = parseInt(parts[0]);
            coordinatesB[i] = parseInt(parts[1]);
        }
        int numberOfSeparatedLines = 0;
        for (int i = 0; i < numberOfLines; i++) {
            boolean isSeparated = true;
            int j = 0;
            while (j < numberOfLines & isSeparated) {
                if(j != i) {
                    if((coordinatesA[i] - coordinatesA[j]) * (coordinatesB[i] - coordinatesB[j]) <= 0) {
                        isSeparated = false;
                    }
                }
                j++;
            }
            if(isSeparated) {
                numberOfSeparatedLines++;
            }
        }
        System.out.println(numberOfSeparatedLines);
    }
}