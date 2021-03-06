package ErrorDetection;

import AbstractSyntax.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RuntimeInterpreter {
    private Absyn root;
    private boolean classvar;
    private String currentClass;
    private Obj currentObj;

    private List<String> classStack = new LinkedList<>();
    private List<Obj> objStack = new LinkedList<>();

    public RuntimeInterpreter(Absyn root){
        this.root=root;
    }

    public void interpret(){
        interpret(root);
    }

    private Result interpret(Absyn node){
        if(node.getClass()==A_Goal.class){
            //the root
            interpret(((A_Goal)node).a_main);
            Absyn []classes = ((A_Goal)node).classes;
            for(int i=0;i<classes.length;i++){
                interpret(classes[i]);
            }
            return null;
        }
        else if(node.getClass()==A_MainClass.class){
            //here we enter the main method of the program
            String id = ((A_MainClass)node).id;
            currentClass=id;
            currentObj= new Obj(SymbolTable.s2tree.get(id));
            currentObj.isInitial=true;
            Absyn stmt = ((A_MainClass)node).stmt;
            interpret(stmt);
            return null;
        }
        else if(node.getClass()==A_ClassDec.class){
            //we register vars and methods in ErrorDetector, here we skip
            return null;
        }
        else if(node.getClass()==A_VarDec.class){
            //we register class vars in ErrorDetector, here we register local vars
            if(!classvar){
                Result res = new Result();
                String id = ((A_VarDec)node).id;
                Absyn absyn = ((A_VarDec)node).t;
                int cat = ((A_Type)absyn).category;
                String typename = ((A_Type)absyn).id;
                switch (cat){
                    case 0:typename="int[]";break;
                    case 1:typename="boolean";break;
                    case 2:typename="int";break;
                    case 3:{
                        if(!SymbolTable.s2tree.containsKey(typename)){
                            System.err.println("Cannot resolve symbol: "+typename);
                            return null;
                        }
                        break;
                    }
                    default:
                        System.out.println("Debug: Undefined type: "+cat);
                        return null;
                }
                res.type=typename;
                res.id = id; //u82jew
                return res;
            }
            return null;
        }
        else if(node.getClass()==A_MethodDec.class){
            //when we declare this method, we will not enter this node
            //when we call this method, we enter this node and calculate
            String []pnames = ((A_MethodDec)node).paras;
            Set<String> set = new HashSet<>();
            for(int i=0;i<pnames.length;i++){
                if(set.contains(pnames[i])){
                    System.err.println("Variable "+pnames[i]+" is already defined in the scope");
                    return null;
                }else{
                    set.add(pnames[i]);
                }
            }
            classvar=false;
            Absyn []varDecs = ((A_MethodDec)node).varDecs;
            for(int i=0;i<varDecs.length;i++){
                Result var = interpret(varDecs[i]);
                if(var==null)
                    return null;
                if(var.type.compareTo("int")==0){
                    SymbolTable.insert_not_initial(var.id);
                }
                else if(var.type.compareTo("int[]")==0){
                    SymbolTable.insert_a(var.id,null);
                }
                else if(var.type.compareTo("boolean")==0){
                    SymbolTable.insert_b_not_initial(var.id);
                }
                else{
                    SymbolTable.insert_c(var.id,var.type);
                }
            }
            Absyn []stmts = ((A_MethodDec)node).stmts;
            for(int i=0;i<stmts.length;i++){
                interpret(stmts[i]);
            }
            return interpret(((A_MethodDec)node).ret);
        }
        else if(node.getClass()==A_CallExp.class){
            Result r = interpret(((A_CallExp)node).obj);
            String m = ((A_CallExp)node).method;
            if(r==null)
                return null;
            if(r.isBase()){
                System.err.println("Cannot resolve symbol: "+m);
                return null;//todo: null: check every interpret call
            }
            if(!r.obj.isInitial){
                return null;
            }
            if(!r.obj.tree.methods.contains(m)){
                System.err.println("Cannot resolve symbol: "+m);
                return null;
            }
            else{
                InheritanceTree temp_tree = r.obj.tree;
                Absyn temp_node = temp_tree.nodes.get(temp_tree.methods.indexOf(m));

                Absyn []absyns = ((A_MethodDec)temp_node).paras_t;
                String []pnames = ((A_MethodDec)temp_node).paras;
                Absyn []call_p = ((A_CallExp)node).paras;
                Result []temp_res = new Result[call_p.length];
                for(int i=0;i<call_p.length;i++){
                    Result interpret_res = interpret(call_p[i]);
                    if(interpret_res==null)
                        return null;
                    temp_res[i] = interpret_res;
                }
                if(absyns.length!=((A_CallExp)node).paras.length){
                    System.err.print(m+" in "+temp_tree.id+" cannot be applied to (");
                    for(int i=0;i<temp_res.length-1;i++){
                        System.err.print(temp_res[i].type+", ");
                    }
                    if(temp_res.length>=1){
                        System.err.print(temp_res[temp_res.length-1]);
                    }
                    System.err.println(")");
                    return null;
                }
                for(int j=0;j<temp_res.length;j++){
                    if(temp_res[j].type.compareTo(absyns[j].toString())!=0){
                        System.err.print(m+" in "+temp_tree.id+" cannot be applied to (");
                        for(int i=0;i<temp_res.length-1;i++){
                            System.err.print(temp_res[i].type+", ");
                        }
                        if(temp_res.length>=1){//todo: why?
                            System.err.print(temp_res[temp_res.length-1]);
                        }
                        System.err.println(")");
                        return null;
                    }
                }
                classStack.add(currentClass);
                objStack.add(currentObj);
                currentObj = r.obj;
                currentObj.isInitial=true;
                currentClass = r.obj.tree.id;
                SymbolTable.table_to_new(); // enter new scope
                //pass parameters
                for(int i=0;i<temp_res.length;i++){
                    if(temp_res[i].type.compareTo("int")==0){
                        SymbolTable.insert(pnames[i],temp_res[i].iValue);
                    }
                    else if(temp_res[i].type.compareTo("int[]")==0){
                        SymbolTable.insert_a(pnames[i],temp_res[i].aValue);
                    }
                    else if(temp_res[i].type.compareTo("boolean")==0){
                        SymbolTable.insert_b(pnames[i],temp_res[i].bValue);
                    }
                    else {
                        SymbolTable.insert_c(pnames[i],temp_res[i].type);
                    }
                }
                //give control to method
                Result res = interpret(temp_node);
                SymbolTable.table_to_old(); // leave this scope
                currentClass=classStack.get(classStack.size()-1);
                currentObj=objStack.get(objStack.size()-1);
                classStack.remove(classStack.size()-1);
                objStack.remove(objStack.size()-1);
                return res;
            }
        }
        else if(node.getClass()==A_Block.class){
            //block statement
            Absyn []absyns = ((A_Block)node).stmts;
            for(int i=0;i<absyns.length;i++){
                interpret(absyns[i]);
            }
            return null;
        }
        else if(node.getClass()==A_If.class){
            //if statement
            Result res = interpret(((A_If)node).exp);
            if(res==null)
                return null;
            if(res.type.compareTo("boolean")!=0){
                System.err.println("Incompatible types, Required: boolean, Found: "+res.type);
            }
            else if(res.bValue){
                interpret(((A_If)node).branch1);
            }
            else{
                interpret(((A_If)node).branch2);
            }
            return null;
        }
        else if(node.getClass()==A_While.class){
            //while statement
            Result res = interpret(((A_While)node).exp);
            if(res==null)
                return null;
            if (res.type.compareTo("boolean") != 0) {
                System.err.println("Incompatible types. Required: 'boolean', Found: "+res.type);
                return null;
            }
            while(res.bValue){
                interpret(((A_While)node).stmt);
                res = interpret(((A_While)node).exp);
                if(res==null)
                    return null;
            }
            return null;
        }
        else if(node.getClass()==A_Print.class){
            //print statement
            Result res = interpret(((A_Print)node).exp);
            if(res==null)
                return null;
            if(res.type.compareTo("int")!=0){
                System.err.println("Incompatible types. Required: 'int', Found: "+res.type);
            }else{
                System.out.println(res.iValue);
            }
            return null;
        }
        else if(node.getClass()==A_Assign.class){
            //assign
            String id = ((A_Assign)node).id;
            Result res = interpret(((A_Assign)node).exp);
            if(res==null)
                return null;
            Card card = SymbolTable.findVariable(id);
            if(!card.isIn){
                List<String> list = SymbolTable.s2tree.get(currentClass).field_names;
                if(list.contains(id)){
                    String typename = SymbolTable.s2tree.get(currentClass).types.get(list.indexOf(id));
                    if(res.type.compareTo(typename)!=0){
                        System.err.println("Incompatible types. Required: '"+ typename+"' Found: "+res.type);
                    }else{
                        if(res.type.compareTo("int")==0){
                            currentObj.fields.get(currentObj.field_names.indexOf(id)).iValue = res.iValue;
                            currentObj.fields.get(currentObj.field_names.indexOf(id)).isInitial=true;
                        }else if(res.type.compareTo("int[]")==0){
                            currentObj.fields.get(currentObj.field_names.indexOf(id)).aValue = res.aValue;
                            currentObj.fields.get(currentObj.field_names.indexOf(id)).isInitial=true;
                        }else if(res.type.compareTo("boolean")==0){
                            currentObj.fields.get(currentObj.field_names.indexOf(id)).bValue = res.bValue;
                            currentObj.fields.get(currentObj.field_names.indexOf(id)).isInitial=true;
                        }else{
                            currentObj.fields.set(currentObj.field_names.indexOf(id),res.obj);
                            currentObj.fields.get(currentObj.field_names.indexOf(id)).isInitial=true;
                        }
                    }
                }
                else{
                    System.err.println("Cannot resolve symbol: "+id);
                }
            }
            else{
                if(card.type.compareTo(res.type)!=0){
                    System.err.println("Incompatible types. Required: "+card.type+" Found: "+res.type);
                }
                else {
                    if(card.type.compareTo("int")==0){
                        SymbolTable.update(id,res.iValue);
                    }else if(card.type.compareTo("int[]")==0){
                        SymbolTable.update_a(id,res.aValue);
                    }else if(card.type.compareTo("boolean")==0){
                        SymbolTable.update_b(id,res.bValue);
                    }else{
                        SymbolTable.update_c(id,res.obj);
                    }
                }
            }
            return null;
        }
        else if(node.getClass()==A_AssignArray.class){
            //assign array
            String id = ((A_AssignArray)node).id;
            Result r_index = interpret(((A_AssignArray)node).index);
            Result r_value = interpret(((A_AssignArray)node).value);
            if(r_index==null||r_value==null)
                return null;
            if(r_index.type.compareTo("int")!=0){
                System.err.println("About y of 'x[y]': Incompatible types. Required: 'int' Found: "+r_value.type);
                return null;
            }
            if(r_value.type.compareTo("int")!=0){
                System.err.println("Incompatible types. Required: 'int' Found: "+r_value.type);
                return null;
            }
            try{
                int []a = SymbolTable.lookup_a(id);
                if(a==null){
                    System.err.println("NullPointerException: "+id);
                    return null;
                }
                if(a.length<=r_index.iValue){
                    System.err.println("ArrayIndexOutOfBoundsException: "+id+"["+r_index.iValue+"]");
                    return null;
                }
                a[r_index.iValue]=r_value.iValue;
                return null;
            }
            catch (UndefinedIdException e){
                if(SymbolTable.s2tree.get(currentClass).field_names.contains(id)){
                    int idx = SymbolTable.s2tree.get(currentClass).field_names.indexOf(id);
                    Obj tempObj = currentObj.fields.get(idx);
                    if(!tempObj.isInitial){
                        System.err.println("Variable "+currentObj.field_names.get(idx)+" might not have been initialized");
                    }
                    if(tempObj.type.compareTo("int[]")!=0){
                        System.err.println("Opera '[]' cannot be applied to "+tempObj.type);
                        return null;
                    }
                    if(tempObj.aValue==null){
                        System.err.println("NullPointerException: "+id);
                        return null;
                    }
                    if(tempObj.aValue.length<=r_index.iValue){
                        System.err.println("ArrayIndexOutOfBoundsException: "+id+"["+r_index.iValue+"]");
                        return null;
                    }
                    tempObj.aValue[r_index.iValue]=r_value.iValue;
                }
                else{
                    System.err.println("Cannot resolve symbol: "+id);
                }
                return null;
            }
        }
        else if(node.getClass()==A_OpExp.class){
            A_Oper oper = ((A_OpExp)node).oper;
            Absyn left = ((A_OpExp)node).left;
            Absyn right = ((A_OpExp)node).right;
            Result res_l = interpret(left);
            Result res_r = interpret(right);

            if(res_l==null||res_r==null)
                return null;

            Result res = new Result();
            if(oper == A_Oper.and){
                if(res_l.type.compareTo("boolean") == 0 &&
                        res_r.type.compareTo("boolean") == 0){
                    res.type = "boolean";
                    res.bValue = res_l.bValue && res_r.bValue;
                    return res;
                }
                else{
                    if(res_l.type.compareTo("boolean") != 0){
                        System.err.println("About left of '&&': Incompatible types. " +
                                "Required: 'boolean' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("boolean") != 0){
                        System.err.println("About right of '&&': Incompatible types. " +
                                "Required: 'boolean' Found: " + res_r.type );
                    }
                    return null;
                }
            }
            else if(oper == A_Oper.minus){
               if(res_l.type.compareTo("int") == 0 &&
                    res_r.type.compareTo(("int")) == 0){
                   res.type = "int";
                   res.iValue = res_l.iValue - res_r.iValue;
                   return res;
               }
               else{
                   if(res_l.type.compareTo("int") != 0){
                       System.err.println("About left of '-': Incompatible types. " +
                               "Required: 'int' Found: " + res_l.type );
                   }
                   if(res_r.type.compareTo("int") != 0){
                       System.err.println("About right of '-': Incompatible types. " +
                               "Required: 'int' Found: " + res_r.type );
                   }
                   return null;
               }
            }
            else if(oper == A_Oper.plus){
                if(res_l.type.compareTo("int") == 0 &&
                        res_r.type.compareTo("int") == 0){
                    res.type = "int";
                    res.iValue = res_l.iValue + res_r.iValue;
                    return res;
                }
                else{
                    if(res_l.type.compareTo("int") != 0){
                        System.err.println("About left of '+': Incompatible types. " +
                                "Required: 'int' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("int") != 0){
                        System.err.println("About right of '+': Incompatible types. " +
                                "Required: 'int' Found: " + res_r.type );
                    }
                    return null;
                }
            }
            else if(oper == A_Oper.times){
                if(res_l.type.compareTo("int") == 0 &&
                        res_r.type.compareTo(("int")) == 0){
                    res.type = "int";
                    res.iValue = res_l.iValue * res_r.iValue;
                    return res;
                }
                else{
                    if(res_l.type.compareTo("int") != 0){
                        System.err.println("About left of '*': Incompatible types. " +
                                "Required: 'int' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("int") != 0){
                        System.err.println("About right of '*': Incompatible types. " +
                                "Required: 'int' Found: " + res_r.type );
                    }
                    return null;
                }
            }
            else if(oper == A_Oper.lt){
                if(res_l.type.compareTo("int") == 0 &&
                        res_r.type.compareTo(("int")) == 0){
                    res.type = "boolean";
                    res.bValue = res_l.iValue < res_r.iValue;
                    return res;
                }
                else{
                    if(res_l.type.compareTo("int") != 0){
                        System.err.println("About left of '<': Incompatible types. " +
                                "Required: 'int' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("int") != 0){
                        System.err.println("About right of '<': Incompatible types. " +
                                "Required: 'int' Found: " + res_r.type );
                    }
                    return null;
                }
            }
            else{
                System.out.println("Debug: should never come to this point");
                return null;
            }
        }
        else if(node.getClass()==A_ArrayIndex.class){
            //array index
            Result array = interpret(((A_ArrayIndex)node).array);
            Result index = interpret(((A_ArrayIndex)node).index);
            if(array==null||index==null)
                return null;
            if(array.type.compareTo("int[]") != 0){
                System.err.println("About x of 'x[y]': Incompatible types. Required: 'int[]' Found: " +
                        array.type);
                return null;
            }
            else if(index.type.compareTo("int") != 0){
                System.err.println("About y of 'x[y]': Incompatible types. Required: 'int' Found: " +
                        index.type);
                return null;
            }
            else{
                Result res = new Result();
                res.type = "int";
                int index_new = index.iValue;
                if(index.iValue >= array.aValue.length){
                    System.err.println("ArrayIndexOutOfBoundsException: "+index_new);
                    return null;
                }
                res.iValue = array.aValue[index_new];
                return res;
            }
        }
        else if(node.getClass()==A_ArrayLen.class){
            //array length
            Result arr = interpret(((A_ArrayLen)node).array);
            if(arr==null)
                return null;
            if(arr.type.compareTo("int[]") == 0){
                Result res = new Result();
                res.type = "int";
                res.iValue = arr.aValue.length;
                return res;
            }
            else{
                System.err.println("Incompatible types. Required: 'int[]' Found: " + arr.type);
                return null;
            }
        }
        else if(node.getClass()==A_IntExp.class){
            //int expression
            int i = ((A_IntExp)node).i;
            Result res = new Result();
            res.type = "int";
            res.iValue = i;
            return res;
        }
        else if(node.getClass()==A_BoolExp.class){
            //bool expression
            boolean b = ((A_BoolExp)node).b;
            Result res = new Result();
            res.type = "boolean";
            res.bValue = b;
            return res;
        }
        else if(node.getClass()==A_IdExp.class){
            //find id in the table or in current object
            String id = ((A_IdExp)node).id;
            return fetchID(id);
        }
        else if(node.getClass()==A_This.class){
            //this
            Result res = new Result();
            res.type=currentClass;
            res.obj=currentObj;
            return res;
        }
        else if(node.getClass()==A_NewArray.class){
            //new array
            Result exp = interpret(((A_NewArray)node).exp);
            if(exp==null)
                return null;
            if(exp.type.compareTo("int") != 0){
                System.err.println("About x of 'new int[x]' Incompatible types. " +
                        "Required: 'int' Found: " + exp.type);
                return null;
            }
            else {
                Result res = new Result();
                res.type = "int[]";
                res.aValue = new int[exp.iValue];
                return res;
            }
        }
        else if(node.getClass()==A_NewObj.class){
            //new object
            String typename = ((A_NewObj)node).id;
            if(!SymbolTable.s2tree.containsKey(typename)){
                System.err.println("Cannot resolve symbol: "+typename);
                return null;
            }
            Obj o = new Obj(SymbolTable.s2tree.get(typename));
            o.isInitial=true;
            Result res = new Result();
            res.type=typename;
            res.obj=o;
            return res;
        }
        else if(node.getClass()==A_NotExp.class){
            //not expression
            Result exp = interpret(((A_NotExp)node).exp);
            if(exp==null)
                return null;
            Result res = new Result();
            if(exp.type.compareTo("boolean") == 0){
                res.type = "boolean";
                res.bValue = !exp.bValue;
                return res;
            }
            else{
                System.err.println("Opera '!' cannot be applied to " + exp.type);
                return null;
            }
        }
        else{
            System.out.println("Debug: should never come to this point");
            return null;
        }
    }
    private Result fetchID(String id){
        Result res = new Result();
        Card card = SymbolTable.findVariable(id);
        if(!card.isIn){
            List<String> list = SymbolTable.s2tree.get(currentClass).field_names;
            if(list.contains(id)){
                res.type = SymbolTable.s2tree.get(currentClass).types.get(list.indexOf(id));
                Obj obj = currentObj.fields.get(currentObj.field_names.indexOf(id));
                if(!obj.isInitial){
                    System.err.println("Variable "+id+" might not have been initialized");
                    return null;
                }
                if(res.type.compareTo("int")==0){
                    res.iValue=obj.iValue;
                }
                else if(res.type.compareTo("int[]")==0){
                    res.aValue=obj.aValue;
                }
                else if(res.type.compareTo("boolean")==0){
                    res.bValue=obj.bValue;
                }
                else{
                    res.obj=obj;
                }
                return res;
            }
            else{
                System.err.println("Cannot resolve symbol: "+id);
                return null;
            }
        }
        else{
            if(!card.isInitial){
                System.err.println("Variable "+id+" might not have been initialized");
                return null;
            }
            else{
                res.type=card.type;
                if(res.type.compareTo("int")==0){
                    res.iValue=card.iValue;
                }
                else if(res.type.compareTo("int[]")==0){
                    res.aValue=card.aValue;
                }
                else if(res.type.compareTo("boolean")==0){
                    res.bValue=card.bValue;
                }
                else{
                    res.obj=card.obj;
                }
                return res;
            }
        }
    }
}

class Result {
    String type;
    int iValue;
    boolean bValue;
    int []aValue;
    Obj obj;
    String id; //to find usage,search   //u82jew
    public boolean isBase(){
        if(type.compareTo("int")*type.compareTo("int[]")*type.compareTo("boolean")==0){
            return true;
        }
        return false;
    }
}

