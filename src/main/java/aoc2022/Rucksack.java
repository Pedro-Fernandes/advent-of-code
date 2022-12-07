package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Rucksack {
    public static void main(String[] args){

        Map<Character, Integer> itemPriorityMap = new HashMap<>();
        fillPriorityMap(itemPriorityMap);
        Integer badgesSum = 0;

        try {
            File input = new File("src/main/resources/2022/rucksacks.txt");
            Scanner scanner = new Scanner(input);
            //read input and organize it
            while(scanner.hasNextLine()){
                List<String> teamRucksacks = new ArrayList<>();
                Map<Character, Set<Integer>> seenItems = new HashMap<>();
                Character teamBadge = '_';
                for (int i = 0; i < 3; i++) {
                    teamRucksacks.add(scanner.nextLine());
                }
                System.out.println(teamRucksacks);
                Integer rucksack = 1;
                for(String ruckSack : teamRucksacks){
                    for(char item : ruckSack.toCharArray()){
                        seenItems.computeIfAbsent(item, k -> new HashSet<>());
                        seenItems.get(item).add(rucksack);

                        if(seenItems.get(item).size() == 3){
                            teamBadge = item;
                            break;
                        }
                    }
                    rucksack++;
                }
                System.out.println(seenItems);
                System.out.println("Found badge: " + teamBadge);
                badgesSum += itemPriorityMap.get(teamBadge);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(badgesSum);
    }

    public static void fillPriorityMap(Map<Character, Integer> itemPriorityMap){
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";

        for (char letter : lowercaseLetters.toCharArray()){
            itemPriorityMap.put(letter, Character.getNumericValue(letter) - 9);
            itemPriorityMap.put(Character.toUpperCase(letter),Character.getNumericValue(Character.toUpperCase(letter)) + 17);
        }
    }
}
