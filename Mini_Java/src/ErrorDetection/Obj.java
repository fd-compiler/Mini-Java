package ErrorDetection;

import java.util.ArrayList;
import java.util.List;

public class Obj {
    String id;
    InheritanceTree type;

    int iValue;
    boolean bValue;
    int []aValue;

    //todo: to support tracking the fields of the parent, we may need to change this structure
    List<String> field_names;
    List<String> types;
    List<Obj> fields;

    public String get_class(){
        return type.id;
    }

    public Obj(String id, String ft){
        this.id=id;
        type = SymbolTable.s2tree.get(ft);
    }

    public Obj(String id, InheritanceTree type){
        this.id = id;
        this.type = type;
        field_names = new ArrayList<>();
        types = new ArrayList<>();
        fields = new ArrayList<>();

        InheritanceTree t = type;
        //todo: here we do not care parent, now we don't loop
        while(t!=null){
            for(int i=0;i<t.types.size();i++){
                String temp_id = t.field_names.get(i);
                field_names.add(temp_id);
                String f_type = t.types.get(i);
                types.add(f_type);
                if(f_type.compareTo("int")*f_type.compareTo("int[]")*f_type.compareTo("boolean")==0){
                    fields.add(new Obj(temp_id,f_type));
                }
                else{
                    fields.add(null);
                }
            }
            t=t.parent;
            break; //todo: do not loop now
        }
    }

}
