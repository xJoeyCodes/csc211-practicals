package A2practical;

import java.util.Random;

public class BiasedShuffle {

    public static int[] biasedshuffle(int n) {
        int[] shuffled = new int[n];

        // fill with starting 1
        for (int i = 0; i < n; i++) {
            shuffled[i] = i + 1;
        }

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            int r = rand.nextInt(n);
            int temp = shuffled[i];
            shuffled[i] = shuffled[r];
            shuffled[r] = temp;
        }

        return shuffled;
    }

    public static void main(String[] args) {
        int[] result = biasedshuffle(10);
        System.out.print("biasedshuffle: ");
        for (int num : result) System.out.print(num + " ");
        System.out.println();
    }
}