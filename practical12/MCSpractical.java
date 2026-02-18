package practical12;


import java.util.Random;

public class MCSpractical {

    // Counters 
    static long countOn3;
    static long countOn2A;
    static long countOn2B;
    static long countOnlogn;
    static long countOn;

    // O(n^3) 
    public static int mcsOn3(int[] X) {
        int n = X.length;
        int maxsofar = 0;

        for (int low = 0; low < n; low++) {
            for (int high = low; high < n; high++) {
                int sum = 0;
                for (int r = low; r < high; r++) {
                    sum += X[r];
                    countOn3++;       
                }
                if (sum > maxsofar)
                    maxsofar = sum;
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
                if (sum > maxsofar)
                    maxsofar = sum;
            }
        }
        return maxsofar;
    }

    // O(n^2) 
    public static int mcsOn2B(int[] X) {
        int n = X.length;
        int[] sumTo = new int[n];

        for (int i = 0; i < n; i++) {
            sumTo[i] = (i == 0 ? 0 : sumTo[i - 1]) + X[i];
        }

        int maxsofar = 0;

        for (int low = 0; low < n; low++) {
            for (int high = low; high < n; high++) {

                int sum;
                if (low == 0)
                    sum = sumTo[high];
                else
                    sum = sumTo[high] - sumTo[low - 1];

                countOn2B++;

                if (sum > maxsofar)
                    maxsofar = sum;
            }
        }
        return maxsofar;
    }

    // maxStraddle 
    public static int maxStraddle(int[] X, int low, int high) {

        if (low >= high)
            return 0;

        if (low == high)
            return Math.max(0, X[low]);

        int middle = (low + high) / 2;

        int sum = 0;
        int maxLeft = 0;

        for (int i = middle; i >= low; i--) {
            sum += X[i];
            countOnlogn++;
            if (sum > maxLeft)
                maxLeft = sum;
        }

        sum = 0;
        int maxRight = 0;

        for (int i = middle + 1; i <= high && i < X.length; i++) {
            sum += X[i];
            countOnlogn++;
            if (sum > maxRight)
                maxRight = sum;
        }

        return maxLeft + maxRight;
    }

    // O(n log n)
    public static int mcsOnlogn(int[] X, int low, int high) {

        if (low >= high)
            return 0;

        if (low == high - 1)
            return Math.max(0, X[low]);

        int middle = (low + high) / 2;

        int mLeft = mcsOnlogn(X, low, middle);
        int mRight = mcsOnlogn(X, middle + 1, high);
        int mStraddle = maxStraddle(X, low, high);

        countOnlogn++;

        return Math.max(Math.max(mLeft, mRight), mStraddle);
    }

    // O(n) 
    public static int mcsOn(int[] X) {
        int maxSoFar = 0;
        int maxToHere = 0;

        for (int i = 0; i < X.length; i++) {
            maxToHere = Math.max(maxToHere + X[i], 0);
            countOn++;
            maxSoFar = Math.max(maxSoFar, maxToHere);
        }
        return maxSoFar;
    }

    // Array Generator 
    public static int[] generateArray(int n) {
        Random rand = new Random();
        int[] X = new int[n];

        for (int i = 0; i < n; i++) {
            int value = rand.nextInt(n) + 1;
            if (rand.nextInt(3) == 0)
                value = -value;
            X[i] = value;
        }
        return X;
    }

    // experiment 
    public static void main(String[] args) {

        int[] sizes = {10, 100, 500, 1000, 2000};

        System.out.println("n\tO(n^3)\tO(n^2)\tO(n^2)\tO(nlogn)\tO(n)");

        for (int n : sizes) {

            int[] X = generateArray(n);

            
            countOn3 = 0;
            countOn2A = 0;
            countOn2B = 0;
            countOnlogn = 0;
            countOn = 0;

            mcsOn3(X);
            mcsOn2A(X);
            mcsOn2B(X);
            mcsOnlogn(X, 0, X.length - 1);
            mcsOn(X);

            System.out.println(
                    n + "\t"
                    + countOn3 + "\t"
                    + countOn2A + "\t"
                    + countOn2B + "\t"
                    + countOnlogn + "\t"
                    + countOn
            );
        }
    }
}
