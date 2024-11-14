grammar Logic;

// Parser rules (define the structure of mathematical logic expressions)

// The starting rule for parsing expressions
expr:
    | '!' expr          // Negation
    | expr '&&' expr    // Conjunction
    | expr '||' expr    // Disjunction
    | expr '->' expr    // Implication
    | expr '<->' expr   // Bi-conditional
    | '(' expr ')'      // Parentheses
    | VAR               // Variable
    ;

// Lexer rules (define tokens for operators, parentheses, and variables)

// Logical operators
AND    : '&&';
OR     : '||';
NOT    : '!';
IMPL   : '->';
IFF    : '<->';

// Parentheses
LPAREN : '(';
RPAREN : ')';

// Variables (single letters, e.g., p, q, r)
VAR    : [a-z];

// Ignore whitespace
WS     : [ \t\r\n]+ -> skip;
