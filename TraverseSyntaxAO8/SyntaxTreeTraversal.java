// Node class representing each node in the syntax tree
class Node {
    char data;
    Node left, right;

    public Node(char item) {
        data = item;
        left = right = null;
    }
}

// Main class to perform tree traversal and arithmetic operations
public class SyntaxTreeTraversal {
    // Function to traverse the syntax tree in postorder
    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.data + " ");
        }
    }

    // Function to traverse the syntax tree in preorder
    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // Function to evaluate the syntax tree
    public int evaluate(Node root) {
        // Base case: if the tree is empty, return 0
        if (root == null) {
            return 0;
        }

        // If leaf node, return the integer value of the node
        if (root.left == null && root.right == null) {
            return Character.getNumericValue(root.data);
        }

        // Evaluate left and right subtrees recursively
        int leftVal = evaluate(root.left);
        int rightVal = evaluate(root.right);

        // Perform arithmetic operation based on the operator
        switch (root.data) {
            case '+':
                return leftVal + rightVal;
            case '-':
                return leftVal - rightVal;
            case '*':
                return leftVal * rightVal;
            case '/':
                if (rightVal != 0) {
                    return leftVal / rightVal;
                } else {
                    throw new ArithmeticException("Cannot divide by zero");
                }
        }
        return 0;
    }

    public static void main(String[] args) {
        // Create a sample syntax tree for expression: ((5*2)+(8/2))
        Node root = new Node('+');
        root.left = new Node('*');
        root.right = new Node('/');
        root.left.left = new Node('5');
        root.left.right = new Node('2');
        root.right.left = new Node('8');
        root.right.right = new Node('2');

        SyntaxTreeTraversal tree = new SyntaxTreeTraversal();

        System.out.println("Postorder traversal of the syntax tree:");
        tree.postOrder(root);
        System.out.println();

        System.out.println("Preorder traversal of the syntax tree:");
        tree.preOrder(root);
        System.out.println();

        int result = tree.evaluate(root);
        System.out.println("Result of the arithmetic expression: " + result);
    }
}
