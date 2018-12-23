package AbstractSyntax;

public class A_Type {
    enum category {
        array_t,
        int_t,
        bool_t,
        ref_t
    }
    category cat;
    A_IdExp id = null;
    public A_Type(category cat,A_IdExp id){
        this.cat=cat;
        if(cat==category.ref_t){
            this.id=id;
        }
    }
}

