package practical17;

// tNode.java
// Node class for the binary search tree

public class tNode {

    int key;
    tNode left;
    tNode right;

    public tNode(int key) {
        this.key = key;
        left = null;
        right = null;
    }
}