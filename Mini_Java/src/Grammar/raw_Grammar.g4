grammar miniJava;

goal: mainClass (classDec)*;

mainClass: 'class' ID '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID ')' '{'
statement '}' '}';

classDec: 'class' ID ('extends' ID)? '{' (varDec)* (methodDec)* '}';

varDec: type ID ';';

methodDec: 'public' type ID '(' (type ID (',' type ID)* )? ')' '{' (varDec)* (statement)*
'return' expression ';' '}';

type: 'int' '[' ']'
    | 'boolean'
    | 'int'
    | ID
    ;

statement: '{' (statement)* '}'
         | 'if' '(' expression ')' statement 'else' statement
         | 'while' '(' expression ')' statement
         | 'System.out.println' '(' expression ')' ';'
         | ID '=' expression ';'
         | ID '[' expression ']' '=' expression ';'
         ;

expression: expression ('&&'|'<'|'+'|'-'|'*') expression
          | expression '[' expression ']'
          | expression '.' 'length'
          | expression '.' ID '(' (expression (',' expression)* )? ')'
          | INT
          | 'true'
          | 'false'
          | ID
          | 'this'
          | 'new' 'int' '[' expression ']'
          | 'new' ID '(' ')'
          | '!' expression
          | '(' expression ')'
          ;

ID: [_a-zA-Z][_a-zA-Z0-9]*;
INT: [1-9][0-9]*;
WS: [ \t\n]+ -> skip;

MUL: '*';
ADD: '+';
SUB: '-';
LT:  '<';
AND: '&&';