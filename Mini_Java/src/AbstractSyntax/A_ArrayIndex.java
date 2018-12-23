package AbstractSyntax;

public class A_ArrayIndex implements A_exp{
    A_exp array;
    A_exp index;
    public A_ArrayIndex(A_exp ar, A_exp idx){
        array=ar;
        index=idx;
    }
}
