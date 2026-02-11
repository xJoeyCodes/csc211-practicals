package A2practical;

import java.util.HashMap;

public class TestBias {

    public static void main(String[] args) {
        int n = 3;
        int runs = 60000;

        System.out.println(" Testing the biasedshuffle (" + runs + " times) ");
        countForBiased(n, runs);

        System.out.println("\n Testing the correct shuffle (" + runs + " times)");
        countForCorrect(n, runs);
    }

    private static void countForBiased(int n, int runs) {
        HashMap<String, Integer> counts = new HashMap<>();

        for (int i = 0; i < runs; i++) {
            int[] array = BiasedShuffle.biasedshuffle(n);

            String key = "" + array[0] + array[1] + array[2];

            // Add to count
            if (counts.containsKey(key)) {
                counts.put(key, counts.get(key) + 1);
            } else {
                counts.put(key, 1);
            }
        }

        printCounts(counts);
    }

    private static void countForCorrect(int n, int runs) {
        HashMap<String, Integer> counts = new HashMap<>();

        for (int i = 0; i < runs; i++) {
            int[] arr = Shuffle.shuffle(n);

            String key = "" + arr[0] + arr[1] + arr[2];

            if (counts.containsKey(key)) {
                counts.put(key, counts.get(key) + 1);
            } else {
                counts.put(key, 1);
            }
        }

        printCounts(counts);
    }

    private static void printCounts(HashMap<String, Integer> counts) {
        String[] allKeys = {"123", "132", "213", "231", "312", "321"};

        for (String key : allKeys) {
            int howMany = 0;
            if (counts.containsKey(key)) {
                howMany = counts.get(key);
            }
            System.out.println(key + " appeared " + howMany + " times");
        }
    }
}