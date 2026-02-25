package practical13;


import java.io.*;
import java.text.*;
import java.util.*;

public class timeSearches {

    public static final int N = 32654;
    public static Node[] nodes = new Node[N];

    public static class Node {
        int key;
        String data;
        Node(int k, String d) { key = k; data = d; }
    }

    public static void main(String[] args) {

        // rImporting data from ulysses.numbered file using BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader("ulysses-1.numbered"))) {
            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                if (line == null) break;
                String[] parts = line.split(" ", 2);
                int key = Integer.parseInt(parts[0]);
                String data = (parts.length > 1) ? parts[1] : "";
                nodes[i] = new Node(key, data);
            }
            System.out.println("Successfully read " + N + " records.");
        } catch (Exception e) {
            System.out.println("ERROR reading ulysses.numbered: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // the 30 keys
        Random rand = new Random();
        int[] searchKeys = new int[30];
        for (int i = 0; i < 30; i++) {
            searchKeys[i] = rand.nextInt(N) + 1;   // 1 to 32654
        }

        DecimalFormat fiveD = new DecimalFormat("0.00000");
        DecimalFormat fourD = new DecimalFormat("0.0000");
        int repetitions = 30;

        
        long runTimeLin = 0;
        double runTime2Lin = 0.0;
        for (int rep = 0; rep < repetitions; rep++) {
            long start = System.currentTimeMillis();
            for (int k = 0; k < 30; k++) {
                linearSearch(searchKeys[k]);
            }
            long time = System.currentTimeMillis() - start;
            runTimeLin += time;
            runTime2Lin += (double) time * time;
        }
        double aveLin = (double) runTimeLin / repetitions;
        double stdLin = Math.sqrt( (runTime2Lin - repetitions * aveLin * aveLin) / (repetitions - 1) );

        System.out.println("\n\nLINEAR SEARCH STATISTICS");
        System.out.println("________________________________________________");
        System.out.println("Average time = " + fiveD.format(aveLin) + " ms ± " + fourD.format(stdLin) + " ms");
        System.out.println("Standard deviation = " + fourD.format(stdLin) + " ms");
        System.out.println("Repetitions = " + repetitions);
        System.out.println("________________________________________________\n");

        // Sorts array
        Arrays.sort(nodes, Comparator.comparingInt(a -> a.key));
        System.out.println("Array sorted for binary search.");

        
        long runTimeBin = 0;
        double runTime2Bin = 0.0;
        for (int rep = 0; rep < repetitions; rep++) {
            long start = System.currentTimeMillis();
            for (int k = 0; k < 30; k++) {
                binarySearch(searchKeys[k]);
            }
            long time = System.currentTimeMillis() - start;
            runTimeBin += time;
            runTime2Bin += (double) time * time;
        }
        double aveBin = (double) runTimeBin / repetitions;
        double stdBin = Math.sqrt( (runTime2Bin - repetitions * aveBin * aveBin) / (repetitions - 1) );

        System.out.println("BINARY SEARCH STATISTICS");
        System.out.println("________________________________________________");
        System.out.println("Average time = " + fiveD.format(aveBin) + " ms ± " + fourD.format(stdBin) + " ms");
        System.out.println("Standard deviation = " + fourD.format(stdBin) + " ms");
        System.out.println("Repetitions = " + repetitions);
        System.out.println("________________________________________________");

        // here is my final numbers
        System.out.println("Results:");
        System.out.printf("Linear avg: %.5f ms, std: %.4f ms\n", aveLin, stdLin);
        System.out.printf("Binary avg: %.5f ms, std: %.4f ms\n", aveBin, stdBin);
        System.out.println("=====================================");
    }

    // Linear search 
    static int linearSearch(int key) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].key == key) return i;
        }
        return -1;
    }

    // Binary search 
    static int binarySearch(int key) {
        int low = 0;
        int high = nodes.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nodes[mid].key == key) return mid;
            if (nodes[mid].key < key) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }
}
