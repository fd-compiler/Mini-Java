package AbstractSyntax;

public class A_If extends A_stmt{
    Absyn exp;
    Absyn branch1;
    Absyn branch2;
    public A_If(Absyn exp, Absyn b1, Absyn b2){
        this.exp=exp;
        branch1=b1;
        branch2=b2;
    }
}
