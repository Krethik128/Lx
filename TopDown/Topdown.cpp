#include <iostream>
#include <vector>
#include <unordered_set>
#include <unordered_map>
#include <string>
using namespace std;

// Structure to hold a production rule
struct Production {
    char nonTerminal;
    string expression;
};

// Function to remove left recursion
vector<Production> removeLeftRecursion(const vector<Production>& productions) {
    vector<Production> newProductions;

    for (size_t i = 0; i < productions.size(); i++) {
        for (size_t j = 0; j < i; j++) {
            char leftNonTerminal = productions[i].nonTerminal;
            char rightNonTerminal = productions[j].nonTerminal;
            string alpha = productions[i].expression;
            string beta = productions[j].expression;

            if (leftNonTerminal == rightNonTerminal) {
                newProductions.push_back({ leftNonTerminal, beta + leftNonTerminal + "'" });
                newProductions.push_back({ static_cast<char>(leftNonTerminal + '\''), alpha + leftNonTerminal + "'" });

            } else {
                newProductions.push_back(productions[i]);
            }
        }
    }

    return newProductions;
}

// Function to perform left factoring
vector<Production> leftFactor(const vector<Production>& productions) {
    vector<Production> newProductions;

    for (size_t i = 0; i < productions.size(); i++) {
        string prefix = productions[i].expression.substr(0, 1);
        vector<Production> commonPrefix;

        for (size_t j = i + 1; j < productions.size(); j++) {
            if (productions[j].expression.substr(0, 1) == prefix) {
                commonPrefix.push_back(productions[j]);
            }
        }

        if (!commonPrefix.empty()) {
            newProductions.push_back({ productions[i].nonTerminal, prefix });
            for (size_t j = 0; j < commonPrefix.size(); j++) {
                newProductions.push_back({
                    productions[i].nonTerminal,
                    commonPrefix[j].expression.substr(1)
                });
            }
            i += commonPrefix.size();
        } else {
            newProductions.push_back(productions[i]);
        }
    }

    return newProductions;
}

// Function to compute FIRST sets
unordered_map<char, unordered_set<char>> computeFirstSets(const vector<Production>& productions) {
    unordered_map<char, unordered_set<char>> firstSets;

    for (const auto& production : productions) {
        char nonTerminal = production.nonTerminal;
        const string& expression = production.expression;

        if (!isupper(expression[0])) {
            firstSets[nonTerminal].insert(expression[0]);
        } else {
            for (char c : expression) {
                if (!isupper(c)) {
                    firstSets[nonTerminal].insert(c);
                    break;
                } else {
                    firstSets[nonTerminal].insert(firstSets[c].begin(), firstSets[c].end());
                    if (firstSets[c].find('e') == firstSets[c].end()) {
                        break;
                    }
                }
            }
        }
    }

    return firstSets;
}

// Function to compute FOLLOW sets
unordered_map<char, unordered_set<char>> computeFollowSets(const vector<Production>& productions,
                                                           const unordered_map<char, unordered_set<char>>& firstSets) {
    unordered_map<char, unordered_set<char>> followSets;
    followSets[productions[0].nonTerminal].insert('$');

    for (const auto& production : productions) {
        for (size_t i = 0; i < production.expression.size(); i++) {
            char symbol = production.expression[i];

            if (isupper(symbol)) {
                if (i == production.expression.size() - 1) {
                    followSets[symbol].insert(followSets[production.nonTerminal].begin(), followSets[production.nonTerminal].end());
                } else {
                    for (size_t j = i + 1; j < production.expression.size(); j++) {
                        if (!isupper(production.expression[j])) {
                            followSets[symbol].insert(production.expression[j]);
                            break;
                        } else {
                            followSets[symbol].insert(firstSets.at(production.expression[j]).begin(), firstSets.at(production.expression[j]).end());
                            if (firstSets.at(production.expression[j]).find('e') == firstSets.at(production.expression[j]).end()) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    return followSets;
}

// Function to print sets
void printSets(const unordered_map<char, unordered_set<char>>& sets, const string& setName) {
    cout << setName << " Sets:" << endl;
    for (const auto& set : sets) {
        cout << "  " << set.first << " : { ";
        for (char c : set.second) {
            cout << c << " ";
        }
        cout << "}" << endl;
    }
    cout << endl;
}

int main() {
    vector<Production> productions = {
        {'S', "Sa"},
        {'S', "b"}
    };

    // Remove left recursion
    vector<Production> nonRecursiveProductions = removeLeftRecursion(productions);
    cout << "After Removing Left Recursion:" << endl;
    for (const auto& production : nonRecursiveProductions) {
        cout << production.nonTerminal << " -> " << production.expression << endl;
    }
    cout << endl;

    // Perform left factoring
    vector<Production> factoredProductions = leftFactor(nonRecursiveProductions);
    cout << "After Left Factoring:" << endl;
    for (const auto& production : factoredProductions) {
        cout << production.nonTerminal << " -> " << production.expression << endl;
    }
    cout << endl;

    // Compute FIRST sets
    unordered_map<char, unordered_set<char>> firstSets = computeFirstSets(factoredProductions);
    printSets(firstSets, "FIRST");

    // Compute FOLLOW sets
    unordered_map<char, unordered_set<char>> followSets = computeFollowSets(factoredProductions, firstSets);
    printSets(followSets, "FOLLOW");

    return 0;
}
