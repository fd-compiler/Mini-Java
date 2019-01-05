package ErrorDetection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SymbolTable {
    private static final int SIZE = 200;
    public static bucket[] table = new bucket[SIZE];       // used for int
    public static bucket_b[] table_b = new bucket_b[SIZE]; // used for boolean
    public static bucket_a[] table_a = new bucket_a[SIZE]; // used for array
    public static bucket_c[] table_c = new bucket_c[SIZE]; // used for objects

    public static Map<String, InheritanceTree> s2tree = new HashMap<>();

    static{
        InheritanceTree t_int = new InheritanceTree("int");
        s2tree.put("int",t_int);
        InheritanceTree t_array = new InheritanceTree("int[]");
        s2tree.put("int[]",t_array);
        InheritanceTree t_bool = new InheritanceTree("boolean");
        s2tree.put("boolean",t_bool);
    }

    public static void checkNullClass() throws ClassRegisterException{
        Iterator<String> iterator = s2tree.keySet().iterator();
        while(iterator.hasNext()){
            InheritanceTree temp_tree = s2tree.get(iterator.next());
            if(!temp_tree.define){
                throw new ClassRegisterException("Undefined class: "+temp_tree.id);
            }
        }
    }

    public static void main(String []args){
        try {
            registerClass("B", "C");
            registerClass("C","A");
            registerClass("A",null);
            checkNullClass();
        }
        catch (ClassRegisterException e){
            System.out.println(e);
        }

        Iterator<String> iterator = s2tree.keySet().iterator();
        while(iterator.hasNext()){
            String s = iterator.next();
            InheritanceTree p = s2tree.get(s);
            while(p!=null){
                System.out.print(p.id);
                System.out.print("->");
                p=p.parent;
            }
            System.out.println("null");
        }
    }

    public static void registerClass(String id, String parent) throws ClassRegisterException{
        if(parent==null){
            if(s2tree.containsKey(id)){
                if(s2tree.get(id).define){
                    throw new ClassRegisterException("Duplicate class: " + id);
                }
                else{
                    s2tree.get(id).define=true;
                }
            }
            else{
                InheritanceTree temp_tree = new InheritanceTree(id,null);
                temp_tree.define=true;
                s2tree.put(id,temp_tree);
            }
            return;
        }
        if(id.compareTo(parent)==0){
            throw new ClassRegisterException("Cyclic inheritance: "+id+" extends "+parent);
        }
        if(s2tree.containsKey(id)){
            if(s2tree.get(id).define) {
                throw new ClassRegisterException("Duplicate class: " + id);
            }
            else{
                InheritanceTree temp_tree = s2tree.get(id);
                temp_tree.define=true;
                if(s2tree.containsKey(parent)){ // both id and parent exist
                    InheritanceTree p = s2tree.get(parent);
                    while(p!=null){
                        if(p.id.compareTo(id)==0){
                            throw new ClassRegisterException("Cyclic inheritance: "+id+" extends "+parent);
                        }
                        p=p.parent;
                    }
                    temp_tree.parent = s2tree.get(parent);
                }
                else{
                    s2tree.put(parent,new InheritanceTree(parent,null));
                    temp_tree.parent = s2tree.get(parent);
                }
            }
        }else{
            if(!s2tree.containsKey(parent)){ // both id and parent are new
                InheritanceTree temp_parent = new InheritanceTree(parent,null);
                InheritanceTree temp_tree = new InheritanceTree(id,temp_parent);
                temp_tree.define=true;
                s2tree.put(parent,temp_parent);
                s2tree.put(id,temp_tree);
            }
            else {
                InheritanceTree temp_tree = new InheritanceTree(id,s2tree.get(parent));
                temp_tree.define=true;
                s2tree.put(id,temp_tree);
            }
        }

    }

    private static int hash(String s) {
        int h = 0;
        int i = 0;
        for(; s.charAt(i) != '\0'; i++) {
            h = (h * 65599 + s.charAt(i))%SIZE; //ljl: 加入了取模
        }
        return h;
    }

    //before insert or update or delete, call lookup
    public static int lookup(String key) throws UndefinedIdException{ //ljl:加入了抛异常
        int index = hash(key);
        bucket b;
        for(b = table[index]; b != null; b = b.next) {
            if(b.key.compareTo((key))==0) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public static void insert(String key, int binding) {
        int index = hash(key);
        table[index] =  new bucket(key, binding, table[index]);
    }

    public static void pop(String key) {
        int index = hash(key);
        table[index] = table[index].next;
    }

    public static void update(String key, int value){
        pop(key);
        insert(key,value);
    }

    public static boolean lookup_b(String key) throws UndefinedIdException{
        int index = hash(key);
        bucket_b b;
        for(b = table_b[index]; b != null; b = b.next) {
            if(b.key.compareTo((key))==0) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public static void insert_b(String key, boolean binding){
        int index = hash(key);
        table_b[index] =  new bucket_b(key, binding, table_b[index]);
    }

    public static void pop_b(String key){
        int index = hash(key);
        table_b[index] = table_b[index].next;
    }

    public static void update_b(String key, boolean value){
        pop_b(key);
        insert_b(key, value);
    }

    public static int[] lookup_a(String key) throws UndefinedIdException{
        int index = hash(key);
        bucket_a b;
        for(b = table_a[index]; b != null; b = b.next) {
            if(b.key.compareTo(key)==0) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public static void insert_a(String key, int[] binding){
        int index = hash(key);
        table_a[index] =  new bucket_a(key, binding, table_a[index]);
    }

    public static void pop_a(String key){
        int index = hash(key);
        table_a[index] = table_a[index].next;
    }

    public static int[] new_a(int size){
        int []w = new int[size];
        return w;
    }

    public static void update_a(String key, int[] value){
        pop_a(key);
        insert_a(key, value);
    }

    public static Obj lookup_c(String key) throws UndefinedIdException{
        int index = hash(key);
        bucket_c b;
        for(b = table_c[index];b!=null;b=b.next){
            if(b.key.compareTo(key)==0){
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public static void insert_c(String key, Obj binding){
        int index = hash(key);
        table_c[index] = new bucket_c(key, binding, table_c[index]);
    }

    public static void pop_c(String key){
        int index = hash(key);
        table_c[index] = table_c[index].next;
    }

    public static Obj new_c(String t){
        Obj o = new Obj(t);
        return o;
    }

    public static void update_c(String key, Obj value){
        pop_c(key);
        insert_c(key,value);
    }
}

class bucket {
    String key;
    int binding;
    bucket next;
    boolean isNull;

    bucket(bucket next){
        isNull=true;
        this.next=next;
    }
    bucket(String key, int binding, bucket next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
        isNull=false;
    }
}

class bucket_b {
    String key;
    boolean binding;
    bucket_b next;
    boolean isNull;

    bucket_b(bucket_b next){
        isNull=true;
        this.next=next;
    }
    bucket_b(String key, boolean binding, bucket_b next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
        isNull=false;
    }
}

class bucket_a {
    String key;
    int[] binding;
    bucket_a next;
    boolean isNull;

    bucket_a(bucket_a next){
        isNull=true;
        this.next=next;
    }
    bucket_a(String key, int[] binding, bucket_a next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
        isNull=false;
    }

}

class bucket_c {
    String key;
    Obj binding;
    bucket_c next;
    boolean isNull;

    bucket_c(bucket_c next){
        isNull=true;
        this.next=next;
    }
    bucket_c(String key, Obj binding, bucket_c next){
        this.key=key;
        this.binding=binding;
        this.next=next;
        isNull=false;
    }
}