package ErrorDetection;

import AbstractSyntax.*;

public class RuntimeInterpreter {
    Absyn root;
    RuntimeInterpreter(Absyn root){
        this.root=root;
    }

    public int interpret(Absyn node){
        if(node.getClass()==A_Goal.class){
            interpret(((A_Goal)node).a_main);
            Absyn []classes = ((A_Goal)node).classes;
            for(int i=0;i<classes.length;i++){
                interpret(classes[i]);
            }
            return 0;
        }
        else if(node.getClass()==A_MainClass.class){
            Absyn stmt = ((A_MainClass)node).stmt;
            int res = interpret(stmt);
            return res;
        }
        else if(node.getClass()==A_ClassDec.class){

        }
        else if(node.getClass()==A_VarDec.class){

        }
        else if(node.getClass()==A_MethodDec.class){

        }
        else if(node.getClass()==A_Block.class){

        }
        else if(node.getClass()==A_If.class){

        }
        else if(node.getClass()==A_While.class){

        }
        else if(node.getClass()==A_Print.class){
            int res = interpret(((A_Print)node).exp);
            System.out.println(res);
            return 0;
        }
        else if(node.getClass()==A_Assign.class){

        }
        else if(node.getClass()==A_AssignArray.class){

        }
        else if(node.getClass()==A_OpExp.class){
            A_Oper oper = ((A_OpExp)node).oper;
            Absyn left = ((A_OpExp)node).left;
            Absyn right = ((A_OpExp)node).right;
            int res_l = interpret(left);
            int res_r = interpret(right);
            if(oper == A_Oper.and){
                return res_l & res_r;
            }
            else if(oper == A_Oper.minus){
                return res_l - res_r;
            }
            else if(oper == A_Oper.plus){
                return res_l + res_r;
            }
            else if(oper == A_Oper.times){
                return res_l * res_r;
            }
            else if(oper == A_Oper.lt){
                return res_l + res_r;
            }
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
        else{
            return 0;
        }
    }
}