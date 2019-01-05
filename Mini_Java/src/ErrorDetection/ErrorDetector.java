package ErrorDetection;

import AbstractSyntax.*;

public class ErrorDetector {
    Absyn root;

    private boolean classvar;
    private String currentClass;
    private String currentMethod;
    public ErrorDetector(Absyn root){
        this.root = root;
    }

    /** Register classes and check invalid declaration
     *  Errors includes:
     *  1.extending undefined class
     *  2.cyclic inheritance
     */
    public void checkClasses() {
        checkClasses(root);
        try {
            SymbolTable.checkNullClass();
        }
        catch (ClassRegisterException e){
            System.out.println(e.getMessage());
        }
    }

    private void checkClasses(Absyn node){
        if(node.getClass()==A_Goal.class){
            checkClasses(((A_Goal)node).a_main);
            Absyn []classes = ((A_Goal)node).classes;
            for(int i=0;i<classes.length;i++){
                checkClasses(classes[i]);
            }
        }
        else if(node.getClass()==A_MainClass.class){
            String id = ((A_MainClass)node).id;
            try {
                SymbolTable.registerClass(id, null);
            }
            catch (ClassRegisterException e){
                System.out.println(e.getMessage());
            }
        }
        else if(node.getClass()==A_ClassDec.class){
            String id = ((A_ClassDec)node).id;
            String superclass = ((A_ClassDec)node).superclass;
            if(superclass.compareTo("")==0)
                superclass=null;
            try {
                SymbolTable.registerClass(id, superclass);
            }
            catch (ClassRegisterException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void recursiveCheck(){
        recursiveCheck(root);
    }

    public void recursiveCheck(Absyn node){
        System.out.println(node.getClass().toString());
        if(node.getClass()==A_Goal.class){
            //the root
            recursiveCheck(((A_Goal)node).a_main);
            Absyn []classes = ((A_Goal)node).classes;
            for(int i=0;i<classes.length;i++){
                recursiveCheck(classes[i]);
            }
        }
        else if(node.getClass()==A_MainClass.class){
            //here we enter the main method of the program
            //todo: register args if we want to support String
            Absyn stmt = ((A_MainClass)node).stmt;
            recursiveCheck(stmt);
        }
        else if(node.getClass()==A_ClassDec.class){
            //class declaration
            String id = ((A_ClassDec)node).id;
            Absyn [] varDecs = ((A_ClassDec)node).varDecs;
            classvar = true;
            currentClass = id;
            for(int i=0;i<varDecs.length;i++){
                recursiveCheck(varDecs[i]);
            }
            Absyn [] methodDecs = ((A_ClassDec)node).methodDecs;
            for(int i=0;i<methodDecs.length;i++){
                recursiveCheck(methodDecs[i]);
            }
        }
        else if(node.getClass()==A_VarDec.class){
            String id = ((A_VarDec)node).id;
            Absyn absyn = ((A_VarDec)node).t;
            int cat = ((A_Type)absyn).category;
            String typename = ((A_Type)absyn).id;
            if(cat==3&&(!SymbolTable.s2tree.containsKey(typename))){
                System.out.println("Cannot resolve symbol: "+typename);
                return;
            }
            if(classvar){
                InheritanceTree temp_tree = SymbolTable.s2tree.get(currentClass);
                temp_tree.append_field(id,cat,typename);
            }
            //todo: check vardec in method during runtime
        }
        else if(node.getClass()==A_MethodDec.class){
            //todo: get id and register
            String id = ((A_MethodDec)node).id;
            Absyn absyn = ((A_MethodDec)node).ret_t;
            if(checkType(absyn)==-1){
                return;
            }
            Absyn []absyns = ((A_MethodDec)node).paras_t;
            for(int i=0;i<absyns.length;i++){
                if(checkType(absyns[i])==-1){
                    return;
                }
            }
            InheritanceTree temp_tree = SymbolTable.s2tree.get(currentClass);
            temp_tree.append_method(id,node);
        }
        else if(node.getClass()==A_Block.class){
            Absyn []stmts = ((A_Block)node).stmts;
            for(int i=0;i<stmts.length;i++){
                recursiveCheck(stmts[i]);
            }
        }
        else if(node.getClass()==A_If.class){
            Absyn exp = ((A_If)node).exp;
            recursiveCheck(exp);
            Absyn branch1 = ((A_If)node).branch1;
            recursiveCheck(branch1);
            Absyn branch2 = ((A_If)node).branch2;
            recursiveCheck(branch2);
        }
        else if(node.getClass()==A_While.class){
            Absyn exp = ((A_While)node).exp;
            recursiveCheck(exp);
            Absyn stmt = ((A_While)node).stmt;
            recursiveCheck(stmt);
        }
        else if(node.getClass()==A_Print.class){
            //todo: check whether int during runtime
            recursiveCheck(((A_Print)node).exp);
        }
        else if(node.getClass()==A_Assign.class){
            //todo: check id in the symbol table during runtime
            recursiveCheck(((A_Assign)node).exp);
        }
        else if(node.getClass()==A_AssignArray.class){
            //todo: check id in the symbol table during runtime
            //todo: check index out of range during runtime
            recursiveCheck(((A_AssignArray)node).index);
            recursiveCheck(((A_AssignArray)node).value);
        }
        else if(node.getClass()==A_OpExp.class){
            recursiveCheck(((A_OpExp)node).left);
            recursiveCheck(((A_OpExp)node).right);
        }
        else if(node.getClass()==A_ArrayIndex.class){
            recursiveCheck(((A_ArrayIndex)node).array);
            recursiveCheck(((A_ArrayIndex)node).index);
        }
        else if(node.getClass()==A_ArrayLen.class){
            recursiveCheck(((A_ArrayLen)node).array);
        }
        else if(node.getClass()==A_CallExp.class){
            //todo: check whether this method exists
            Absyn []absyns = ((A_CallExp)node).paras;
            recursiveCheck(((A_CallExp)node).obj);
            for(int i=0;i<absyns.length;i++){
                recursiveCheck(absyns[i]);
            }
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
            recursiveCheck(((A_NewArray)node).exp);
        }
        else if(node.getClass()==A_NewObj.class){
            String id = ((A_NewObj)node).id;
            if(!SymbolTable.s2tree.containsKey(id)){
                System.out.println("Cannot resolve symbol: "+id);
            }
        }
        else if(node.getClass()==A_NotExp.class){
            recursiveCheck(((A_NotExp)node).exp);
        }
    }

    private int checkType(Absyn node){
        if(node.getClass()!=A_Type.class){
            System.out.println("Debug: should not use checkType");
        }
        int cat = ((A_Type)node).category;
        String typename = ((A_Type)node).id;
        if(cat==3&&(!SymbolTable.s2tree.containsKey(typename))){
            System.out.println("Cannot resolve symbol: "+typename);
            return -1;
        }
        return 0;
    }
}
