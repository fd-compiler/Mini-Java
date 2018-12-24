package AbstractSyntax;

public class A_Assign extends A_stmt {
    public String id;
    public Absyn exp;
    public A_Assign(String id, Absyn exp){
        this.id=id;
        this.exp=exp;
    }
}
