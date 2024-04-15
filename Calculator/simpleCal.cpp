#include <iostream>
using namespace std;

int main() {
    char op;
    float num1, num2;

    // Input two numbers and operator from user
    cout << "Enter first number: ";
    cin >> num1;
    cout << "Enter operator (+, -, *, /): ";
    cin >> op;
    cout << "Enter second number: ";
    cin >> num2;

    // Perform calculation based on operator
    switch(op) {
        case '+':
            cout << "Result: " << num1 << " + " << num2 << " = " << num1 + num2;
            break;
        case '-':
            cout << "Result: " << num1 << " - " << num2 << " = " << num1 - num2;
            break;
        case '*':
            cout << "Result: " << num1 << " * " << num2 << " = " << num1 * num2;
            break;
        case '/':
            if (num2 != 0)
                cout << "Result: " << num1 << " / " << num2 << " = " << num1 / num2;
            else
                cout << "Error! Division by zero.";
            break;
        default:
            cout << "Invalid operator!";
    }

    return 0;
}
