package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DeviceSpacePt2 {
    private static Map<Node, Long> foldersSize = new HashMap<>();
    private static Long MAX_SPACE = 70000000L;
    private static Long UPDATE_SIZE = 30000000L;

    public static void main(String[] args) {
        Node root = new Node("/", -1);

        try {
            File input = new File("src/main/resources/2022/deviceSpace.txt");
            Scanner scanner = new Scanner(input);
            String nextInputLine = scanner.nextLine();
            Node currentNode = root;
            //read input and organize it
            while (nextInputLine != null) {
                Command currentCommand = parseCommand(nextInputLine);
                State currentState = processCommand(root, currentNode, currentCommand, scanner);
                currentNode = currentState.currentNode;
                nextInputLine = currentState.currentInputLine;
            }
            printFileSystem(root);
            System.out.println(getResult(root));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Long getResult(Node root) {
        System.out.println(foldersSize);
        
        Long remainingSpace = MAX_SPACE - foldersSize.get(root);
        Long requiredSpace = UPDATE_SIZE - remainingSpace;
        System.out.println("required space: " + requiredSpace);
        List<Node> possibleNodes = new ArrayList<>();
        Node targetNode = root;
        Long candidateSize = foldersSize.get(root);
        if(requiredSpace > 0) {
            for (Node node : foldersSize.keySet()) {
                if (foldersSize.get(node) >= requiredSpace && candidateSize >= foldersSize.get(node)){
                    System.out.println("Candidate " + node);
                    candidateSize = foldersSize.get(node);
                }
            }
        }
        return candidateSize;
    }

    public static void printFileSystem(Node node){
        System.out.println(node);
        for(Node child : node.children.values()){
            printFileSystem(child);
        }
    }
    public static Command parseCommand(String line){
        Command command = null;
        if(line.startsWith("$")){
            String[] commandComponents = line.split(" ");
            command = new Command(commandComponents[1], commandComponents.length > 2 ? commandComponents[2] : null);
        }
        return command;
    }

    public static State processCommand(Node root, Node currentNode, Command command, Scanner scanner){
        String currentInputLine = scanner.nextLine();
        if("ls".equals(command.command)){
            while (!currentInputLine.startsWith("$")) {
                Command itemToCreate = parseListResult(currentInputLine);
                processListResult(currentNode, itemToCreate);
                if(!scanner.hasNextLine()) {
                    currentInputLine = null;
                    break;
                }
                else {
                    currentInputLine = scanner.nextLine();
                }
            }
        }
        if("cd".equals(command.command)){
            if("/".equals(command.argument)) currentNode = root;
            if("..".equals(command.argument)) currentNode = currentNode.parent;
            if(currentNode.children.containsKey(command.argument)){
                currentNode = currentNode.children.get(command.argument);
            }
        }
        return new State(currentInputLine, currentNode);
    }

    public static Command parseListResult(String line){
        String[] splitLine = line.split(" ");
        return new Command(splitLine[0], splitLine[1]);
    }

    public static void processListResult(Node currentNode, Command command){
        if(!"dir".equals(command.command)){
            currentNode.children.put(command.argument, new Node(command.argument, Integer.valueOf(command.command), currentNode));
            Node parent = currentNode;
            while(parent != null){
                foldersSize.put(parent, foldersSize.getOrDefault(parent, 0L) + Integer.parseInt(command.command));
                parent = parent.parent;
            }

        } else {
            currentNode.children.put(command.argument, new Node(command.argument, -1, currentNode));
        }
    }
}


