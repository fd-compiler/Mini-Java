package AbstractSyntax;

public class A_Goal extends Absyn {
    public Absyn a_main;
    public Absyn [] classes;
    public A_Goal(Absyn m, Absyn []cs){
        a_main=m;
        classes = cs;
    }
}
