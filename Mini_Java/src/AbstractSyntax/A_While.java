package AbstractSyntax;

public class A_While extends A_stmt{
    A_exp exp;
    A_stmt stmt;
    public A_While(A_exp exp,A_stmt stmt){
        this.exp=exp;
        this.stmt=stmt;
    }
}
