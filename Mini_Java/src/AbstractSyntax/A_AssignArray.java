package AbstractSyntax;

public class A_AssignArray extends A_stmt{
    public String  id;
    public Absyn index;
    public Absyn value;
    public A_AssignArray(String id, Absyn index, Absyn value){
        this.id=id;
        this.index=index;
        this.value=value;
    }
}
