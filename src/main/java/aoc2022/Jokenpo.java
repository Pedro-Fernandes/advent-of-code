package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Jokenpo {

    private enum Choices {
        ROCK, PAPER, SCISSOR
    }

    public static void main(String[] args){

        List<String> plays = new ArrayList<>();
        Map<String, Integer> possibleScores = new HashMap<>();
        int totalScore = 0;
        try {
            File input = new File("src/main/resources/2022/jokenpo.txt");
            Scanner scanner = new Scanner(input);
            //read input and organize it
            while(scanner.hasNextLine()){
                String play = scanner.nextLine();
                String[] eachSidePlay = play.split(" ");
                addPlay(plays, eachSidePlay);
            }
            fillPossibleScores(possibleScores);

            //Calculate the result for the given input
            for(String play : plays){
                int playScore = possibleScores.get(play);
                totalScore += playScore;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(totalScore);
    }
    private static void addPlay(List<String> plays, String[] currentPlay){
        Choices player = null, enemyChoice = null;

        switch(currentPlay[0]){
            case "A": enemyChoice = Choices.ROCK; break;
            case "B": enemyChoice = Choices.PAPER; break;
            case "C": enemyChoice = Choices.SCISSOR; break;
        }

        if("Y".equals(currentPlay[1])){
            player = enemyChoice;
        }
        else if("X".equals(currentPlay[1])){
            switch(enemyChoice){
                case ROCK: player = Choices.SCISSOR; break;
                case PAPER: player = Choices.ROCK; break;
                case SCISSOR: player = Choices.PAPER; break;
            }
        }else if("Z".equals(currentPlay[1])){
            switch(enemyChoice){
                case ROCK: player = Choices.PAPER; break;
                case PAPER: player = Choices.SCISSOR; break;
                case SCISSOR: player = Choices.ROCK; break;
            }
        }

        plays.add(player.name()+enemyChoice.name());
    }

    private static void fillPossibleScores(Map<String, Integer> possibleScores){
        //victory
        possibleScores.put(Choices.ROCK.name() + Choices.SCISSOR.name(), Choices.ROCK.ordinal() + 1 + 6);
        possibleScores.put(Choices.SCISSOR.name() + Choices.PAPER.name(), Choices.SCISSOR.ordinal() + 1 + 6);
        possibleScores.put(Choices.PAPER.name() + Choices.ROCK.name(), Choices.PAPER.ordinal() + 1 + 6);
        //Tie
        possibleScores.put(Choices.ROCK.name() + Choices.ROCK.name(), Choices.ROCK.ordinal() + 1 + 3);
        possibleScores.put(Choices.SCISSOR.name() + Choices.SCISSOR.name(), Choices.SCISSOR.ordinal() + 1 + 3);
        possibleScores.put(Choices.PAPER.name() + Choices.PAPER.name(), Choices.PAPER.ordinal() + 1 + 3);
        //Defeat
        possibleScores.put(Choices.ROCK.name() + Choices.PAPER.name(), Choices.ROCK.ordinal() + 1);
        possibleScores.put(Choices.SCISSOR.name() + Choices.ROCK.name(), Choices.SCISSOR.ordinal() + 1);
        possibleScores.put(Choices.PAPER.name() + Choices.SCISSOR.name(), Choices.PAPER.ordinal() + 1);
    }
}
