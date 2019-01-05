package Parser;

import AbstractSyntax.*;

public class astGenerator extends miniJavaBaseVisitor<Absyn> {

    public Absyn root;

    //goal
    @Override public Absyn visitGoal(miniJavaParser.GoalContext ctx) {
        int n = ctx.cd;
        Absyn mainC = visit(ctx.mainClass());
        Absyn [] classes = new A_ClassDec[n];
        for(int i=0;i<n;i++){
            classes[i] = visit(ctx.classDec(i));
        }
        Absyn goal = new A_Goal(mainC,classes);
        root = goal;
        return goal;
    }

    //mainClass
    @Override public Absyn visitMainClass(miniJavaParser.MainClassContext ctx) {
        String classname = ctx.ID(0).getText();
        String args = ctx.ID(1).getText();
        Absyn mainC = new A_MainClass(classname,args,visit(ctx.statement()));
        return mainC;
    }

    //classDec
    @Override public Absyn visitClassDec(miniJavaParser.ClassDecContext ctx) {
        String id = ctx.ID(0).getText();
        String superclass = "";
        if(ctx.ext)
            superclass = ctx.ID(1).getText();
        Absyn []varDecs = new Absyn[ctx.vd];
        for(int i=0;i<ctx.vd;i++){
            varDecs[i] = visit(ctx.varDec(i));
        }
        Absyn []methodDecs = new Absyn[ctx.md];
        for(int i=0;i<ctx.md;i++){
            methodDecs[i] = visit(ctx.methodDec(i));
        }
        Absyn classDec = new A_ClassDec(id,superclass,varDecs,methodDecs);
        return classDec;
    }

    //varDec
    @Override public Absyn visitVarDec(miniJavaParser.VarDecContext ctx) {
        //todo: insert into symbol table
        Absyn type = visit(ctx.type());
        String id = ctx.ID().getText();
        Absyn varDec = new A_VarDec(type,id);
        return varDec;
    }

    //methodDec
    @Override public Absyn visitMethodDec(miniJavaParser.MethodDecContext ctx) {
        String id = ctx.ID(0).getText();
        Absyn ret_t = visit(ctx.type(0));

        Absyn [] paras_t = new Absyn[ctx.pa];
        String[] paras = new String[ctx.pa];
        for(int i=0;i<ctx.pa;i++){
            paras_t[i] = visit(ctx.type(i+1));
            paras[i] = ctx.ID(i+1).getText();
        }
        Absyn[] varDecs = new Absyn[ctx.vd];
        for(int i=0;i<ctx.vd;i++){
            varDecs[i] = visit(ctx.varDec(i));
        }
        Absyn[] stmts = new Absyn[ctx.st];
        for(int i=0;i<ctx.st;i++){
            stmts[i] = visit(ctx.statement(i));
        }
        Absyn ret = visit(ctx.expression());
        Absyn methodDec = new A_MethodDec(ret_t,id,paras_t,paras,varDecs,stmts,ret);
        return methodDec;
    }

    //type
    @Override public Absyn visitType(miniJavaParser.TypeContext ctx) {
        String id = "";
        if(ctx.t==3){
            id=ctx.ID().getText();
        }
        Absyn type_node = new A_Type(ctx.t,id);
        return type_node;
    }

    //block
    @Override public Absyn visitBlock(miniJavaParser.BlockContext ctx) {
        Absyn stmts[] = new Absyn[ctx.st];
        for(int i=0;i<ctx.st;i++){
            stmts[i] = visit(ctx.statement(i));
        }
        Absyn block = new A_Block(stmts);
        return block;
    }

    //if
    @Override public Absyn visitIf(miniJavaParser.IfContext ctx) {
        Absyn exp = visit(ctx.expression());
        Absyn branch1 = visit(ctx.statement(0));
        Absyn branch2 = visit(ctx.statement(1));
        Absyn if_node = new A_If(exp,branch1,branch2);
        return if_node;
    }

    //while
    @Override public Absyn visitWhile(miniJavaParser.WhileContext ctx) {
        Absyn exp = visit(ctx.expression());
        Absyn stmt = visit(ctx.statement());
        Absyn while_node = new A_While(exp,stmt);
        return while_node;
    }

    //print
    @Override public Absyn visitPrintExpr(miniJavaParser.PrintExprContext ctx) {
        Absyn exp = visit(ctx.expression());
        Absyn print_node = new A_Print(exp);
        return print_node;
    }

