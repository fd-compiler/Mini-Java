package AbstractSyntax;

public class A_NotExp implements A_exp{
    A_exp exp;
    public A_NotExp(A_exp exp){
        this.exp=exp;
    }
}
