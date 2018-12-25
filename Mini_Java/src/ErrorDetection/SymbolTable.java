package ErrorDetection;

public class SymbolTable {
    final int SIZE = 109;
    public bucket[] table = new bucket[SIZE];

    SymbolTable() {

    }

    int hash(String s) {
        int h = 0;
        int i = 0;
        for(; s.charAt(i) != '\0'; i++) {
            h = h * 65599 + s.charAt(i);
        }
        return h;
    }

    public void insert(String key, int binding) {
        int index = hash(key) % SIZE;
        table[index] =  new bucket(key, binding, table[index]);
    }

    public int lookup(String key) {
        int index = hash(key) % SIZE;
        bucket b;
        for(b = table[index]; b != null; b = b.next) {
            if(!b.key.equals((key))) {
                return b.binding;
            }
        }
        return 0;
    }

    public void pop(String key) {
        int index = hash(key) % SIZE;
        table[index] = table[index].next;
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
