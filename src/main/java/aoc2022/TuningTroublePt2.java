package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TuningTroublePt2 {
    private static Integer WINDOW_SIZE = 14;
    
    public static void main(String[] args) {
        int left = 0, right = WINDOW_SIZE - 1;
        Deque<Character> currentBlock = new ArrayDeque<>();
        
        try {
            File input = new File("src/main/resources/2022/TuningTrouble.txt");
            Scanner scanner = new Scanner(input);
            //read input and organize it
            while (scanner.hasNextLine()) {
                String signal = scanner.nextLine();

                for (int i = 0; i <= right; i++) {
                    currentBlock.add(signal.charAt(i));
                }

                while (right < signal.length() - 2){
                    if(validateCurrentBlock(currentBlock)){
                        System.out.println(String.format("End of processing. Left: %d, Right: %d. ", left, right) + currentBlock);
                        System.out.println("Result is " + (right+1));
                        break;
                    }else {
                        currentBlock.removeFirst();
                        left++;
                        right++;
                        currentBlock.add(signal.charAt(right));
                    }
                }
                currentBlock =  new ArrayDeque<>(); left = 0; right = WINDOW_SIZE - 1;
            }
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean validateCurrentBlock(Deque<Character> currentBlock) {
        Set<Character> currentItems = new HashSet<>();
        for(Character item : currentBlock){
            if(!currentItems.add(item)){
                return false;
            }
        }
        return true;
    }
}
