%{
    #include <stdio.h>
%}

%%

// Keywords
if|display|then|while|for|to {
    printf("\n%7s : Keyword", yytext);
}

// Data types
int|float|double {
    printf("\n%7s : datatype", yytext);
}

// Identifiers (must start with a letter or underscore, followed by any combination of letters, digits, and underscores)
[a-zA-Z_][a-zA-Z0-9]* {
    printf("\n%7s : Identifier", yytext);
}

// Relational Operators (includes operators that could optionally be followed by an equals sign)
[><=!]=? {
    printf("\n%7s : Relational Operator", yytext);
}

%%

int main() {
    yylex();
    return 0;
}
