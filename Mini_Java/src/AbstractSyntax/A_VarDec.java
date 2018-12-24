package AbstractSyntax;

public class A_VarDec extends Absyn {
    public Absyn t;
    public String id;
    public A_VarDec(Absyn t, String id){
        this.t=t;
        this.id=id;
    }
}
