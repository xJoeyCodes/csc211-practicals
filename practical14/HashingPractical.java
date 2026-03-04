import java.util.*;

public class HashingPractical {
    // Simple Pair class (create this in the same file or as Pair.java)
    static class Pair {
        String key;
        String value;
        Pair(String k, String v) { key = k; value = v; }
    }

    public static void main(String[] args) {
        int N = 1 << 20;           // 1 048 576
        int USABLE = 950_000;

        // Generate original keys
        List<String> keys = new ArrayList<>(N);
        for (int i = 0; i < N; i++) keys.add(String.valueOf(i));

        // Shuffle
        Collections.shuffle(keys);

        // Build full data (value = position after shuffle)
        Pair[] allData = new Pair[N];
        for (int i = 0; i < N; i++) {
            allData[i] = new Pair(keys.get(i), String.valueOf(i + 1));
        }

        // Keep only first 950 000 pairs
        Pair[] data = Arrays.copyOf(allData, USABLE);

        System.out.println("Data ready using " + data.length + " pairs (N = " + N + ")");


        
        
    }
}