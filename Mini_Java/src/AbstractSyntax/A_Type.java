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
    public String toString(){
        if(category==0){
            return "int[]";
        }
        else if(category==1){
            return "boolean";
        }
        else if(category==2){
            return "int";
        }
        else {
            return id;
        }
    }
    public boolean equals(A_Type t2){
        if(category!=t2.category){
            return false;
        }
        if(category<3){
            return true;
        }
        return id.compareTo(t2.id)==0;
    }
}

