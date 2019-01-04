package AbstractSyntax;

public class A_CallExp extends A_exp{
    public Absyn obj;
    public String method;
    public Absyn[] paras;
    public A_CallExp(Absyn obj, String method, Absyn[] paras){
        this.obj=obj;
        this.method=method;
        this.paras=paras;
    }
}
