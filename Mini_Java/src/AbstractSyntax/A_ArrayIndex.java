package AbstractSyntax;

public class A_ArrayIndex extends A_exp{
    public Absyn array;
    public Absyn index;
    public A_ArrayIndex(Absyn ar, Absyn idx){
        array=ar;
        index=idx;
    }
}
