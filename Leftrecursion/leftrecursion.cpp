#include <iostream>
#include <vector>
#include <string>
using namespace std;

int main() {
    char nonTerminal;
    cout << "Enter the Parent Non-Terminal: ";
    cin >> nonTerminal;

    int numProductions;
    cout << "Enter the number of productions: ";
    cin >> numProductions;

    vector<string> productions(numProductions);
    cout << "Enter Productions (one per line):\n";
    for (int i = 0; i < numProductions; i++) {
        cin >> productions[i];
    }

    string op1 = string(1, nonTerminal) + "\'->";
    string op2 = string(1, nonTerminal) + "->";

    for (const auto& prod : productions) {
        if (prod[0] == nonTerminal) {
            cout << "Production " << prod << " has left recursion." << endl;
            op1 += prod.substr(1) + nonTerminal + "\'|";
        } else {
            cout << "Production " << prod << " does not have left recursion." << endl;
            op2 += prod + nonTerminal + "\'|";
        }
    }

    op1 += "epsilon";  // Using 'epsilon' instead of '#'
    op2.pop_back();  // Remove the last '|'

    cout << "Productions without left recursion:\n";
    cout << op2 << endl;
    cout << op1 << endl;

    return 0;
}