package ErrorDetection;

import AbstractSyntax.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RuntimeInterpreter {
    Absyn root;
    private boolean classvar;
    private String currentClass;
    private Obj currentObj;

    List<String> classStack = new LinkedList<>();
    List<Obj> objStack = new LinkedList<>();

    public RuntimeInterpreter(Absyn root){
        this.root=root;
    }

    public void interpret(){
        interpret(root);
    }

    public Result interpret(Absyn node){
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
            Absyn stmt = ((A_MainClass)node).stmt;
            interpret(stmt);
            return null;
        }
        else if(node.getClass()==A_ClassDec.class){
            String id = ((A_ClassDec)node).id;
            Absyn [] varDecs = ((A_ClassDec)node).varDecs;
            classvar = true;
            currentClass = id;
            for(int i=0;i<varDecs.length;i++){
                interpret(varDecs[i]);
            }
        }
        else if(node.getClass()==A_VarDec.class){
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
                    case 3:break;
                    default:
                        System.out.println("Debug: Undefined type: "+cat);
                }
                res.type=typename;
                res.id = id; //u82jew
                return res;
            }
        }
        else if(node.getClass()==A_MethodDec.class){
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
                if(var.type.compareTo("int")==0){
                    SymbolTable.insert(var.id,0);//todo check initialization //hp
                }
                else if(var.type.compareTo("int[]")==0){
                    SymbolTable.insert_a(var.id,null);
                }
                else if(var.type.compareTo("boolean")==0){
                    SymbolTable.insert_b(var.id,true);
                }
                else{
                    SymbolTable.insert_c(var.id,null);
                }
            }
            Absyn []stmts = ((A_MethodDec)node).stmts;
            for(int i=0;i<stmts.length;i++){
                interpret(stmts[i]);
            }
            Result res = interpret(((A_MethodDec)node).ret);
            return res;
        }
        else if(node.getClass()==A_CallExp.class){
            Result r = interpret(((A_CallExp)node).obj);
            String m = ((A_CallExp)node).method;
            if(r.isBase()){
                System.err.println("Cannot resolve symbol: "+m);
                return null;//todo: null: check every interpret call
            }
            if(r.obj==null){
                //u82jew
                System.err.println("Variable "+r.id+" might not have been initialized");//todo: might be wrong
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
                    temp_res[i] = interpret(call_p[i]);
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
                currentClass = r.obj.tree.id;
                //todo:delete locals
                //pass parameters
                for(int i=0;i<temp_res.length;i++){
                    if(temp_res[i].type.compareTo("int")==0){
                        SymbolTable.insert(pnames[i],temp_res[i].iValue);//todo: check every insert into table, caution uninitialized variable
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
                //todo: restore locals
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
            if (res.type.compareTo("boolean") != 0) {
                System.err.println("Incompatible types. Required: 'boolean', Found: "+res.type);
            }
            while(res.bValue){
                interpret(((A_While)node).stmt);
                res = interpret(((A_While)node).exp);
            }
        }
        else if(node.getClass()==A_Print.class){
            //print statement
            Result res = interpret(((A_Print)node).exp);
            if(res.type.compareTo("int")!=0){
                System.err.println("Incompatible types. Required: 'int', Found: "+res.type);
            }else{
                System.out.println(res.iValue);
            }
        }
        else if(node.getClass()==A_Assign.class){
            String id = ((A_Assign)node).id;
            Result res = interpret(((A_Assign)node).exp);
            //todo: initialized //hp
            Card card = SymbolTable.findVariable(id);
            if(!card.isIn){
                List<String> list = SymbolTable.s2tree.get(currentClass).field_names;
                if(list.contains(id)){
                    String typename = SymbolTable.s2tree.get(currentClass).types.get(list.indexOf(id));
                    //todo: where to define current obj
                    //todo: where to define current class
                    if(res.type.compareTo(typename)!=0){
                        System.err.println("Incompatible types. Required: '"+ typename+"' Found: "+res.type);
                    }else{
                        currentObj.fields.set(currentObj.field_names.indexOf(id),res.obj);
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
            String id = ((A_AssignArray)node).id;
            Result r_index = interpret(((A_AssignArray)node).index);
            Result r_value = interpret(((A_AssignArray)node).value);
            if((r_index.type.compareTo("int")!=0)||(r_value.type.compareTo("int")!=0)){
                System.err.println("About y of 'x[y]': Incompatible types. Required: 'int' ");
                return null;
            }
            try{
                int []a = SymbolTable.lookup_a(id);
                if(a==null){
                    System.err.println("NullPointerException: "+id);
                    return null;
                }
                if(a.length-1<r_index.iValue){
                    System.err.println("IndexOutOfRangeException: "+id+"["+r_index.iValue+"]");
                    return null;
                }
                a[r_index.iValue]=r_value.iValue;
                return null;
            }
            catch (UndefinedIdException e){
                if(SymbolTable.s2tree.get(currentClass).field_names.contains(id)){
                    int idx = SymbolTable.s2tree.get(currentClass).field_names.indexOf(id);
                    Obj tempObj = currentObj.fields.get(currentObj.field_names.indexOf(id));
                    if(tempObj.aValue==null){
                        System.err.println("NullPointerException: "+id);
                        return null;
                    }
                    if(tempObj.aValue.length-1<r_index.iValue){
                        System.err.println("IndexOutOfRangeException: "+id+"["+r_index.iValue+"]");
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
        }
        else if(node.getClass()==A_ArrayIndex.class){
            Result array = interpret(((A_ArrayIndex)node).array);
            Result index = interpret(((A_ArrayIndex)node).index);
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
                    System.err.println("Warning: index bigger than length of array, have done with %");
                    index_new = index.iValue % array.aValue.length;
                }
                res.iValue = array.aValue[index_new];
                return res;
            }
        }
        else if(node.getClass()==A_ArrayLen.class){
            Result arr = interpret(((A_ArrayLen)node).array);
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
            int i = ((A_IntExp)node).i;
            Result res = new Result();
            res.type = "int";
            res.iValue = i;
            return res;
        }
        else if(node.getClass()==A_BoolExp.class){
            boolean b = ((A_BoolExp)node).b;
            Result res = new Result();
            res.type = "boolean";
            res.bValue = b;
            return res;
        }
        else if(node.getClass()==A_IdExp.class){
            String id = ((A_IdExp)node).id;
            Result res = new Result();
            Card card = SymbolTable.findVariable(id);
            if(!card.isIn){
                List<String> list = SymbolTable.s2tree.get(currentClass).field_names;
                if(list.contains(id)){
                    //todo: class var initialized ?
                    String typename = SymbolTable.s2tree.get(currentClass).types.get(list.indexOf(id));
                    res.type=typename;
                    Obj obj = currentObj.fields.get(currentObj.field_names.indexOf(id));
                    res.iValue=obj.iValue;
                    res.aValue=obj.aValue;
                    res.bValue=obj.bValue;
                    res.obj=obj;
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
                    res.iValue=card.iValue;
                    res.aValue=card.aValue;
                    res.bValue=card.bValue;
                    res.obj=card.obj;
                    return res;
                }
            }
        }
        else if(node.getClass()==A_This.class){
            Result res = new Result();
            //todo:
            res.type=currentClass;
            res.obj=currentObj;
            return res;
        }
        else if(node.getClass()==A_NewArray.class){
            Result exp = interpret(((A_NewArray)node).exp);
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
            String typename = ((A_NewObj)node).id;
            if(!SymbolTable.s2tree.containsKey(typename)){
                System.err.println("Cannot resolve symbol: "+typename);
            }
            Obj o = new Obj(SymbolTable.s2tree.get(typename));
            Result res = new Result();
            res.type=typename;
            res.obj=o;
            return res;
        }
        else if(node.getClass()==A_NotExp.class){
            Result exp = interpret(((A_NotExp)node).exp);
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
            return null;//todo: delete this
        }
        return null;
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

