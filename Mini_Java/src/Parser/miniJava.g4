grammar miniJava;

goal
locals [int cd=0]: mainClass (classDec {$cd++;})*EOF;

mainClass: 'class' ID '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID ')' '{'
statement '}' '}';

classDec
locals [boolean ext=false, int vd=0, int md=0]
: 'class' ID ('extends' ID {$ext=true;})? '{' (varDec {$vd++;})* (methodDec {$md++;})* '}';

varDec: type ID ';';

methodDec
locals [int vd=0, int st=0, int pa=0]
: 'public' type ID '(' (type ID (',' type ID {$pa++;})* {$pa++;})? ')' '{' (varDec)* (statement)*
'return' expression ';' '}';

type
locals [int t]
    : 'int' '[' ']' {$t=0;}
    | 'boolean'     {$t=1;}
    | 'int'         {$t=2;}
    | ID            {$t=3;}
    ;

statement
locals [int st=0]
    : '{' (statement {$st++;})* '}'                                  # block
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