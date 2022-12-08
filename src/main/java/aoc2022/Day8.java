package aoc2022;
//Problem link: https://adventofcode.com/2022/day/8

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 {

    private static Map<Integer, List<String>> visibleTop = new HashMap<>();
    private static Map<Integer, List<String>> visibleLeft = new HashMap<>();
    private static Map<Integer, List<String>> visibleRight = new HashMap<>();
    private static Map<Integer, List<String>> visibleBottom = new HashMap<>();
    private static long[][] scenicValue;
    
    public static void main(String[] args) {
        try {
            File input = new File("src/main/resources/2022/Day8.txt");
            Scanner scanner = new Scanner(input);
            List<String> inputLines = new ArrayList<>();
            
            
            //read input and organize it
            while( scanner.hasNextLine()){
                inputLines.add(scanner.nextLine());
            }
            int rows = inputLines.size();
            int columns = inputLines.get(0).length();
            int[][] inputMatrix = new int[rows][columns];
            scenicValue = new long[rows][columns];
            long bestScenicvalue = 0l;
            String bestScenicNode = "";
            
            for (int i = 0; i < rows; i++) {
                char[] currentLine = inputLines.get(i).toCharArray();
                for (int j = 0; j < columns; j++){
                    int value = Character.getNumericValue(currentLine[j]);
                    inputMatrix[i][j] = value;
                }
            }
            printMatrix(inputMatrix);
            //visible from top
            for (int i = 0; i < inputMatrix.length; i++) {
                for (int j = 0; j < inputMatrix[0].length; j++) {
                    checkNodeVisible("top", i, j, inputMatrix);
                    checkNodeVisible("bottom", i, j, inputMatrix);
                    checkNodeVisible("left", i, j, inputMatrix);
                    checkNodeVisible("right", i, j, inputMatrix);

                    long scenicValueLeft = checkScenicValue("left", i, j, inputMatrix);
                    long scenicValueRight = checkScenicValue("right", i, j, inputMatrix);
                    long scenicValueTop = checkScenicValue("top", i, j, inputMatrix);
                    long scenicValueDown = checkScenicValue("down", i, j, inputMatrix);
                    
                    scenicValue[i][j] = scenicValueDown * scenicValueLeft * scenicValueTop * scenicValueRight;
                    if(bestScenicvalue < scenicValue[i][j]){
                        bestScenicvalue = scenicValue[i][j];
                        bestScenicNode = String.format("%d,%d",i,j);
                    }
                    bestScenicvalue = Math.max(bestScenicvalue, scenicValue[i][j]);
                }
            }
            Set<String> uniqueResults = new HashSet<>();
            visibleRight.values().forEach(uniqueResults::addAll);
            visibleBottom.values().forEach(uniqueResults::addAll);
            visibleLeft.values().forEach(uniqueResults::addAll);
            visibleTop.values().forEach(uniqueResults::addAll);
            
            System.out.println(uniqueResults.size());
            System.out.println(String.format("Best scenic node is %s with value %d", bestScenicNode, bestScenicvalue));
            
            

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int checkScenicValue(String direction, int i, int j, int[][] inputMatrix){
        int visibleTrees = 0;
        switch (direction){
            case "top": {
                if(i == 0) return 0;
                int k = i - 1;
                while(k >= 0 && (inputMatrix[k][j] < inputMatrix[i][j])){
                    visibleTrees++;
                    k--;
                }
                if(k > -1) visibleTrees++;
                break;
            }
            case "right": {
                if(j == inputMatrix[0].length - 1) return 0;
                int k = j + 1;
                while(k < inputMatrix[0].length && (inputMatrix[i][k] < inputMatrix[i][j])){
                    visibleTrees++;
                    k++;
                }
                if(k < inputMatrix[0].length) visibleTrees++;
                break;
            }
            case "left": {
                if(j == 0) return 0;
                int k = j - 1;
                while(k >= 0 && (inputMatrix[i][k] < inputMatrix[i][j])){
                    visibleTrees++;
                    k--;
                }
                if(k > -1) visibleTrees++;
                break;
            }
            case "down": {
                if(i == inputMatrix.length - 1) return 0;
                int k = i + 1;
                while(k < inputMatrix.length && (inputMatrix[k][j] < inputMatrix[i][j])){
                    visibleTrees++;
                    k++;
                }
                if(k < inputMatrix.length) visibleTrees++;
                break;
            }

        }
        System.out.printf("Node %d,%d looking %s has value %d%n", i,j,direction,visibleTrees);
        return visibleTrees;
    }
    
    private static void checkNodeVisible(String direction, int i, int j, int[][] inputMatrix) {
        if("top".equals(direction)){
            
            if(!visibleTop.containsKey(j)) visibleTop.put(j, new ArrayList<>());
            int currentlyVisible = inputMatrix[0][j];
            if(i == 0){
                visibleTop.get(j).add(String.format("%d,%d",i,j));
            }
            for(int k = 0; k <= i; k++){
                if(inputMatrix[k][j] > currentlyVisible){
                    currentlyVisible = inputMatrix[k][j];
                    visibleTop.get(j).add(String.format("%d,%d",k,j));
                }
            }
        } else if ("right".equals(direction)) {
            if(!visibleRight.containsKey(i)) visibleRight.put(i, new ArrayList<>());
            int currentlyVisible = inputMatrix[i][inputMatrix[0].length - 1];
            if(j == inputMatrix[0].length - 1){
                visibleRight.get(i).add(String.format("%d,%d",i,j));
            }
            for(int k = inputMatrix[0].length - 1; k >= j; k--){
                if(inputMatrix[i][k] > currentlyVisible){
                    currentlyVisible = inputMatrix[i][k];
                    visibleRight.get(i).add(String.format("%d,%d",i,k));
                }
            }
        } else if ("bottom".equals(direction)) {
            if(!visibleBottom.containsKey(j)) visibleBottom.put(j, new ArrayList<>());
            int currentlyVisible = inputMatrix[inputMatrix.length - 1][j];
            if(i == inputMatrix.length - 1){
                visibleBottom.get(j).add(String.format("%d,%d",i,j));
            }
            for(int k = inputMatrix.length - 1; k >= i; k--){
                if(inputMatrix[k][j] > currentlyVisible){
                    currentlyVisible = inputMatrix[k][j];
                    visibleBottom.get(i).add(String.format("%d,%d",k,j));
                }
            }
        } else if ("left".equals(direction)) {
            if(!visibleLeft.containsKey(i)) visibleLeft.put(i, new ArrayList<>());
            int currentlyVisible = inputMatrix[i][0];
            if(j == 0){
                visibleLeft.get(i).add(String.format("%d,%d",i,j));
            }
            for(int k = 0; k < inputMatrix[0].length; k++){
                if(inputMatrix[i][k] > currentlyVisible){
                    currentlyVisible = inputMatrix[i][k];
                    visibleLeft.get(i).add(String.format("%d,%d",i,k));
                }
            }
        }
    }


    public static void printMatrix(int[][] input){
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                System.out.print(input[i][j]);
            }
            System.out.println();
        }
    }

    public static void printMatrix(long[][] input){
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                System.out.print(input[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
