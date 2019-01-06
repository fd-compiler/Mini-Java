package ErrorDetection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SymbolTable {
    private static final int SIZE = 1000;
    private static bucket[] table = new bucket[SIZE];       // used for int
    private static bucket_b[] table_b = new bucket_b[SIZE]; // used for boolean
    private static bucket_a[] table_a = new bucket_a[SIZE]; // used for array
    private static bucket_c[] table_c = new bucket_c[SIZE]; // used for objects

    static Map<String, InheritanceTree> s2tree = new HashMap<>();

    static{
        InheritanceTree t_int = new InheritanceTree("int");
        s2tree.put("int",t_int);
        InheritanceTree t_array = new InheritanceTree("int[]");
        s2tree.put("int[]",t_array);
        InheritanceTree t_bool = new InheritanceTree("boolean");
        s2tree.put("boolean",t_bool);
    }

    static void checkNullClass() throws ClassRegisterException{
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
            System.out.println(e.getMessage());
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

    static void registerClass(String id, String parent) throws ClassRegisterException{
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
        for(; i<s.length(); i++) {
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

    static void insert(String key, int binding) {
        int index = hash(key);
        table[index] =  new bucket(key, binding, table[index]);
    }

    static void insert_not_initial(String key){
        int index = hash(key);
        table[index] = new bucket(key,table[index]);
    }

    private static void pop(String key) {
        int index = hash(key);
        table[index] = table[index].next;
    }

    private static void insert_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table[i] != null) {
                table[i] = new bucket(table[i]);
            }
        }
    }

    private static void pop_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table[i] != null && table[i].isNull) {
                table[i] = table[i].next;
            }
            else if(table[i] != null && !table[i].isNull){
                if(table[i].next != null && table[i].next.isNull) {
                    table[i] = table[i].next.next;
                }
                else{
                    table[i] = table[i].next;
                }
            }
        }
    }

    static void update(String key, int value){
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

    static void insert_b(String key, boolean binding){
        int index = hash(key);
        table_b[index] =  new bucket_b(key, binding, table_b[index]);
    }

    static void insert_b_not_initial(String key){
        int index = hash(key);
        table_b[index]=new bucket_b(key,table_b[index]);
    }

    private static void pop_b(String key){
        int index = hash(key);
        table_b[index] = table_b[index].next;
    }

    private static void insert_b_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table_b[i] != null) {
                table_b[i] = new bucket_b(table_b[i]);
            }
        }
    }

    private static void pop_b_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table_b[i] != null && table_b[i].isNull) {
                table_b[i] = table_b[i].next;
            }
            else if(table_b[i] != null && !table_b[i].isNull){
                if(table_b[i].next != null && !table_b[i].next.isNull) {
                    table_b[i] = table_b[i].next.next;
                }
                else{
                    table_b[i] = table_b[i].next;
                }
            }
        }
    }

    static void update_b(String key, boolean value){
        pop_b(key);
        insert_b(key, value);
    }

    static int[] lookup_a(String key) throws UndefinedIdException{
        int index = hash(key);
        bucket_a b;
        for(b = table_a[index]; b != null; b = b.next) {
            if(b.key.compareTo(key)==0) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    static void insert_a(String key, int[] binding){
        int index = hash(key);
        table_a[index] =  new bucket_a(key, binding, table_a[index]);
    }

    private static void pop_a(String key){
        int index = hash(key);
        table_a[index] = table_a[index].next;
    }

    private static void insert_a_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table_a[i] != null) {
                table_a[i] = new bucket_a(table_a[i]);
            }
        }
    }

    private static void pop_a_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table_a[i] != null && table_a[i].isNull) {
                table_a[i] = table_a[i].next;
            }
            else if(table_a[i] != null && !table_a[i].isNull){
                if(table_a[i].next != null && table_a[i].next.isNull) {
                    table_a[i] = table_a[i].next.next;
                }
                else{
                    table_a[i] = table_a[i].next;
                }
            }
        }
    }


    static void update_a(String key, int[] value){
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

    static void insert_c(String key, String type){
        int index = hash(key);
        table_c[index] = new bucket_c(key, type, table_c[index]);
    }

    public static void pop_c(String key){
        int index = hash(key);
        table_c[index] = table_c[index].next;
    }

    private static void insert_c_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table_c[i] != null) {
                table_c[i] = new bucket_c(table_c[i]);
            }
        }
    }

    private static void pop_c_all() {
        int i;
        for(i=0; i<SIZE; i++) {
            if(table_c[i] != null && table_c[i].isNull) {
                table_c[i] = table_c[i].next;
            }
            else if(table_c[i] != null && !table_c[i].isNull){
                if(table_c[i].next != null && table_c[i].next.isNull) {
                    table_c[i] = table_c[i].next.next;
                }
                else{
                    table_c[i] = table_c[i].next;
                }
            }
        }
    }

    static void update_c(String key, Obj value){
        int index = hash(key);
        table_c[index].binding=value;
    }

    static Card findVariable(String key){
        Card card = new Card();
        card.isIn=false;
        card.isInitial=false;
        int index = hash(key);
        if(table[index]!=null&&(!table[index].isNull)){
            if(table[index].key.compareTo(key)==0){
                card.type="int";
                card.isIn=true;
                card.isInitial = table[index].isInitial;
                if(card.isInitial){
                    card.iValue=table[index].binding;
                }
                return card;
            }
        }
        else if(table_b[index]!=null&&(!table_b[index].isNull)){
            if(table_b[index].key.compareTo(key)==0) {
                card.type = "boolean";
                card.isIn = true;
                card.isInitial = table_b[index].isInitial;
                if (card.isInitial) {
                    card.bValue = table_b[index].binding;
                }
                return card;
            }
        }
        else if(table_a[index]!=null&&(!table_a[index].isNull)){
            if(table_a[index].key.compareTo(key)==0){
                card.type="int[]";
                card.isIn=true;
                card.isInitial = table_a[index].binding!=null;
                if(card.isInitial){
                    card.aValue=table_a[index].binding;
                }
                return card;
            }
        }
        else if(table_c[index]!=null&&(!table_c[index].isNull)){
            if(table_c[index].key.compareTo(key)==0){
                card.type=table_c[index].type;
                card.isIn =true;
                card.isInitial = table_c[index].binding!=null;
                if(card.isInitial){
                    card.obj=table_c[index].binding;
                }
                return card;
            }
        }
        return card;
    }

    static void table_to_new() {
        insert_all();
        insert_a_all();
        insert_b_all();
        insert_c_all();
    }

    static void table_to_old() {
        pop_all();
        pop_a_all();
        pop_b_all();
        pop_c_all();
    }
}

