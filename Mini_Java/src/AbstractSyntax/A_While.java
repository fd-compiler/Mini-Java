package AbstractSyntax;

public class A_While extends A_stmt{
    Absyn exp;
    Absyn stmt;
    public A_While(Absyn exp, Absyn stmt){
        this.exp=exp;
        this.stmt=stmt;
    }
}
