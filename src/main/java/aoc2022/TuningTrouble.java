package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TuningTrouble {
    
    public static void main(String[] args) {
        int left = 0, right = 3;
        Set<Character> currentBlock = new HashSet<>();
        try {
            File input = new File("src/main/resources/2022/TuningTrouble.txt");
            Scanner scanner = new Scanner(input);
            //read input and organize it
            while (scanner.hasNextLine()) {
                String signal = scanner.nextLine();

                for (int i = 0; i <= right; i++) {
                    currentBlock.add(signal.charAt(i));
                }
                
                while (left <= signal.length() - 4){
                    if(validateCurrentBlock(currentBlock)){
                        System.out.println(String.format("End of processing. Left: %d, Right: %d. ", left, right) + currentBlock);
                        System.out.println("Result is " + right);
                        return;
                    }else {
                        currentBlock.remove(signal.charAt(left));
                        left++;
                        currentBlock.add(signal.charAt(right+1));
                        right++;
                    }
                }
            }
            System.out.println(String.format("End of processing. Left: %d, Right: %d. ", left, right) + currentBlock);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean validateCurrentBlock(Set<Character> currentBlock) {
        return currentBlock.size() == 4;
    }

}