    //assign
    @Override public Absyn visitAssign(miniJavaParser.AssignContext ctx) {
        //todo: change value in the table
        String id = ctx.ID().getText();
        Absyn exp = visit(ctx.expression());
        Absyn assign = new A_Assign(id, exp);
        return assign;
    }

    //assign array
    @Override public Absyn visitAssignArray(miniJavaParser.AssignArrayContext ctx) {
        //todo: change value in the table
        String id = ctx.ID().getText();
        Absyn index = visit(ctx.expression(0));
        Absyn value = visit(ctx.expression(1));
        Absyn assign_array = new A_AssignArray(id,index,value);
        return assign_array;
    }

    //parenthesis
    @Override public Absyn visitParens(miniJavaParser.ParensContext ctx) {
        return visit(ctx.expression());
    }

    //call member
    @Override public Absyn visitCallMember(miniJavaParser.CallMemberContext ctx) {
        Absyn obj = visit(ctx.expression(0));
        String method = ctx.ID().getText();
        Absyn[] paras = new Absyn[ctx.pa];
        if(ctx.pa!=0){
            for(int i=0;i<ctx.pa;i++){
                paras[i]=visit(ctx.expression(i+1));
            }
        }
        Absyn call_node = new A_CallExp(obj,method,paras);
        return call_node;
    }

    //index array
    @Override public Absyn visitIndexArray(miniJavaParser.IndexArrayContext ctx) {
        Absyn exp = visit(ctx.expression(0));
        Absyn idx = visit(ctx.expression(1));
        Absyn indexArray = new A_ArrayIndex(exp,idx);
        return indexArray;
    }

    //false
    @Override public Absyn visitFalse(miniJavaParser.FalseContext ctx) {
        Absyn f = new A_BoolExp(false);
        return f;
    }

    //this
    @Override public Absyn visitThis(miniJavaParser.ThisContext ctx) {
        //todo: implement 'this'
        Absyn this_node = new A_This();
        return this_node;
    }

    //length
    @Override public Absyn visitLength(miniJavaParser.LengthContext ctx) {
        Absyn exp = visit(ctx.expression());
        Absyn length_node = new A_ArrayLen(exp);
        return length_node;
    }

    //new array
    @Override public Absyn visitNewArray(miniJavaParser.NewArrayContext ctx) {
        //todo: implement new array
        Absyn exp = visit(ctx.expression());
        Absyn newArray = new A_NewArray(exp);
        return newArray;
    }

    //expression -> int
    @Override public Absyn visitInt(miniJavaParser.IntContext ctx) {
        int i = Integer.valueOf(ctx.INT().getText());
        Absyn int_node = new A_IntExp(i);
        return int_node;
    }

    //algorithm logical operation
    @Override public Absyn visitALOp(miniJavaParser.ALOpContext ctx) {
        Absyn left = visit(ctx.expression(0));
        Absyn right = visit(ctx.expression(1));
        A_Oper op = A_Oper.and;
        switch (ctx.op.getType()){
            case miniJavaParser.AND: op = A_Oper.and; break;
            case miniJavaParser.MUL: op = A_Oper.times; break;
            case miniJavaParser.ADD: op = A_Oper.plus; break;
            case miniJavaParser.SUB: op = A_Oper.minus; break;
            case miniJavaParser.LT:  op = A_Oper.lt; break;
            default:
                System.out.println("error");
        }
        Absyn alop = new A_OpExp(op,left,right);
        return alop;
    }

    //!expression
    @Override public Absyn visitNotExpr(miniJavaParser.NotExprContext ctx) {
        Absyn exp = visit(ctx.expression());
        Absyn notExp = new A_NotExp(exp);
        return notExp;
    }

    //new object
    @Override public Absyn visitNewObject(miniJavaParser.NewObjectContext ctx) {
        String id = ctx.ID().getText();
        Absyn newObj = new A_NewObj(id);
        return newObj;
    }

    //true
    @Override public Absyn visitTrue(miniJavaParser.TrueContext ctx) {
        Absyn t = new A_BoolExp(true);
        return t;
    }

    //expression -> ID
    @Override public Absyn visitId(miniJavaParser.IdContext ctx) {
        //todo: check the symbol table
        String id = ctx.ID().getText();
        Absyn id_node = new A_IdExp(id);
        return id_node;
    }
}
