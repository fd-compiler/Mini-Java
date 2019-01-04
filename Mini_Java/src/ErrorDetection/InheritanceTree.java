package ErrorDetection;

import java.util.ArrayList;
import java.util.List;

public class InheritanceTree {
    public boolean define=false;
    public String id;
    public InheritanceTree parent;

    //fields
    // int "int"
    // int[] "int[]"
    // boolean "boolean"
    // id id
    List<String> types;
    List<String> field_names;

    InheritanceTree(String id){
        define=true;
        this.id=id;
    }

    public InheritanceTree(String id, InheritanceTree parent){
        this.id=id;
        this.parent=parent;
        field_names = new ArrayList<>();
        types = new ArrayList<>();
    }
}
