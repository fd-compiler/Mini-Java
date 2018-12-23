package AbstractSyntax;

public class A_Assign implements A_stmt {
    A_IdExp id;
    A_exp exp;
    public A_Assign(A_IdExp id, A_exp exp){
        this.id=id;
        this.exp=exp;
    }
}
