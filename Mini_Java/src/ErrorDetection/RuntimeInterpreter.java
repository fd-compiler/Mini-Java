package ErrorDetection;

import AbstractSyntax.*;

import java.util.HashSet;
import java.util.Set;

public class RuntimeInterpreter {
    Absyn root;

    private boolean classvar;
    private A_MethodDec currentParameters;
    private String owner;
    private String currentClass;
    private String currentMethod;
    private Obj currentObj;
    public RuntimeInterpreter(Absyn root){
        this.root=root;
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
            Absyn [] methodDecs = ((A_ClassDec)node).methodDecs;
            for(int i=0;i<methodDecs.length;i++){
                interpret(methodDecs[i]);
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
                res.id = id;
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

        }
        else if(node.getClass()==A_CallExp.class){
            Result r = interpret(((A_CallExp)node).obj);
            String m = ((A_CallExp)node).method;
            if(r.isBase()){
                System.err.println("Cannot resolve symbol: "+m);
                return null;//todo: null
            }
            if(r.obj==null){
                System.err.println("Variable "+r.id+" might not have been initialized");//todo: might be wrong
                return null;
            }
            if(!r.obj.type.methods.contains(m)){
                System.err.println("Cannot resolve symbol: "+m);
                return null;
            }
            else{
                InheritanceTree temp_tree = r.obj.type;
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
                //todo:delete locals
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
                        SymbolTable.insert_c(pnames[i],temp_res[i].obj);
                    }
                }


            }
        }
        else if(node.getClass()==A_Block.class){
            //block statement
            Absyn []absyns = ((A_Block)node).stmts;
            for(int i=0;i<absyns.length;i++){
                interpret(absyns[i]);
            }
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
                System.err.println("Incompatible types, Required: boolean, Found: "+res.type);
            }
            while(res.bValue){
                System.out.println("loop");
                interpret(((A_While)node).stmt);
                res = interpret(((A_While)node).exp);
            }
        }
        else if(node.getClass()==A_Print.class){
            //print statement
            Result res = interpret(((A_Print)node).exp);
            if(res.type.compareTo("int")!=0){
                System.err.println("Incompatible types, Required: int, Found: "+res.type);
            }else{
                System.out.println(res.iValue);
            }
        }
        else if(node.getClass()==A_Assign.class){
            String id = ((A_Assign)node).id;
            Result res = interpret(((A_Assign)node).exp);
            //todo:

        }
        else if(node.getClass()==A_AssignArray.class){

        }
        else if(node.getClass()==A_OpExp.class){

        }
        else if(node.getClass()==A_ArrayIndex.class){

        }
        else if(node.getClass()==A_ArrayLen.class){

        }

        else if(node.getClass()==A_IntExp.class){

        }
        else if(node.getClass()==A_BoolExp.class){

        }
        else if(node.getClass()==A_IdExp.class){

        }
        else if(node.getClass()==A_This.class){

        }
        else if(node.getClass()==A_NewArray.class){

        }
        else if(node.getClass()==A_NewObj.class){

        }
        else if(node.getClass()==A_NotExp.class){

        }
        else{
            return null;//todo: delete this
        }
        return null;
    }
    public void parameterError(){

    }
}

class Result {
    String type;
    int iValue;
    boolean bValue;
    int []aValue;
    Obj obj;
    String id;
    public boolean isBase(){
        if(type.compareTo("int")*type.compareTo("int[]")*type.compareTo("boolean")==0){
            return true;
        }
        return false;
    }
}

