package AbstractSyntax;

public class A_ClassDec extends Absyn {
    public String id;
    public String superclass;
    public Absyn[] varDecs;
    public Absyn[] methodDecs;
    public A_ClassDec(String id, String superclass, Absyn[] vd, Absyn[] md){
        this.id=id;
        this.superclass=superclass;
        varDecs = vd;
        methodDecs = md;
    }
}
