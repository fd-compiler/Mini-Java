package AbstractSyntax;

public class A_If implements A_stmt{
    A_exp exp;
    A_stmt branch1;
    A_stmt branch2;
    public A_If(A_exp exp,A_stmt b1,A_stmt b2){
        this.exp=exp;
        branch1=b1;
        branch2=b2;
    }
}
