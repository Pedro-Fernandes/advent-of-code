package aoc2022;

import java.io.*;
import java.util.*;

public class MaxCalories {

    public static void main(String[] args){

        File caloriesInput = new File("src/main/resources/2022/calories.txt");
        try {
            Scanner reader = new Scanner(caloriesInput);
            int elfNumber = 1;
            Map<Integer, Integer> caloriesPerElf = new HashMap<>();

            while(reader.hasNextLine()){
                String currentLine = reader.nextLine();
                if(currentLine.equals("")){
                    elfNumber++;
                } else {
                    caloriesPerElf.put(elfNumber, caloriesPerElf.getOrDefault(elfNumber, 0) + Integer.parseInt(currentLine));
                }
            }


           Integer[] caloriesAsList= caloriesPerElf.values().toArray(new Integer[caloriesPerElf.values().size()]);
           Arrays.sort(caloriesAsList, Collections.reverseOrder());
           int result = 0;
            for (int i = 0; i < 3; i++) {
                result += caloriesAsList[i];
            }
            System.out.println(result);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
