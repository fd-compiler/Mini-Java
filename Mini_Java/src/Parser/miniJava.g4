grammar miniJava;

@parser::members{
    String [] my_s = new String[10];
}//todo: change this

goal: mainClass (classDec)*EOF {System.out.println("this is main");};

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

statement
locals [int st=0]
     : '{' (statement
            {
                $st++;
            }
            )* '}' //todo: implement this
     | 'if' '(' expression ')' statement 'else' statement
     | 'while' '(' expression ')' statement
     | 'System.out.println' '(' expression ')' ';'
     | ID '=' expression ';'
     | ID '[' expression ']' '=' expression ';'
     ;

expression
locals [int ct=1]
    : expression op=('&&'|'<'|'+'|'-'|'*') expression
    | expression '[' expression ']'
    | expression '.' 'length'
    | expression '.' ID '(' (expression (',' temp=expression{$ct++;my_s[$ct]=$temp.text;})* )? ')'
    {System.out.print($ct);System.out.println("p");for(int ii=2;ii<=$ct;ii++) System.out.println(my_s[ii]);}
    //todo: implement this
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
WS: [ \t\n\r]+ -> skip;

MUL: '*';
ADD: '+';
SUB: '-';
LT:  '<';
AND: '&&';