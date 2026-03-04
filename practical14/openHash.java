package practical14;

public class openHash {
    private Pair[] table;   // indices 1 to m
    private int m;
    private int count;      // number of items currently in table

    public openHash(int tableSize) {
        m = tableSize;
        table = new Pair[m + 1];
        count = 0;
    }

    
    private int hash(String key) {
        return Math.abs(key.hashCode()) % m + 1;   // guaranteed [1..m]
    }

    // (b) insert
    public void insert(String key, String value) {
        if (count >= m) { System.out.println("Table full!"); return; }

        int i = hash(key);
        int start = i;

        while (table[i] != null) {
            if (table[i].key.equals(key)) {
                table[i].value = value;   
                return;
            }
            i = (i % m) + 1;             
            if (i == start) break;
        }

        table[i] = new Pair(key, value);
        count++;
    }

    // (c) lookup
    public String lookup(String key) {
        int i = hash(key);
        int start = i;

        while (table[i] != null) {
            if (table[i].key.equals(key)) return table[i].value;
            i = (i % m) + 1;
            if (i == start) break;
        }
        return null;   // if not found, stop at first null
    }

    // (d) remove
    public String remove(String key) {
        int i = hash(key);
        int start = i;

        while (table[i] != null) {
            if (table[i].key.equals(key)) {
                String val = table[i].value;
                table[i] = null;
                count--;
                return val;
            }
            i = (i % m) + 1;
            if (i == start) break;
        }
        return null;
    }

    // (e) predicates
    public boolean isEmpty()  { return count == 0; }
    public boolean isFull()   { return count == m; }
    public boolean isInTable(String key) { return lookup(key) != null; }
}