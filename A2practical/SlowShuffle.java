package A2practical;
import java.util.Random;

public class SlowShuffle {

    public static int[] shuffle(int n) {
        int[] result = new int[n];
        boolean[] used = new boolean[n + 1];   // index 1 to n, false = not used yet

        Random rand = new Random();
        int count = 0;

        
        while (count < n - 1) {
            int r = rand.nextInt(n) + 1;       // random number from 1 to n

            if (!used[r]) {                    
                result[count] = r;
                used[r] = true;
                count = count + 1;
            }
            
        }



        return result;
    }


}