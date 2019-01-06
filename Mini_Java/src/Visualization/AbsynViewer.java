package Visualization;

import AbstractSyntax.*;

public class AbsynViewer {
    private Absyn root;
    private boolean small;
    public AbsynViewer(Absyn root){
        this.root=root;
    }
    public void view(boolean small){
        this.small=small;
        view(0,root);
    }
    private void view(int indent,Absyn node){
        for(int j=0;j<indent-1;j++){
            System.out.print("|   ");
        }
        if(indent>0){
            System.out.print("|-- ");
        }
        if(node.getClass()==A_Goal.class){
            System.out.println("A_Goal");
            view(indent+1,((A_Goal)node).a_main);
            for(int i=0;i<((A_Goal)node).classes.length;i++)
            view(indent+1,((A_Goal)node).classes[i]);
        }
        else if(node.getClass()==A_MainClass.class){
            System.out.print("A_MainClass: ");
            System.out.println(((A_MainClass)node).id);
            view(indent+1,((A_MainClass)node).stmt);
        }
        else if(node.getClass()==A_ClassDec.class){
            System.out.print("A_ClassDec: ");
            System.out.println(((A_ClassDec)node).id);
            for(int i=0;i<((A_ClassDec)node).varDecs.length;i++){
                view(indent+1,((A_ClassDec)node).varDecs[i]);
            }
            for(int i=0;i<((A_ClassDec)node).methodDecs.length;i++){
                view(indent+1,((A_ClassDec)node).methodDecs[i]);
            }
        }
        else if(node.getClass()==A_VarDec.class){
            System.out.print("A_VarDec: ");
            Absyn absyn = ((A_VarDec)node).t;
            System.out.print(((A_Type)absyn).toString());
            System.out.println(" "+((A_VarDec)node).id);
        }
        else if(node.getClass()==A_MethodDec.class){
            System.out.print("A_MethodDec: ");
            System.out.print(((A_MethodDec)node).ret_t.toString());
            System.out.print(" "+((A_MethodDec)node).id+" (");
            for(int i=0;i<((A_MethodDec)node).paras_t.length-1;i++){
                System.out.print(((A_MethodDec)node).paras_t[i].toString()+", ");
            }
            if(((A_MethodDec)node).paras_t.length>0){
                System.out.print(((A_MethodDec)node).paras_t[0].toString());
            }
            System.out.println(")");
            for(int i=0;i<((A_MethodDec)node).varDecs.length;i++){
                view(indent+1,((A_MethodDec)node).varDecs[i]);
            }
            for(int i=0;i<((A_MethodDec)node).stmts.length;i++){
                view(indent+1,((A_MethodDec)node).stmts[i]);
            }
        }
        else if(node.getClass()==A_Block.class){
            System.out.println("A_Block");
            for(int i=0;i<((A_Block)node).stmts.length;i++){
                view(indent+1,((A_Block)node).stmts[i]);
            }
        }
        else if(node.getClass()==A_If.class){
            System.out.println("A_If");
            view(indent+1,((A_If)node).exp);
            view(indent+1,((A_If)node).branch1);
            view(indent+1,((A_If)node).branch2);
        }
        else if(node.getClass()==A_While.class){
            System.out.println("A_While");
            view(indent+1,((A_While)node).exp);
            view(indent+1,((A_While)node).stmt);
        }
        else if(node.getClass()==A_Print.class){
            System.out.println("A_Print");
            view(indent+1,((A_Print)node).exp);
        }
        else if(node.getClass()==A_Assign.class){
            System.out.println("A_Assign: "+((A_Assign)node).id+"=exp");
            view(indent+1,((A_Assign)node).exp);
        }
        else if(node.getClass()==A_AssignArray.class){
            System.out.println("A_Assign: "+((A_AssignArray)node).id+"[exp]=exp");
            view(indent+1,((A_AssignArray)node).index);
            view(indent+1,((A_AssignArray)node).value);
        }
        else if(small){
            System.out.println("some expression");
        }
        else if(node.getClass()==A_OpExp.class){
            System.out.println("A_OpExp: "+((A_OpExp)node).oper.toString());
            view(indent+1,((A_OpExp)node).left);
            view(indent+1,((A_OpExp)node).right);
        }
        else if(node.getClass()==A_ArrayIndex.class){
            System.out.println("A_ArrayIndex");
            view(indent+1,((A_ArrayIndex)node).array);
            view(indent+1,((A_ArrayIndex)node).index);
        }
        else if(node.getClass()==A_ArrayLen.class){
            System.out.println("A_ArrayLen");
            view(indent+1,((A_ArrayLen)node).array);
        }
        else if(node.getClass()==A_CallExp.class){
            System.out.println("A_CallExp: "+((A_CallExp)node).method);
            view(indent+1,((A_CallExp)node).obj);
            for(int i=0;i<((A_CallExp)node).paras.length;i++){
                view(indent+1,((A_CallExp)node).paras[i]);
            }
        }
        else if(node.getClass()==A_IntExp.class){
            System.out.println("A_IntExp: "+((A_IntExp)node).i);
        }
        else if(node.getClass()==A_BoolExp.class){
            System.out.println("A_BoolExp: "+((A_BoolExp)node).b);
        }
        else if(node.getClass()==A_IdExp.class){
            System.out.println("A_IdExp: "+((A_IdExp)node).id);
        }
        else if(node.getClass()==A_This.class){
            System.out.println("A_This");
        }
        else if(node.getClass()==A_NewArray.class){
            System.out.println("A_NewArray");
            view(indent+1,((A_NewArray)node).exp);
        }
        else if(node.getClass()==A_NewObj.class){
            System.out.println("A_NewObj: "+((A_NewObj)node).id+"()");
        }
        else if(node.getClass()==A_NotExp.class){
            System.out.println("A_NotExp");
            view(indent+1,((A_NotExp)node).exp);
        }
        else{
        }
    }
}
