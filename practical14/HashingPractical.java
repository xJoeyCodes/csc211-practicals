package practical14;

import java.util.*;

public class HashingPractical {
    
    static class Pair {
        String key;
        String value;
        Pair(String k, String v) { key = k; value = v; }
    }

    public static void main(String[] args) {
       
        int FULL_N = 1 << 20;           // 1 048 576
        int MAX_USABLE = 950_000;

        System.out.println("Generating and shuffling " + FULL_N + " keys...");

        List<String> keys = new ArrayList<>(FULL_N);
        for (int i = 0; i < FULL_N; i++) {
            keys.add(String.valueOf(i));
        }

        Collections.shuffle(keys);

        Pair[] allData = new Pair[FULL_N];
        for (int i = 0; i < FULL_N; i++) {
            allData[i] = new Pair(keys.get(i), String.valueOf(i + 1));
        }

        Pair[] data = Arrays.copyOf(allData, MAX_USABLE);
        System.out.println("Data ready: " + data.length + " pairs available");


        //  α and target N 
        double[] alphas     = {0.75, 0.80, 0.85, 0.90, 0.95};
        int[] targetN       = {750_000, 800_000, 850_000, 900_000, 950_000};
        double[] oneOver1ma = {4.0, 5.0, 6.6667, 10.0, 20.0};  

        final int M = 1_000_000;               
        final int LOOKUPS_PER_RUN = 10_000;
        final int REPS = 30;

        System.out.println("\nHash α\tN\t1/(1-α)\tAverage time in seconds");
        System.out.println("      \t  \t      \tOpen hash\tChained hash");

        for (int row = 0; row < alphas.length; row++) {
            double alpha = alphas[row];
            int n = targetN[row];   

            
            openHash oh = new openHash(M);
            chainedHashing ch = new chainedHashing(M);

           
            System.out.print("Building tables for α ≈ " + alpha + " (N = " + n + ") ... ");
            for (int i = 0; i < n; i++) {
                oh.insert(data[i].key, data[i].value);
                ch.insert(data[i].key, data[i].value);
            }
            System.out.println("done");

            // Time lookups 
            long totalOpenMs = 0;
            long totalChainMs = 0;

            for (int r = 0; r < REPS; r++) {
                long start = System.currentTimeMillis();
                for (int i = 0; i < LOOKUPS_PER_RUN; i++) {
                    String k = data[i].key;  // keys 0 to LOOKUPS_PER_RUN-1 are definitely inserted
                    oh.lookup(k);
                }
                totalOpenMs += System.currentTimeMillis() - start;

                start = System.currentTimeMillis();
                for (int i = 0; i < LOOKUPS_PER_RUN; i++) {
                    String k = data[i].key;
                    ch.lookup(k);
                }
                totalChainMs += System.currentTimeMillis() - start;
            }

            double avgOpenSec  = totalOpenMs  / (double) REPS / 1000.0;
            double avgChainSec = totalChainMs / (double) REPS / 1000.0;

            
            System.out.printf("Hash α=%.0f%%\t%dK\t%.1f\t%.4f\t\t%.4f%n",
                    alpha * 100, n / 1000, oneOver1ma[row], avgOpenSec, avgChainSec);
        }

    }
}