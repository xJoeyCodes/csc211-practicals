package A2practical;

import java.util.Random;

public class SlowShuffle {

    public static int[] slowshuffle(int n) {
        int[] shuffled = new int[n];
        boolean[] isNotPresent = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            isNotPresent[i] = true;
        }

        Random rand = new Random();
        int count = 0;

        while (count < n - 1) {
            int r = rand.nextInt(n) + 1;  // 1 to n

            if (isNotPresent[r]) {
                shuffled[count] = r;
                isNotPresent[r] = false;
                count++;
            }
        }

        for (int i = 1; i <= n; i++) {
            if (isNotPresent[i]) {
                shuffled[n - 1] = i;
                break;
            }
        }

        return shuffled;
    }

    // Quick test to check my output
    public static void main(String[] args) {
        int[] result = slowshuffle(10);
        System.out.print("slowshuffle: ");
        for (int num : result) System.out.print(num + " ");
        System.out.println();
    }
}