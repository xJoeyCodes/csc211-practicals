package practical12;

import java.util.Random;

public class MCSpractical {
    // Static counters for operations
    static long countOn3 = 0;
    static long countOn2A = 0;
    static long countOn2B = 0;
    static long countOnlogn = 0;
    static long countOn = 0;

    // O(n^3) 
    public static int mcsOn3(int[] X) {
        int n = X.length;
        int maxsofar = 0;
        for (int low = 0; low < n; low++) {
            for (int high = low + 1; high <= n; high++) {  
                int sum = 0;
                for (int r = low; r < high; r++) {
                    sum += X[r];
                    countOn3++;
                }
                if (sum > maxsofar) {
                    maxsofar = sum;
                }
            }
        }
        return maxsofar;
    }

    // O(n^2) 
    public static int mcsOn2A(int[] X) {
        int n = X.length;
        int maxsofar = 0;
        for (int low = 0; low < n; low++) {
            int sum = 0;
            for (int r = low; r < n; r++) {
                sum += X[r];
                countOn2A++;
                if (sum > maxsofar) {
                    maxsofar = sum;
                }
            }
        }
        return maxsofar;
    }

    // O(n^2) Version B 
    public static int mcsOn2B(int[] X) {
        int n = X.length;
        int[] sumTo = new int[n + 1];
        sumTo[0] = 0;
        for (int i = 1; i <= n; i++) {
            sumTo[i] = sumTo[i - 1] + X[i - 1];
        }
        int maxsofar = 0;
        for (int low = 0; low < n; low++) {
            for (int high = low + 1; high <= n; high++) {
                int sum = sumTo[high] - sumTo[low];
                countOn2B++;
                if (sum > maxsofar) {
                    maxsofar = sum;
                }
            }
        }
        return maxsofar;
    }

    // Helper for O(n log n), max straddle sum
    public static int maxStraddle(int[] X, int low, int high) {
        if (low > high) return 0;
        int mid = (low + high) / 2;
        int sum = 0;
        int maxLeft = 0;
        for (int i = mid; i >= low; i--) {
            sum += X[i];
            countOnlogn++;
            maxLeft = Math.max(maxLeft, sum);
        }
        sum = 0;
        int maxRight = 0;
        for (int i = mid + 1; i <= high; i++) {
            sum += X[i];
            countOnlogn++;
            maxRight = Math.max(maxRight, sum);
        }
        return maxLeft + maxRight;
    }

    // O(n log n) 
    public static int mcsOnlogn(int[] X, int low, int high) {
        if (low > high) return 0;
        if (low == high) return Math.max(0, X[low]);
        int mid = (low + high) / 2;
        int mLeft = mcsOnlogn(X, low, mid);
        int mRight = mcsOnlogn(X, mid + 1, high);
        int mStraddle = maxStraddle(X, low, high);
        return Math.max(Math.max(mLeft, mRight), mStraddle);
    }

    // O(n) 
    public static int mcsOn(int[] X) {
        int maxSoFar = 0;
        int maxToHere = 0;
        for (int value : X) {
            maxToHere = Math.max(maxToHere + value, 0);
            countOn++;
            maxSoFar = Math.max(maxSoFar, maxToHere);
        }
        return maxSoFar;
    }

    // array generation 
    public static int[] generateArray(int n) {
        Random rand = new Random();
        int[] X = new int[n];
        for (int i = 0; i < n; i++) {
            int value = rand.nextInt(n) + 1;
            if (rand.nextInt(3) == 0) {
                value = -value;
            }
            X[i] = value;
        }
        return X;
    }

    public static void main(String[] args) {
        int[] sizes = {10, 100, 500, 1000, 2000, 5000};
        System.out.println("n\tO(n^3)\tO(n^2 A)\tO(n^2 B)\tO(n log n)\tO(n)");
        for (int n : sizes) {
            int[] X = generateArray(n);
            System.out.print(n + "\t");

            // O(n^3)
            countOn3 = 0;
            long start = System.currentTimeMillis();
            mcsOn3(X);
            long time = System.currentTimeMillis() - start;
            System.out.print((time > 60000 ? "Over minute" : countOn3) + "\t");

            // O(n^2 A)
            countOn2A = 0;
            start = System.currentTimeMillis();
            mcsOn2A(X);
            time = System.currentTimeMillis() - start;
            System.out.print((time > 60000 ? "Over minute" : countOn2A) + "\t");

            // O(n^2 B)
            countOn2B = 0;
            start = System.currentTimeMillis();
            mcsOn2B(X);
            time = System.currentTimeMillis() - start;
            System.out.print((time > 60000 ? "Over minute" : countOn2B) + "\t");

            // O(n log n)
            countOnlogn = 0;
            start = System.currentTimeMillis();
            mcsOnlogn(X, 0, n - 1);
            time = System.currentTimeMillis() - start;
            System.out.print((time > 60000 ? "Over minute" : countOnlogn) + "\t");

            // O(n)
            countOn = 0;
            start = System.currentTimeMillis();
            mcsOn(X);
            time = System.currentTimeMillis() - start;
            System.out.println(time > 60000 ? "Over minute" : countOn);
        }
    }
}