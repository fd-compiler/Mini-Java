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
            return 0;
        }

        //...
        //else if(){}
        //...

        else{
            return 0;
        }
    }
}
