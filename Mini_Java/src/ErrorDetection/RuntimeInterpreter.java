package ErrorDetection;

import AbstractSyntax.*;

public class RuntimeInterpreter {
    Absyn root;
    RuntimeInterpreter(Absyn root){
        this.root=root;
    }

    public Result interpret(Absyn node){
        if(node.getClass()==A_Goal.class){
            interpret(((A_Goal)node).a_main);
            Absyn []classes = ((A_Goal)node).classes;
            for(int i=0;i<classes.length;i++){
                interpret(classes[i]);
            }
            return null;
        }
        else if(node.getClass()==A_MainClass.class){
            Absyn stmt = ((A_MainClass)node).stmt;
            Result res = interpret(stmt);
            return res;
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

        }
        else if(node.getClass()==A_MethodDec.class){

        }
        else if(node.getClass()==A_Block.class){

        }
        else if(node.getClass()==A_If.class){
            boolean res = interpret(((A_If)node).exp);
            if(res)
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
                        System.out.println("About left of '&&': Incompatible type. " +
                                "Required: 'boolean' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("boolean") != 0){
                        System.out.println("About right of '&&': Incompatible type. " +
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
                       System.out.println("About left of '-': Incompatible type. " +
                               "Required: 'int' Found: " + res_l.type );
                   }
                   if(res_r.type.compareTo("int") != 0){
                       System.out.println("About right of '-': Incompatible type. " +
                               "Required: 'int' Found: " + res_r.type );
                   }
                   return null;
               }
            }
            else if(oper == A_Oper.plus){
                if(res_l.type.compareTo("int") == 0 &&
                        res_r.type.compareTo(("int")) == 0){
                    res.type = "int";
                    res.iValue = res_l.iValue + res_r.iValue;
                    return res;
                }
                else{
                    if(res_l.type.compareTo("int") != 0){
                        System.out.println("About left of '+': Incompatible type. " +
                                "Required: 'int' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("int") != 0){
                        System.out.println("About right of '+': Incompatible type. " +
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
                        System.out.println("About left of '*': Incompatible type. " +
                                "Required: 'int' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("int") != 0){
                        System.out.println("About right of '*': Incompatible type. " +
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
                        System.out.println("About left of '<': Incompatible type. " +
                                "Required: 'int' Found: " + res_l.type );
                    }
                    if(res_r.type.compareTo("int") != 0){
                        System.out.println("About right of '<': Incompatible type. " +
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
                System.out.println("About x of 'x[y]': Incompatible type. Required: 'int[]' Found: " +
                        array.type);
                return null;
            }
            else if(index.type.compareTo("int") != 0){
                System.out.println("About y of 'x[y]': Incompatible type. Required: 'int' Found: " +
                        index.type);
                return null;
            }
            else{
                Result res = new Result();
                res.type = "int";
                int index_new = index.iValue;
                if(index.iValue >= array.aValue.length){
                    System.out.println("Warning: index bigger than length of array, have done with %");
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
                System.out.println("Incompatible type. Required: 'int[]' Found: " + arr.type);
                return null;
            }
        }
        else if(node.getClass()==A_CallExp.class){

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
            return 0;
        }
        else if(node.getClass()==A_This.class){

        }
        else if(node.getClass()==A_NewArray.class){

        }
        else if(node.getClass()==A_NewObj.class){

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
                System.out.println("Opera '!' cannot be applied to " + exp.type);
                return null;
            }
        }
        else{
            return 0;
        }
    }
}

class Result {
    String type;
    int iValue;
    boolean bValue;
    int []aValue;
    Obj obj;
}