package SimpJva;

import java.util.Scanner;

public class calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Calculator!");
        System.out.println("Enter an expression (e.g., 5 + 3):");

        // Read the expression from the user
        String input = scanner.nextLine();

        // Split the input string by whitespace7
        String[] tokens = input.split("\\s+");

        if (tokens.length != 3) {
            System.out.println("Invalid input format. Please enter an expression like '5 + 3'.");
            return;
        }

        // Parse the operands and operator
        double operand1, operand2, result;
        try {
            operand1 = Double.parseDouble(tokens[0]);
            operand2 = Double.parseDouble(tokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid operands. Please enter numeric values.");
            return;
        }

        String operator = tokens[1];

        // Perform the calculation based on the operator
        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                if (operand2 == 0) {
                    System.out.println("Division by zero is not allowed.");
                    return;
                }
                result = operand1 / operand2;
                break;
            default:
                System.out.println("Invalid operator. Please use '+', '-', '*', or '/'.");
                return;
        }

        // Print the result
        System.out.println("Result: " + result);
    }
}
