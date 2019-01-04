package ErrorDetection;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private static final int SIZE = 200;
    public bucket[] table = new bucket[SIZE];       // used for int
    public bucket_b[] table_b = new bucket_b[SIZE]; // used for boolean
    public bucket_a[] table_a = new bucket_a[SIZE]; // used for array

    public static Map<String, InheritanceTree> s2tree = new HashMap<>();

    public SymbolTable(){
        InheritanceTree t_int = new InheritanceTree("int");
        s2tree.put("int",t_int);
        InheritanceTree t_array = new InheritanceTree("int[]");
        s2tree.put("int[]",t_array);
        InheritanceTree t_bool = new InheritanceTree("boolean");
        s2tree.put("boolean",t_bool);
    }

    int hash(String s) {
        int h = 0;
        int i = 0;
        for(; s.charAt(i) != '\0'; i++) {
            h = (h * 65599 + s.charAt(i))%SIZE; //ljl: 加入了取模
        }
        return h;
    }

    //before insert or update or delete, call lookup
    public int lookup(String key) throws UndefinedIdException{ //ljl:加入了抛异常
        int index = hash(key);
        bucket b;
        for(b = table[index]; b != null; b = b.next) {
            if(b.key.compareTo((key))==0) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public void insert(String key, int binding) {
        int index = hash(key);
        table[index] =  new bucket(key, binding, table[index]);
    }

    public void pop(String key) {
        int index = hash(key);
        table[index] = table[index].next;
    }

    public void update(String key, int value){
        pop(key);
        insert(key,value);
    }

    public boolean lookup_b(String key) throws UndefinedIdException{
        int index = hash(key);
        bucket_b b;
        for(b = table_b[index]; b != null; b = b.next) {
            if(b.key.compareTo((key))==0) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public void insert_b(String key, boolean binding){
        int index = hash(key);
        table_b[index] =  new bucket_b(key, binding, table_b[index]);
    }

    public void pop_b(String key){
        int index = hash(key);
        table_b[index] = table_b[index].next;
    }

    public void update_b(String key, boolean value){
        pop_b(key);
        insert_b(key, value);
    }

    public int[] lookup_a(String key) throws UndefinedIdException{
        int index = hash(key);
        bucket_a b;
        for(b = table_a[index]; b != null; b = b.next) {
            if(b.key.compareTo((key))==0) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public void insert_a(String key, int[] binding){
        int index = hash(key);
        table_a[index] =  new bucket_a(key, binding, table_a[index]);
    }

    public void pop_a(String key){
        int index = hash(key);
        table_a[index] = table_a[index].next;
    }

    public int[] new_a(int size){
        int []w = new int[size];
        return w;
    }

    public void update_a(String key, int[] value){
        pop_a(key);
        insert_a(key, value);
    }
}

class bucket {
    String key;
    int binding;
    bucket next;

    bucket(String key, int binding, bucket next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
    }
}

class bucket_b {
    String key;
    boolean binding;
    bucket_b next;

    bucket_b(String key, boolean binding, bucket_b next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
    }
}

class bucket_a {
    String key;
    int[] binding;
    bucket_a next;

    bucket_a(String key, int[] binding, bucket_a next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
    }

}

class bucket_c {
    String key;
    InheritanceTree type;
    //todo: 用一个树t作为class的实例的保存，包括String[] field_names, t[] fields;
}