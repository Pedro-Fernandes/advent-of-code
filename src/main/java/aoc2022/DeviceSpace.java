package aoc2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class DeviceSpace {
    private static Map<Node, Integer> foldersSize = new HashMap<>();
    
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
            System.out.println(getResult());
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Integer getResult() {
        int sum = 0;
        List<Integer> sizes = foldersSize.values().stream().filter(size -> size <= 100000).collect(Collectors.toList());
        for(Integer size : sizes){
            sum += size;
        }
        return sum;
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
                foldersSize.put(parent, foldersSize.getOrDefault(parent, 0) + Integer.parseInt(command.command));
                parent = parent.parent;
            }
     
        } else {
            currentNode.children.put(command.argument, new Node(command.argument, -1, currentNode));
        }
    }
}

class State {
    public String currentInputLine;
    public Node currentNode;
    
    public State(String currentInputLine, Node currentNode){
        this.currentInputLine = currentInputLine;
        this.currentNode = currentNode;
    }
}

class Node {
    public final String value;
    public final Integer size;
    public final Integer level;
    public final Map<String, Node> children;
    public final Node parent;
    
    public boolean isDir(){
        return size < 0;
    }
    
    public Node(String value, Integer size, Node parent){
        this.value = value;
        this.size = size;
        this.children = new HashMap<>();
        this.parent = parent;
        this.level = parent.level + 1;
    }

    public Node(String value, Integer size){
        this.value = value;
        this.size = size;
        this.children = new HashMap<>();
        this.parent = null;
        this.level = 0;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(Math.max(0, level)));
        sb.append("- ").append(value);
        if(!isDir()) sb.append(" ").append(size);
        return sb.toString();
    }
}

class Command {
    public String command;
    public String argument;
    public Command(String command, String argument){
        this.command = command;
        this.argument = argument;
    }
}
