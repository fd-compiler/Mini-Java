package AbstractSyntax;

public class A_OpExp extends A_exp {
    public A_Oper oper;
    public Absyn left;
    public Absyn right;
    public A_OpExp(A_Oper op, Absyn l, Absyn r){
        oper=op;
        left=l;
        right=r;
    }
}
