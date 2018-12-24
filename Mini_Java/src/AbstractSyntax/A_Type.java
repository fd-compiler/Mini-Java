package AbstractSyntax;

public class A_Type extends Absyn{
    public int category;
    public String id;
    public A_Type(int cat,String id){
        this.category = cat;
        if(cat==3){
            this.id=id;
        }
    }
}

