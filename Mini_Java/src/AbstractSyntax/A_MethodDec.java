package AbstractSyntax;

public class A_MethodDec extends Absyn {
    public Absyn ret_t;
    public String id;
    public String[] paras;
    public Absyn[] paras_t;
    public Absyn[] varDecs;
    public Absyn[] stmts;
    public Absyn ret;
    public A_MethodDec(Absyn ret_t, String id, Absyn[] paras_t, String[] paras,
                       Absyn []varDecs, Absyn[] stmts, Absyn ret)
    {
        this.ret_t=ret_t;
        this.id=id;
        this.paras=paras;
        this.paras_t=paras_t;
        this.varDecs=varDecs;
        this.stmts=stmts;
        this.ret=ret;
    }
}
