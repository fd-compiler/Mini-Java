grammar miniJava;

@header{
    import AbstractSyntax.*;
}

@parser::members{
    String [] my_s = new String[10];
}//todo: change this

goal
locals [int cd=0]
    : mainClass (classDec{
                    $cd++;
                }
                )*EOF {
                    A_ClassDec[] ls = new A_ClassDec[$cd];
                };//todo: add return value

mainClass: 'class' ID '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID ')' '{'
statement '}' '}';

classDec
locals [int ext=0, int vd=0, int md=0]
    : 'class' ID ('extends' ID { $ext++; }
    )? '{' (varDec { $vd++; })* (methodDec { $md++; })* '}';

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

expression returns [A_exp r_exp]
locals [int ct=1]
    : expression op=('&&'|'<'|'+'|'-'|'*') expression
    | expression '[' expression ']'
    | expression '.' 'length'
    | expression '.' ID '(' (expression (',' temp=expression{$ct++;my_s[$ct]=$temp.text;})* )? ')'
    {System.out.print($ct);System.out.println("p");for(int ii=2;ii<=$ct;ii++) System.out.println(my_s[ii]);}
    //todo: implement this
    | INT     {$r_exp = new A_IntExp($INT.int); System.out.println("lueluelue"+$r_exp.temp);}
    | 'true'  {$r_exp = new A_BoolExp(true);}
    | 'false' {$r_exp = new A_BoolExp(false);}
    | ID
    | 'this'
    | 'new' 'int' '[' expression ']'
    | 'new' ID '(' ')'
    | '!' expression     {$r_exp = new A_NotExp($expression.r_exp); } //Do not use like this because the expression value has been throwed due to GC
    | '(' expression ')' {$r_exp = $expression.r_exp;}
    ;

ID: [_a-zA-Z][_a-zA-Z0-9]*;
INT: [1-9][0-9]*;
WS: [ \t\n\r]+ -> skip;

MUL: '*';
ADD: '+';
SUB: '-';
LT:  '<';
AND: '&&';