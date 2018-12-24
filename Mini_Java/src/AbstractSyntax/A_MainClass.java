package AbstractSyntax;

public class A_MainClass extends Absyn{
    public String id;
    public String args;
    public Absyn stmt;
    public A_MainClass(String id, String  args, Absyn stmt){
        this.id=id;
        this.args=args;
        this.stmt=stmt;
    }
}
