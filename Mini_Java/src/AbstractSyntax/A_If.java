package AbstractSyntax;

public class A_If extends A_stmt{
    public Absyn exp;
    public Absyn branch1;
    public Absyn branch2;
    public A_If(Absyn exp, Absyn b1, Absyn b2){
        this.exp=exp;
        branch1=b1;
        branch2=b2;
    }
}
