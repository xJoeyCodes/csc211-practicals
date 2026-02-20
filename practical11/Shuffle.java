package A2practical;

import java.util.Random;

public class Shuffle {

    public static int[] shuffle(int n) {
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }

        Random rand = new Random();

        for (int i = n - 1; i > 0; i--) {
            int r = rand.nextInt(i + 1);  
            int temp = arr[i];
            arr[i] = arr[r];
            arr[r] = temp;
        }

        return arr;
    }

    public static void main(String[] args) {
        int[] result = shuffle(10);
        System.out.print("correct shuffle: ");
        for (int num : result) System.out.print(num + " ");
        System.out.println();
    }
}