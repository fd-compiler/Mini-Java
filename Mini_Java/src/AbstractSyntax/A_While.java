package AbstractSyntax;

public class A_While extends A_stmt{
    public Absyn exp;
    public Absyn stmt;
    public A_While(Absyn exp, Absyn stmt){
        this.exp=exp;
        this.stmt=stmt;
    }
}
