grammar miniJava;

goal: mainClass (classDec)*EOF;

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

statement: '{' (statement)* '}'                                           # block
         | 'if' '(' expression ')' statement 'else' statement             # if
         | 'while' '(' expression ')' statement                           # while
         | 'System.out.println' '(' expression ')' ';'                    # printExpr
         | ID '=' expression ';'                                          # assign
         | ID '[' expression ']' '=' expression ';'                       # assignArray
         ;

expression: expression op=('&&'|'<'|'+'|'-'|'*') expression               # ALOp
          | expression '[' expression ']'                                 # indexArray
          | expression '.' 'length'                                       # length
          | expression '.' ID '(' (expression (',' expression)* )? ')'    # callMember
          | INT                                                           # int
          | 'true'                                                        # true
          | 'false'                                                       # false
          | ID                                                            # id
          | 'this'                                                        # this
          | 'new' 'int' '[' expression ']'                                # newArray
          | 'new' ID '(' ')'                                              # newObject
          | '!' expression                                                # notExpr
          | '(' expression ')'                                            # parens
          ;

ID: [_a-zA-Z][_a-zA-Z0-9]*;
INT: [1-9][0-9]*;
WS: [ \t\n\r]+ -> skip;

MUL: '*';
ADD: '+';
SUB: '-';
LT:  '<';
AND: '&&';