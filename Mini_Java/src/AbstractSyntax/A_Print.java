package AbstractSyntax;

public class A_Print implements A_stmt {
    A_exp exp;
    public A_Print(A_exp exp){
        this.exp=exp;
    }
}
