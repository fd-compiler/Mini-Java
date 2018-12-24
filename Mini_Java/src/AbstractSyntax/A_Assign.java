package AbstractSyntax;

public class A_Assign extends A_stmt {
    String id;
    Absyn exp;
    public A_Assign(String id, Absyn exp){
        this.id=id;
        this.exp=exp;
    }
}
