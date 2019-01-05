package ErrorDetection;

import java.util.ArrayList;
import java.util.List;

public class Obj {
    InheritanceTree tree;
    String type;

    int iValue;
    boolean bValue;
    int []aValue;

    //todo: to support tracking the fields of the parent, we may need to change this structure
    List<String> field_names;
    List<String> types;
    List<Obj> fields;

    public String get_class(){
        return tree.id;
    }

    public Obj(InheritanceTree tree){
        this.tree = tree;
        type=tree.id;
        field_names = new ArrayList<>();
        types = new ArrayList<>();
        fields = new ArrayList<>();

        InheritanceTree t = tree;
        //todo: here we do not care parent, now we don't loop
        while(t!=null){
            for(int i=0;i<t.types.size();i++){
                String temp_id = t.field_names.get(i);
                field_names.add(temp_id);
                String f_type = t.types.get(i);
                types.add(f_type);
                fields.add(new Obj(SymbolTable.s2tree.get(f_type)));
            }
            t=t.parent;
            break; //todo: do not loop now
        }
    }

}
