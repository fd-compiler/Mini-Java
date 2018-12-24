package AbstractSyntax;

public class A_OpExp extends A_exp {
    A_oper oper;
    A_exp left;
    A_exp right;
    public A_OpExp(A_oper op, A_exp l, A_exp r){
        oper=op;
        left=l;
        right=r;
    }
}
