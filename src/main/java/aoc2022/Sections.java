package aoc2022;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Sections {

    public static void main(String[] args) throws FileNotFoundException {

        File input = new File("src/main/resources/2022/sections.txt");
        Scanner scanner = new Scanner(input);
        int result = 0;
        //read input and organize it
        while(scanner.hasNextLine()) {
            String[] assignmentPair = scanner.nextLine().split(",");
            List<Pair<Integer, Integer>> assignments = new ArrayList<>();
            for(String assignment : assignmentPair){
                Integer left = Integer.valueOf(assignment.split("-")[0]);
                Integer right = Integer.valueOf(assignment.split("-")[1]);
                assignments.add(new Pair<>(left, right));
            }

            if(checkRangesMatch(assignments)){
                result++;
            }
        }
        System.out.println(result);
    }

    public static boolean checkRangesMatch(List<Pair<Integer, Integer>> assignments){
        Collections.sort(assignments, Comparator.comparing(Pair::getKey));

        if(assignments.get(1).getKey() <= assignments.get(0).getValue()){
            return true;
        }
        return false;
    }
}
