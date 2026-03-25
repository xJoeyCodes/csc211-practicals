package practical17;

// tryBST.java
// Builds a balanced BST by inserting integers [1..2^n - 1] in breadth-first
// order (middle element first), then removes all even-keyed nodes.
// Times both operations over 30 repetitions and prints the results.

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class tryBST {

    private tNode root;

    public tryBST() {
        root = null;
    }

    // insert a key into the BST
    public void insert(int key) {
        tNode newNode = new tNode(key);
        if (root == null) {
            root = newNode;
            return;
        }
        tNode current = root;
        while (true) {
            if (key < current.key) {
                if (current.left == null) {
                    current.left = newNode;
                    return;
                }
                current = current.left;
            } else if (key > current.key) {
                if (current.right == null) {
                    current.right = newNode;
                    return;
                }
                current = current.right;
            } else {
                return; // duplicate, ignore
            }
        }
    }

    // search for a key, returns the node or null if not found
    public tNode search(int key) {
        tNode current = root;
        while (current != null) {
            if (key == current.key) {
                return current;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    // delete a key from the BST
    public void delete(int key) {
        root = deleteNode(root, key);
    }

    private tNode deleteNode(tNode node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = deleteNode(node.left, key);
        } else if (key > node.key) {
            node.right = deleteNode(node.right, key);
        } else {
            // node to delete found
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // two children
            tNode successor = findMin(node.right);
            node.key = successor.key;
            node.right = deleteNode(node.right, successor.key);
        }
        return node;
    }

    private tNode findMin(tNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // checks whether the tree satisfies the BST property
    public boolean isBST() {
        return checkBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean checkBST(tNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        if (node.key <= min || node.key >= max) {
            return false;
        }
        return checkBST(node.left, min, node.key) && checkBST(node.right, node.key, max);
    }

    // builds a balanced BST from integers by inserting the middle
    // element first, then the middles of each half, in breadth-first order
    public void populate(int lo, int hi) {
        root = null;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{lo, hi});

        while (!queue.isEmpty()) {
            int[] range = queue.poll();
            int l = range[0];
            int h = range[1];

            if (l > h) continue;

            int mid = (l + h) / 2;
            insert(mid);

            queue.add(new int[]{l, mid - 1});
            queue.add(new int[]{mid + 1, h});
        }
    }

    // removes all nodes with even keys
    public void removeEvens() {
        ArrayList<Integer> evenKeys = new ArrayList<>();
        collectEvens(root, evenKeys);
        for (int key : evenKeys) {
            delete(key);
        }
    }

    private void collectEvens(tNode node, ArrayList<Integer> evenKeys) {
        if (node == null) return;
        collectEvens(node.left, evenKeys);
        if (node.key % 2 == 0) {
            evenKeys.add(node.key);
        }
        collectEvens(node.right, evenKeys);
    }

    // in-order traversal print
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(tNode node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.print(node.key + " ");
        inOrderRec(node.right);
    }

    // returns the number of nodes in the tree
    public int size() {
        return countNodes(root);
    }

    private int countNodes(tNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // statistics helpers
    private static double average(double[] times) {
        double sum = 0;
        for (double t : times) sum += t;
        return sum / times.length;
    }

    private static double stdDeviation(double[] times, double avg) {
        double sum = 0;
        for (double t : times) {
            sum += (t - avg) * (t - avg);
        }
        return Math.sqrt(sum / times.length);
    }

    public static void main(String[] args) {

        // smmall test 
        System.out.println("Testing with n = 4 (keys 1 to 15)");
        tryBST T = new tryBST();
        int n = 4;
        int hi = (int) Math.pow(2, n) - 1;
        T.populate(1, hi);

        System.out.print("In-order traversal: ");
        T.inOrder();
        System.out.println("isBST() = " + T.isBST());
        System.out.println("Size = " + T.size());

        T.removeEvens();
        System.out.print("In-order after removing evens: ");
        T.inOrder();
        System.out.println("isBST() after removing evens = " + T.isBST());
        System.out.println("Size after removing evens = " + T.size());

        // timing run with n = 20
        System.out.println("\nStarting timing run with n = 20...");

        n = 20;
        hi = (int) Math.pow(2, n) - 1;
        int reps = 30;

        double[] populateTimes = new double[reps];
        double[] removeTimes = new double[reps];

        tryBST tree = new tryBST();

        for (int i = 0; i < reps; i++) {
            long start = System.currentTimeMillis();
            tree.populate(1, hi);
            long end = System.currentTimeMillis();
            populateTimes[i] = end - start;

            start = System.currentTimeMillis();
            tree.removeEvens();
            end = System.currentTimeMillis();
            removeTimes[i] = end - start;
        }

        double popAvg = average(populateTimes);
        double popStd = stdDeviation(populateTimes, popAvg);
        double remAvg = average(removeTimes);
        double remStd = stdDeviation(removeTimes, remAvg);

        System.out.println("\nNumber of keys n = " + hi);
        System.out.println("Repetitions = " + reps);
        System.out.println();
        System.out.printf("%-30s %20s %20s%n", "Method", "Average time (ms)", "Std Deviation");
        System.out.printf("%-30s %20.2f %20.2f%n", "Populate tree", popAvg, popStd);
        System.out.printf("%-30s %20.2f %20.2f%n", "Remove evens from tree", remAvg, remStd);
    }
}
