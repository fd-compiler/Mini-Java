package ErrorDetection;

import java.util.LinkedList;
import java.util.List;

public class ScopeStack {
    static List<List<String>> stack;
    static List<List<String>> stack_b;
    static List<List<String>> stack_a;
    static List<List<String>> stack_c;

    static List<String> buffer;
    static List<String> buffer_a;
    static List<String> buffer_b;
    static List<String> buffer_c;

    static {
        stack=new LinkedList<>();
        stack_a=new LinkedList<>();
        stack_b=new LinkedList<>();
        stack_c=new LinkedList<>();

        buffer = new LinkedList<>();
        buffer_a = new LinkedList<>();
        buffer_b = new LinkedList<>();
        buffer_c = new LinkedList<>();
    }
    public static void pushBuffer(){
        stack.add(buffer);
        stack_a.add(buffer_a);
        stack_b.add(buffer_b);
        stack_c.add(buffer_c);
        buffer = new LinkedList<>();
    }
    public static void popGroup(){
        if(stack.size()==0){
            System.out.println("Debug: pop an empty stack");
            return;
        }
        List<String> s = stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        for(int i=0;i<s.size();i++){
            SymbolTable.pop(s.get(i));
        }
        //todo:
    }
    public static void addInt(String key){
        buffer.add(key);
    }
    public static void addBool(String key){
        buffer_b.add(key);
    }

    //todo:
}
