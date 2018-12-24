package AbstractSyntax;

public class A_Block extends A_stmt{
    Absyn [] stmts;
    public A_Block(Absyn []stmts){
        this.stmts = stmts;
    }
}
