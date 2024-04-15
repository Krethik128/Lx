/* File: lexer.l */
%{
#include <stdio.h>
%}

%%

[a-z]       { printf("Lowercase letter: %c\n", yytext[0]); }
[A-Z]       { printf("Uppercase letter: %c\n", yytext[0]); }
[0-9]       { printf("Digit: %c\n", yytext[0]); }
.           { /* Ignore all other characters */ }

%%

int main() {
    yylex();
    return 0;
}
