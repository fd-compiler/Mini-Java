package ErrorDetection;

public class SymbolTable {
    private static final int SIZE = 200;
    public bucket[] table = new bucket[SIZE];

    SymbolTable() {

    }

    int hash(String s) {
        int h = 0;
        int i = 0;
        for(; s.charAt(i) != '\0'; i++) {
            h = (h * 65599 + s.charAt(i))%SIZE; //ljl: 加入了取模
        }
        return h;
    }

    public void insert(String key, int binding) {
        int index = hash(key);
        table[index] =  new bucket(key, binding, table[index]);
    }

    //before insert or update or delete, call lookup
    public int lookup(String key) throws UndefinedIdException{ //ljl:加入了抛异常
        int index = hash(key);
        bucket b;
        for(b = table[index]; b != null; b = b.next) {
            if(b.key.equals((key))) {
                return b.binding;
            }
        }
        throw new UndefinedIdException(key);
    }

    public void pop(String key) {
        int index = hash(key);
        table[index] = table[index].next;
    }

    //ljl: 加入update
    public void update(String key, int value){
        pop(key);
        insert(key,value);
    }

    public boolean lookup_b(String key) throws UndefinedIdException{
        throw new UndefinedIdException(key);
    }

    public void insert_b(String key, boolean value){

    }

    public void pop_b(String key){

    }

    public void update_b(String key, boolean value){

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

}

