package AbstractSyntax;

public class A_MainClass {
    A_IdExp class_name;
    A_IdExp args;
    A_stmt stmt;
    public A_MainClass(A_IdExp class_name,A_IdExp args, A_stmt stmt){
        this.class_name=class_name;
        this.args=args;
        this.stmt=stmt;
    }
}
