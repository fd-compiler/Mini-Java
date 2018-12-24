package AbstractSyntax;

public class A_Block extends A_stmt{
    A_stmt [] stmts;
    public A_Block(A_stmt []s){
        int size = s.length;
        stmts=new A_stmt[size];
        for(int i=0;i<size;i++){
            stmts[i]=s[i];
        }
    }
}
