package AbstractSyntax;

public class A_Block extends A_stmt{
    public Absyn [] stmts;
    public A_Block(Absyn []stmts){
        this.stmts = stmts;
    }
}
