package org.unisafe.pj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UniSafeTestAssignment {

    public static final double kMulX = 7.0843;
    public static final double kMulY = 7.0855;

//     1: добавить первый элемент фигуры последним к каждой фигуре
//     2: изменить порядок реза фигур от самой маленькой к самой большой
//     3: развернуть все фигуры по часовой меняя координаты местами
//     4: изменить начало реза каждой фигуры в направлении окончания реза предыдущей

    public static void main(String[] args) {

        String filePath = "data/debug_block.eps";

        List<List<List<Integer>>> listOfFigures = getFromEps(filePath);
        System.out.println("исходные данные");
        ShowList(listOfFigures);
        System.out.println("повторение первого элемента");
        listOfFigures = AddFirstElement(listOfFigures);
        ShowList(listOfFigures);
        System.out.println("сортировка по размеру");
        listOfFigures = Sort(listOfFigures);
        ShowList(listOfFigures);
        System.out.println("изменение направления обхода всех фигур по часовой стрелке");
        listOfFigures = ChangeToClockwise(listOfFigures);
        ShowList(listOfFigures);
        System.out.println("изменение начала резки фигур");
        listOfFigures = ChangeDirection(listOfFigures);
        ShowList(listOfFigures);
    }

    public static void ShowList (List<List<List<Integer>>> listOfFigures) {
        for (List<List<Integer>> listOfFigure : listOfFigures) {
            System.out.println(listOfFigure);
        }
    }

    public static List<List<List<Integer>>> AddFirstElement (List<List<List<Integer>>> listOfFigures) {
        for (List<List<Integer>> figure : listOfFigures) {
            figure.add(figure.get(1));
        }
        return listOfFigures;
    }

    public static List<List<List<Integer>>> Sort (List<List<List<Integer>>> listOfFigures) {
        Collections.sort(listOfFigures, new Comparator<List<List<Integer>>>() {
            @Override
            public int compare(List<List<Integer>> p1, List<List<Integer>> p2) {
                int p1maxX = p1.stream().flatMap(List::stream).filter(i -> i % 2 == 1).collect(Collectors.summarizingInt(Integer::intValue)).getMax();
                int p1minX = p1.stream().flatMap(List::stream).filter(i -> i % 2 == 1).collect(Collectors.summarizingInt(Integer::intValue)).getMin();
                int p1maxY = p1.stream().flatMap(List::stream).filter(i -> i % 2 == 0).collect(Collectors.summarizingInt(Integer::intValue)).getMax();
                int p1minY = p1.stream().flatMap(List::stream).filter(i -> i % 2 == 0).collect(Collectors.summarizingInt(Integer::intValue)).getMin();

                int p2maxX = p2.stream().flatMap(List::stream).filter(i -> i % 2 == 1).collect(Collectors.summarizingInt(Integer::intValue)).getMax();
                int p2minX = p2.stream().flatMap(List::stream).filter(i -> i % 2 == 1).collect(Collectors.summarizingInt(Integer::intValue)).getMin();
                int p2maxY = p2.stream().flatMap(List::stream).filter(i -> i % 2 == 0).collect(Collectors.summarizingInt(Integer::intValue)).getMax();
                int p2minY = p2.stream().flatMap(List::stream).filter(i -> i % 2 == 0).collect(Collectors.summarizingInt(Integer::intValue)).getMin();

                return (p1maxX - p1minX + p1maxY - p1minY) - (p2maxX - p2minX + p2maxY - p2minY);
            }
        });
        return listOfFigures;
    }

    // только после повторения последнего отрезка
    public static List<List<List<Integer>>> ChangeToClockwise (List<List<List<Integer>>> listOfFigures) {
        for (List<List<Integer>> figure : listOfFigures) {
            int minX = figure.getFirst().getFirst();
            int minIndex = 0;
            int flag = 0;
            for (List<Integer> line : figure) {
                if(line.getFirst() < minX) {
                    minX = line.getFirst();
                    minIndex = figure.indexOf(line);
                    flag = 0;
                }
                if(line.size() > 2) {
                    if(line.get(2) < minX) {
                        minX = line.get(2);
                        minIndex = figure.indexOf(line);
                        flag = 2;
                    }
                    if(line.get(4) < minX) {
                        minX = line.get(4);
                        minIndex = figure.indexOf(line);
                        flag = 4;
                    }
                }
            }
            int minY = 0;
            int prevX = 0;
            int prevY = 0;
            int nextX = 0;
            int nextY = 0;
            if(flag == 0) {
                minY = figure.get(minIndex).get(1);
                int prevIndex = minIndex - 1;
                if(minIndex == 0) {
                    prevIndex = figure.size() - 3;
                }
                if(figure.get(minIndex).size() == 2) {
                    nextX = figure.get(minIndex + 1).get(0);
                    nextY = figure.get(minIndex + 1).get(1);
                } else {
                    nextX = figure.get(minIndex).get(2);
                    nextY = figure.get(minIndex).get(3);
                }
                if(figure.get(prevIndex).size() == 2) {
                    prevX = figure.get(prevIndex).get(0);
                    prevY = figure.get(prevIndex).get(1);
                } else {
                    prevX = figure.get(prevIndex).get(4);
                    prevY = figure.get(prevIndex).get(5);

                }
            } else if(flag == 2) {
                prevX = figure.get(minIndex).get(0);
                prevY = figure.get(minIndex).get(1);
                nextX = figure.get(minIndex).get(4);
                nextY = figure.get(minIndex).get(5);
            } else {
                prevX = figure.get(minIndex).get(2);
                prevY = figure.get(minIndex).get(3);
                nextX = figure.get(minIndex + 1).get(0);
                nextY = figure.get(minIndex + 1).get(1);
            }

            if(((prevX - minX) * (nextY - minY) - (prevY - minY) * (nextX - minX)) > 0) {
                Collections.reverse(figure);
            }
        }
        return listOfFigures;
    }

    public static List<List<List<Integer>>> ChangeDirection (List<List<List<Integer>>> listOfFigures) {
        double prevDeltaX;
        double prevDeltaY;
        double newDeltaX;
        double newDeltaY;
        double prevDirection = 0;
        for (int i = 1; i < listOfFigures.size(); i++) {
            if(listOfFigures.get(i - 1).getLast().size() == 2) {
                prevDeltaX = listOfFigures.get(i - 1).getLast().get(0) - listOfFigures.get(i - 1).getFirst().get(0);
                prevDeltaY = listOfFigures.get(i - 1).getLast().get(1) - listOfFigures.get(i - 1).getFirst().get(1);
            } else {
                prevDeltaX = listOfFigures.get(i - 1).getLast().get(4) - listOfFigures.get(i - 1).getLast().get(2);
                prevDeltaY = listOfFigures.get(i - 1).getLast().get(5) - listOfFigures.get(i - 1).getLast().get(3);
            }
            prevDirection = Math.atan2(prevDeltaY, prevDeltaX);
            double bestDirection = Math.atan2(listOfFigures.get(i).get(1).get(1) - listOfFigures.get(i).get(0).getLast(), listOfFigures.get(i).get(1).get(0) - listOfFigures.get(i).get(0).get(listOfFigures.get(i).get(0).size() - 2));
            int bestLineIndex = 1;
            for (int j = 2; j < listOfFigures.get(i).size(); j++) {
                newDeltaX = listOfFigures.get(i).get(j).get(0) - listOfFigures.get(i).get(j - 1).get(listOfFigures.get(i).get(j - 1).size() - 2);
                newDeltaY = listOfFigures.get(i).get(j).get(1) - listOfFigures.get(i).get(j - 1).getLast();
                double bestDifference = Math.abs(bestDirection - prevDirection) > Math.PI ? 2 * Math.PI - Math.abs(bestDirection - prevDirection) : Math.abs(bestDirection - prevDirection);
                double newDifference = Math.abs(Math.atan2(newDeltaY, newDeltaX) - prevDirection) > Math.PI ? 2 * Math.PI - Math.abs(Math.atan2(newDeltaY, newDeltaX) - prevDirection) : Math.abs(Math.atan2(newDeltaY, newDeltaX) - prevDirection);
                if(newDifference < bestDifference) {
                    bestDirection = Math.atan2(newDeltaY, newDeltaX);
                    bestLineIndex = j;
                }
            }
            listOfFigures.get(i).removeLast();
            listOfFigures.get(i).removeLast();
            Collections.rotate(listOfFigures.get(i), -(bestLineIndex - 1));
            listOfFigures.get(i).add(listOfFigures.get(i).get(0));
            listOfFigures.get(i).add(listOfFigures.get(i).get(1));
        }
        return listOfFigures;
    }

    public static List<List<List<Integer>>> getFromEps(String filePath){
        List<List<List<Integer>>> listOfFigures = new ArrayList<>();

        List<String> blocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean reachedEndData = false;
            boolean reachedBeginData = false;
            boolean blockStarted = false;

            while ((line = reader.readLine()) != null) {
                if (!reachedBeginData) {
                    if (line.trim().startsWith("%%EndPageSetup")) {
                        reachedBeginData = true;
                    }
                } else if (!reachedEndData) {

                    if (line.startsWith("%ADO")) {
                        reachedEndData = true;
                    } else {
                        if(line.contains("mo") && Character.isDigit(line.charAt(0))){
                            listOfFigures.add(new ArrayList<>());
                            blockStarted = true;
                            blocks.add(line);
                        } else if (line.contains("m") && Character.isDigit(line.charAt(0))) {
                            listOfFigures.add(new ArrayList<>());
                            blockStarted = true;
                            blocks.add(line);
                        } else if (line.trim().equals("cp") || line.trim().equals("@c") || line.trim().equals("@")) {
                            blockStarted = false;
                        } else if (blockStarted) {
                            blocks.add(line);
                        }
                    }
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading EPS file: " + e.getMessage());
            return new ArrayList<>();
        }

        int current_figure = -1;
        for (String block : blocks) {
            String[] line_parts = block.split(" ");

            if (Objects.equals(line_parts[line_parts.length - 1], "mo") || Objects.equals(line_parts[line_parts.length - 1], "m")) {
                List<Integer> listN = new ArrayList<>();
                current_figure++;
                getNumericalWithDot(current_figure, listOfFigures, line_parts, listN);
            } else if (Objects.equals(line_parts[line_parts.length - 1], "li")) {
                List<Integer> listN = new ArrayList<>();
                getNumericalWithDot(current_figure, listOfFigures, line_parts, listN);
            } else if (Objects.equals(line_parts[line_parts.length - 1], "cv") || Objects.equals(line_parts[line_parts.length - 1], "C")) {
                List<Integer> listN = new ArrayList<>();
                getNumericalWithDot(current_figure, listOfFigures, line_parts, listN);
            }
        }

        removeEmptyLists(listOfFigures);
        removeNotCycledFigures(listOfFigures);

        return listOfFigures;
    }

    public static void removeEmptyLists(List<List<List<Integer>>> listOfFigures) {
        listOfFigures.removeIf(List::isEmpty);
    }
    public static void removeNotCycledFigures(List<List<List<Integer>>> listOfFigures) {
        Iterator<List<List<Integer>>> iterator = listOfFigures.iterator();
        while (iterator.hasNext()) {
            List<List<Integer>> listOfFigure = iterator.next();
            int last_x = listOfFigure.get(listOfFigure.size() - 1).get(listOfFigure.get(listOfFigure.size() - 1).size() - 2);
            int last_y = listOfFigure.get(listOfFigure.size() - 1).get(listOfFigure.get(listOfFigure.size() - 1).size() - 1);
            int first_x = listOfFigure.get(0).get(0);
            int first_y = listOfFigure.get(0).get(1);
            if (first_x != last_x || first_y != last_y) {
                iterator.remove();
            }
        }
    }

    private static void getNumericalWithDot(int current_figure, List<List<List<Integer>>> listOfFigures, String[] line_parts, List<Integer> listN) {
        for (int j = 0; j < line_parts.length - 1; j++) {
            if (line_parts[j].startsWith(".")) {
                line_parts[j] = "0" + line_parts[j];
            }
            double calk;
            if (j % 2 != 0) {
                calk = (Double.parseDouble(line_parts[j]) + 1.5) * kMulX;
            } else {
                calk = (Double.parseDouble(line_parts[j]) + 1.5) * kMulY;
            }
            int this_int = (int) Math.round(calk);
            listN.add(this_int);
        }
        listOfFigures.get(current_figure).add(listN);
    }

}