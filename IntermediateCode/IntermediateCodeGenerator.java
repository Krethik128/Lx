package IntermediateCode; // Define the package

import java.util.Stack;

public class IntermediateCodeGenerator {
    private Stack<String> labelStack;
    private StringBuilder intermediateCode;
    private int labelCounter;

    public IntermediateCodeGenerator() {
        labelStack = new Stack<>();
        intermediateCode = new StringBuilder();
        labelCounter = 0;
    }

    public String generateIntermediateCode(String condition, String codeBlock) {
        String label1 = generateNewLabel();
        String label2 = generateNewLabel();

        intermediateCode.append("if ").append(condition).append(" goto ").append(label1).append("\n");
        intermediateCode.append(codeBlock).append("\n");
        intermediateCode.append("goto ").append(label2).append("\n");
        intermediateCode.append("label ").append(label1).append("\n");
        intermediateCode.append("label ").append(label2).append("\n");

        return intermediateCode.toString();
    }

    public String generateIntermediateCodeForWhile(String condition, String codeBlock) {
        String label1 = generateNewLabel();
        String label2 = generateNewLabel();

        intermediateCode.append("label ").append(label1).append("\n");
        intermediateCode.append("if ").append(condition).append(" goto ").append(label2).append("\n");
        intermediateCode.append("goto ").append(label1).append("\n");
        intermediateCode.append("label ").append(label2).append("\n");

        return intermediateCode.toString();
    }

    private String generateNewLabel() {
        return "L" + labelCounter++;
    }

    public static void main(String[] args) {
        IntermediateCodeGenerator generator = new IntermediateCodeGenerator();
       
        // Example usage for If statement
        String ifIntermediateCode = generator.generateIntermediateCode("x < 10", "y = y + 1");
        System.out.println("Intermediate Code for If statement:\n" + ifIntermediateCode);

        // Example usage for While loop
        String whileIntermediateCode = generator.generateIntermediateCodeForWhile("x > 0", "x = x - 1");
        System.out.println("Intermediate Code for While loop:\n" + whileIntermediateCode);
    }
}
