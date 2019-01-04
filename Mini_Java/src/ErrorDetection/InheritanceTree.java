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
    List<String> types;
    List<String> field_names;

    List<String> methods;
    List<Absyn> nodes;

    InheritanceTree(String id){
        define=true;
        this.id=id;
    }

    InheritanceTree(String id, InheritanceTree parent){
        this.id=id;
        this.parent=parent;
        field_names = new ArrayList<>();
        types = new ArrayList<>();
        methods = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    public void append_field(String n,int cat,String t){
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
}
