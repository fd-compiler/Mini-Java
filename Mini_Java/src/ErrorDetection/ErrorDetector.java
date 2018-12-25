package ErrorDetection;

import AbstractSyntax.*;

public class ErrorDetector {
    Absyn root;
    public ErrorDetector(Absyn root){
        this.root = root;
    }

    public void recursiveCheck(){
        recursiveCheck(root);
    }

    public void recursiveCheck(Absyn node){
        if(node.getClass()==A_Goal.class){
            recursiveCheck(((A_Goal)node).a_main);
            Absyn []classes = ((A_Goal)node).classes;
            for(int i=0;i<classes.length;i++){
                recursiveCheck(classes[i]);
            }
        }
        else if(node.getClass()==A_MainClass.class){
            //here we enter the main method of the program
            //todo: get id and register in the Inheritance Tree's forest
            //todo: register args
            Absyn stmt = ((A_MainClass)node).stmt;
            recursiveCheck(stmt);
        }
        else if(node.getClass()==A_ClassDec.class){
            //todo: get id and register this class, check if extends
            Absyn [] varDecs = ((A_ClassDec)node).varDecs;
            for(int i=0;i<varDecs.length;i++){
                recursiveCheck(varDecs[i]);
            }
            Absyn [] methodDecs = ((A_ClassDec)node).methodDecs;
            for(int i=0;i<methodDecs.length;i++){
                recursiveCheck(methodDecs[i]);
            }
        }
        else if(node.getClass()==A_VarDec.class){
            //todo: register in symbol table
            System.out.println("var dec");
        }
        else if(node.getClass()==A_MethodDec.class){
            //todo: get id and register
            System.out.println("Method Declare");
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
            //todo: check whether int
            System.out.println("print");
        }
        else if(node.getClass()==A_Assign.class){
            String id = ((A_Assign)node).id;
            //todo: check id in the symbol table
            //if in the table
            System.out.println("assign");
        }
        else if(node.getClass()==A_AssignArray.class){
            String id = ((A_AssignArray)node).id;
            //todo: check id in the symbol table
            System.out.println("assign array");
            //todo: check index out of range
        }
        else if(node.getClass()==A_OpExp.class){

        }
        else if(node.getClass()==A_ArrayIndex.class){

        }
        else if(node.getClass()==A_ArrayLen.class){

        }
        else if(node.getClass()==A_CallExp.class){

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
    }
}
