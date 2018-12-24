package Parser;

import AbstractSyntax.*;

public class astGenerator extends miniJavaBaseVisitor<Absyn> {



    //goal
    @Override public Absyn visitGoal(miniJavaParser.GoalContext ctx) {
        int n = ctx.cd;
        Absyn mainC = visit(ctx.mainClass());
        Absyn [] classes = new A_ClassDec[n];
        for(int i=0;i<n;i++){
            classes[i] = visit(ctx.classDec(i));
        }
        Absyn goal = new A_Goal(mainC,classes);
        //int shit =((A_MainClass)(((A_Goal) goal).a_main)).standout;
        //System.out.println(shit);
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

    @Override public Absyn visitBlock(miniJavaParser.BlockContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitIf(miniJavaParser.IfContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitWhile(miniJavaParser.WhileContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitPrintExpr(miniJavaParser.PrintExprContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitAssign(miniJavaParser.AssignContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitAssignArray(miniJavaParser.AssignArrayContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitParens(miniJavaParser.ParensContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitCallMember(miniJavaParser.CallMemberContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitIndexArray(miniJavaParser.IndexArrayContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitFalse(miniJavaParser.FalseContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitThis(miniJavaParser.ThisContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitLength(miniJavaParser.LengthContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitNewArray(miniJavaParser.NewArrayContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitInt(miniJavaParser.IntContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitALOp(miniJavaParser.ALOpContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitNotExpr(miniJavaParser.NotExprContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitNewObject(miniJavaParser.NewObjectContext ctx) { return visitChildren(ctx); }

    @Override public Absyn visitTrue(miniJavaParser.TrueContext ctx) { return visitChildren(ctx); }

    //expression -> ID
    @Override public Absyn visitId(miniJavaParser.IdContext ctx) {
        //todo: check the symbol table
        String id = ctx.ID().getText();
        Absyn id_node = new A_IdExp(id);
        return id_node;
    }
}
