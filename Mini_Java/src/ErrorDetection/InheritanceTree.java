package ErrorDetection;

import AbstractSyntax.Absyn;
import java.util.ArrayList;
import java.util.List;

public class InheritanceTree {
    public boolean define=false;
    public String id;
    public InheritanceTree parent;

    // int "int"
    // int[] "int[]"
    // boolean "boolean"
    // id id
    List<String> types= new ArrayList<>();
    List<String> field_names= new ArrayList<>();

    List<String> methods= new ArrayList<>();
    List<Absyn> nodes= new ArrayList<>();

    InheritanceTree(String id){
        define=true;
        this.id=id;
    }

    InheritanceTree(String id, InheritanceTree parent){
        this.id=id;
        this.parent=parent;
    }

    void append_field(String n,int cat,String t){
        if(cat==3){
            types.add(t);
        }else if(cat==0){
            types.add("int[]");
        }else if(cat==1){
            types.add("boolean");
        }else if(cat==2){
            types.add("int");
        }
        field_names.add(n);
    }

    void append_method(String m, Absyn node){
        methods.add(m);
        nodes.add(node);
    }
}
