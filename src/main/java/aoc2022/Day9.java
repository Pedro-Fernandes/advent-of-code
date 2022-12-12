package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Day9 {
    private static List<Day9Command> commands = new ArrayList<>();
    private static Set<String> uniqueTailPositions = new HashSet<>();
    List<List<Character>> movementMatrix;
    
    public static void main(String[] args) {
        try {
            File input = new File("src/main/resources/2022/Day9.txt");
            Scanner scanner = new Scanner(input);
            uniqueTailPositions.add("0,0");
            
            //read input and organize it
            while (scanner.hasNextLine()) {
                commands.add(new Day9Command(scanner.nextLine()));
            }
            List<Day9Node> rope = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                rope.add(new Day9Node(0, 0, i));
            }
            Day9Node head = rope.get(0);
            Day9Node tail = rope.get(9);
            int startLine = 0, startColumn = 0;
            for (int i = 0; i < commands.size(); i++) {
                System.out.printf("**Processing command: %s\n", commands.get(i));
                processCommand(commands.get(i), rope, head, tail);
                //printMatrix(rope);
                Runtime.getRuntime().exec("clear");
                System.out.println("");
            }
            System.out.println(uniqueTailPositions.size());
            System.out.println(uniqueTailPositions);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void processTailMovement(Day9Node head, Day9Node tail){
        //covers
        if(head.x == tail.x && head.y == tail.y) return;
        
        //diagonals
        if(head.x != tail.x && head.y != tail.y){

            if(head.y > tail.y + 1 && head.x > tail.x + 1){
                tail.x++;tail.y++;
            }else if(head.y < tail.y - 1 && head.x < tail.x - 1){
                tail.x--;tail.y--;
            } else if(head.y < tail.y - 1 && head.x > tail.x + 1){
                tail.y--;tail.x++;
            }else if(head.y > tail.y + 1 && head.x < tail.x - 1){
                tail.y++;tail.x--;
            }else {
                if(head.y > tail.y + 1){
                    System.out.printf("Node %s is %d,%d while tail is %d,%d. Moving %s \n", head.name, head.x, head.y, tail.x, tail.y, tail.name);
                    tail.x = head.x; tail.y++;
                }else if(head.y < tail.y - 1){
                    System.out.printf("Node %s is %d,%d while tail is %d,%d. Moving %s \n", head.name, head.x, head.y, tail.x, tail.y, tail.name);
                    tail.x = head.x; tail.y--;
                }
                if(head.x > tail.x + 1){
                    System.out.printf("Node %s is %d,%d while tail is %d,%d. Moving %s \n", head.name, head.x, head.y, tail.x, tail.y, tail.name);
                    tail.x++; tail.y = head.y;
                } else if(head.x < tail.x - 1){
                    System.out.printf("Node %s is %d,%d while tail is %d,%d. Moving %s \n", head.name, head.x, head.y, tail.x, tail.y, tail.name);
                    tail.x--; tail.y = head.y;
                }
            }
            
            return;
        }
        //side movement
        if(head.x > tail.x + 1 && head.y == tail.y){
            System.out.printf("%s.x is %d while %s.x is %d. Moving tail down \n", head.name, head.x, tail.name, tail.x);
            tail.x++;
        }
        else if(head.x == tail.x && head.y < tail.y - 1) {
            System.out.printf("%s.y is %d while %s.y is %d. Moving tail left \n", head.name, head.y, tail.name, tail.y);
            tail.y--;
        }
        else if(head.x < tail.x - 1 && head.y == tail.y){
            System.out.printf("%s.x is %d while %s.x is %d. Moving tail up \n", head.name, head.x, tail.name, tail.x);
            tail.x--;
        }
        else if(head.x == tail.x && head.y > tail.y + 1){
            System.out.printf("%s.y is %d while %s.y is %d. Moving tail right \n", head.name, head.y, tail.name, tail.y);
            tail.y++;
        }
    }
    
    private static void processCommand(Day9Command command, List<Day9Node> rope, Day9Node head, Day9Node tail){
        for (int i = 0; i < command.steps; i++) {
            switch (command.direction) {
                case "U": {
                    head.x = head.x - 1;
                    break;
                }
                case "L": {
                    head.y = head.y - 1;
                    break;
                }
                case "R": {
                    head.y = head.y + 1;
                    break;
                }
                case "D": {
                    head.x = head.x + 1;
                    break;
                }
            }
            System.out.printf("Node %s new position: %d,%d\n", head.name, head.x, head.y);

            for (int j = 0; j <= rope.size() - 2; j++) {
                processTailMovement(rope.get(j), rope.get(j+1)); 
            }
            
            boolean addresult = uniqueTailPositions.add(String.format("%d,%d", tail.x, tail.y));
            if(!addresult) System.out.printf("Position %d,%d already in map\n", tail.x, tail.y);
            
        }
    }
    
    private static void printMatrix(List<Day9Node> rope){
        String[][] matrix = new String[30][30];
        for(Day9Node node : rope){
            matrix[node.x + 15][node.y + 15] = node.name;
        }
        for(String position : uniqueTailPositions){
            String[] positions = position.split(",");
            matrix[Integer.parseInt(positions[0]) + 15][Integer.parseInt(positions[1]) + 15] = "#";
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == null) matrix[i][j] = ".";
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print("" + matrix[i][j] + "");
            }
            System.out.print("\n");
        }
    }
    
}

class Day9Node {
    int x;
    int y;
    String name;
    public Day9Node(int x, int y, int ropePosition){
        this.x = x;
        this.y = y;
        if(ropePosition == 0) name = "H";
        else if (ropePosition == 9) name = "T";
        else name = String.valueOf(ropePosition);
    }

    @Override
    public String toString() {
        return "Current position: " + x + "," + y;
    }
}
class Day9Command {
    String direction;
    Integer steps;
    
    public Day9Command(String input){
        String[] inputList = input.split(" ");
        direction = inputList[0];
        steps = Integer.parseInt(inputList[1]);
    }
    @Override
    public String toString(){
        return String.format("%d steps in direction %s", steps, direction);
    }
}