class bucket {
    String key;
    int binding;
    bucket next;
    boolean isNull;
    boolean isInitial;

    bucket(bucket next){
        isNull=true;
        this.next=next;
    }
    bucket(String key, bucket next){
        this.key=key;
        this.next=next;
        isInitial=false;
    }
    bucket(String key, int binding, bucket next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
        isNull=false;
        isInitial=true;
    }
}

class bucket_b {
    String key;
    boolean binding;
    bucket_b next;
    boolean isNull;
    boolean isInitial;

    bucket_b(bucket_b next){
        isNull=true;
        this.next=next;
    }
    bucket_b(String key, bucket_b next){
        this.key=key;
        this.next=next;
        isInitial=false;
    }
    bucket_b(String key, boolean binding, bucket_b next) {
        this.key = key;
        this.binding = binding;
        this.next = next;
        isNull=false;
        isInitial = true;
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
    String type;
    bucket_c next;
    boolean isNull;

    bucket_c(bucket_c next){
        isNull=true;
        this.next=next;
    }
    bucket_c(String key, String type, bucket_c next){
        this.key=key;
        this.type=type;
        this.next=next;
        isNull=false;
    }
}

class Card {
    boolean isIn;
    String type;
    boolean isInitial;

    int iValue;
    boolean bValue;
    int[] aValue;
    Obj obj;

}