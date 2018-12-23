package AbstractSyntax;

public class A_AssignArray implements A_stmt{
    A_IdExp id;
    A_exp index;
    A_exp value;
    public A_AssignArray(A_IdExp id, A_exp index, A_exp value){
        this.id=id;
        this.index=index;
        this.value=value;
    }
}
