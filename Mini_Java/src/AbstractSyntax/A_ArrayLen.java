package AbstractSyntax;

public class A_ArrayLen extends A_exp{
    public Absyn array; // not id because it may be a 2 dimensional array, e.g. a[1][2], where a[1] is an expression
    public A_ArrayLen(Absyn ar){
        array=ar;
    }
}
