package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class CratesArrangement {
    public static void main(String[] args) throws FileNotFoundException {

        File input = new File("src/main/resources/2022/cratesarrangement.txt");
        Scanner scanner = new Scanner(input);
        Map<Integer, Deque<Character>> stackState = new HashMap<>();
        
        String result = "";
        //read initial state
        while(scanner.hasNextLine()){
            
            String stateLine = scanner.nextLine();
            if(stateLine.startsWith(" 1")) {
                scanner.nextLine(); //Blank line
                break;
            }
            
            System.out.println("Line: " + stateLine + ". Line size: " + stateLine.length());
            for(int i = 0; i < 9; i++){
                String ithStackItem = stateLine.substring(4*i, 4*i + 3);
                System.out.println("Raw: " + ithStackItem);
                if(!ithStackItem.contains("[")) continue;
                Character crateLetter = ithStackItem.replace("] ", "").replace("[", "").toCharArray()[0];
                if(!stackState.containsKey(i + 1)){
                    stackState.put(i+1, new ArrayDeque<>());
                }
                stackState.get(i+1).addFirst(crateLetter);
            }
        }
        System.out.println(stackState);

        while(scanner.hasNextLine()) {
            //read input and organize it
            String inputLine = scanner.nextLine();
            String treatedInputText = inputLine.substring(5).replace(" from ", ",").replace(" to ", ",");
            List<Integer> command = Arrays.stream(treatedInputText.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            Stack<Character> auxiliary = new Stack<>();
            for (int i = 0; i < command.get(0); i++) {
                Character crate = stackState.get(command.get(1)).removeLast();
                auxiliary.push(crate);
            }
            for(int i = 0; i < command.get(0); i++){
                stackState.get(command.get(2)).addLast(auxiliary.pop());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Integer key: stackState.keySet().stream().sorted().collect(Collectors.toList())){
            sb.append(stackState.get(key).peek());
        }
        System.out.println(sb);
    }

    public static Map<Integer, Stack<Character>> formInput(){
        /*
                        [G] [W]         [Q]
            [Z]         [Q] [M]     [J] [F]
            [V]         [V] [S] [F] [N] [R]
            [T]         [F] [C] [H] [F] [W] [P]
            [B] [L]     [L] [J] [C] [V] [D] [V]
            [J] [V] [F] [N] [T] [T] [C] [Z] [W]
            [G] [R] [Q] [H] [Q] [W] [Z] [G] [B]
            [R] [J] [S] [Z] [R] [S] [D] [L] [J]
             1   2   3   4   5   6   7   8   9
         */

        Map<Integer, Stack<Character>> result = new HashMap<>();
        addListToStackMap(1, Arrays.asList('R','G','J','B','T','V','Z'), result);
        addListToStackMap(2, Arrays.asList('J', 'R', 'V', 'L'), result);
        addListToStackMap(3, Arrays.asList('S', 'Q', 'F'), result);
        addListToStackMap(4, Arrays.asList('Z', 'H', 'N', 'L', 'F', 'V', 'Q', 'G'), result);
        addListToStackMap(5, Arrays.asList('R', 'Q', 'T', 'J', 'C', 'S', 'M', 'W'), result);
        addListToStackMap(6, Arrays.asList('S', 'W', 'T', 'C', 'H', 'F'), result);
        addListToStackMap(7, Arrays.asList('D', 'Z', 'C', 'V', 'F', 'N', 'J'), result);
        addListToStackMap(8, Arrays.asList('L', 'G', 'Z', 'D', 'W', 'R', 'F', 'Q'), result);
        addListToStackMap(9, Arrays.asList('J', 'B', 'W', 'V', 'P'), result);

        return result;
    }

    public static void addListToStackMap(int key, List<Character> charsToAdd, Map<Integer, Stack<Character>> target){
        Stack<Character> stackToAdd = new Stack<>();
        stackToAdd.addAll(charsToAdd);
        target.put(key, stackToAdd);
    }

    public static void buildInitialState(){

    }
}
