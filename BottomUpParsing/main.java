package BottomUpParsing;

import java.util.*;

class SLRParser {
    private final List<String> grammar;
    private final Map<Integer, Map<String, String>> actionTable;
    private final Map<Integer, Map<String, Integer>> goToTable;
    private final Stack<Integer> stateStack;
    private final Stack<String> symbolStack;
    private final List<String> input;

    public SLRParser(List<String> grammar, Map<Integer, Map<String, String>> actionTable,
            Map<Integer, Map<String, Integer>> goToTable, List<String> input) {
        this.grammar = grammar;
        this.actionTable = actionTable;
        this.goToTable = goToTable;
        this.input = input;
        this.stateStack = new Stack<>();
        this.symbolStack = new Stack<>();
    }

    public boolean parse() {
        stateStack.push(0);
        int inputIndex = 0;
        while (inputIndex < input.size()) {
            int currentState = stateStack.peek();
            String currentSymbol = input.get(inputIndex);
            if (actionTable.containsKey(currentState) && actionTable.get(currentState).containsKey(currentSymbol)) {
                String action = actionTable.get(currentState).get(currentSymbol);
                if (action.startsWith("s")) {
                    int nextState = Integer.parseInt(action.substring(1));
                    stateStack.push(nextState);
                    symbolStack.push(currentSymbol);
                    inputIndex++;
                } else if (action.startsWith("r")) {
                    int productionIndex = Integer.parseInt(action.substring(1));
                    String production = grammar.get(productionIndex);
                    String[] parts = production.split("->");
                    String leftPart = parts[0].trim();
                    String rightPart = parts[1].trim();
                    int len = rightPart.split(" ").length;
                    for (int i = 0; i < len; i++) {
                        stateStack.pop();
                        symbolStack.pop();
                    }
                    currentState = stateStack.peek();
                    String nonTerminal = leftPart;
                    stateStack.push(goToTable.get(currentState).get(nonTerminal));
                    symbolStack.push(nonTerminal);
                } else if (action.equals("accept")) {
                    return true;
                }
            } else {
                // No valid action in action table, parsing fails
                return false;
            }
        }
        // No more input symbols, parsing fails
        return false;
    }
}

public class main {
    public static void main(String[] args) {
        // Example grammar
        List<String> grammar = Arrays.asList("S -> E", "E -> E + T", "E -> T", "T -> id");

        // Example action table
        Map<Integer, Map<String, String>> actionTable = new HashMap<>();
        Map<String, String> action0 = new HashMap<>();
        action0.put("id", "s3");
        actionTable.put(0, action0);
        Map<String, String> action1 = new HashMap<>();
        action1.put("+", "s4");
        actionTable.put(1, action1);
        Map<String, String> action3 = new HashMap<>();
        action3.put("$", "accept");
        actionTable.put(3, action3);
        Map<String, String> action4 = new HashMap<>();
        action4.put("id", "s3");
        actionTable.put(4, action4);

        // Example go-to table
        Map<Integer, Map<String, Integer>> goToTable = new HashMap<>();
        Map<String, Integer> goTo0 = new HashMap<>();
        goTo0.put("E", 1);
        goToTable.put(0, goTo0);
        Map<String, Integer> goTo1 = new HashMap<>();
        goTo1.put("T", 2);
        goToTable.put(1, goTo1);

        // Example input
        List<String> input = Arrays.asList("id", "+", "id", "$");

        SLRParser parser = new SLRParser(grammar, actionTable, goToTable, input);
        boolean result = parser.parse();

        if (result) {
            System.out.println("Input string accepted by grammar.");
        } else {
            System.out.println("Input string rejected by grammar.");
        }
    }
}
