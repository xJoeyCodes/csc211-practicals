package practical14;

public class chainedHashing {
    private Node[] table;   // headers of linked lists
    private int m;
    private int count;

    // Inner node class 
    private static class Node {
        String key;
        String value;
        Node next;
        Node(String k, String v) { key = k; value = v; next = null; }
    }

    public chainedHashing(int tableSize) {
        m = tableSize;
        table = new Node[m + 1];
        count = 0;
    }

    
    private int hash(String key) {
        return Math.abs(key.hashCode()) % m + 1;
    }

    public void insert(String key, String value) {
        int i = hash(key);

        // search the list for existing keys 
        Node current = table[i];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;   // update
                return;
            }
            current = current.next;
        }

        
        Node newNode = new Node(key, value);
        newNode.next = table[i];
        table[i] = newNode;
        count++;
    }


    public String lookup(String key) {
        int i = hash(key);
        Node current = table[i];      
        while (current != null) {
            if (current.key.equals(key)) return current.value;
            current = current.next;
        }
        return null;
    }

    public String remove(String key) {
        int i = hash(key);
        Node current = table[i];
        Node prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                String val = current.value;
                if (prev == null) table[i] = current.next;
                else prev.next = current.next;
                count--;
                return val;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public boolean isEmpty()  { return count == 0; }
    public boolean isFull()   { return false; }   // never full
    public boolean isInTable(String key) { return lookup(key) != null; }
}
