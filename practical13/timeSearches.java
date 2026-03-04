// practical13/TimeSearches.java
// CSC211 Practical 3 - Linear vs Binary Search Timings
// Simple version for submission - 25 Feb 2026

package practical13;

import java.io.*;
import java.text.*;
import java.util.*;

public class timeSearches {

    
    public static Record[] bigList = new Record[32654];

    
    public static class Record {
        int number;
        String text;

        Record(int n, String t) {
            number = n;
            text = t;
        }
    }

    public static void main(String[] args) {

        // imported the buffered reader and read the file
        try {
            BufferedReader reader = new BufferedReader(
                new FileReader("practical13/ulysses-1.numbered")
            );

            for (int i = 0; i < 32654; i++) {
                String line = reader.readLine();
                if (line == null) break;

                
                String[] parts = line.split(" ", 2);
                int key = Integer.parseInt(parts[0]);
                String data = (parts.length > 1) ? parts[1] : "";
                bigList[i] = new Record(key, data);
            }
            reader.close();
            System.out.println("Read all 32654 lines successfully.");

        } catch (Exception e) {
            System.out.println("File not found! Make sure ulysses-1.numbered is inside the practical13 folder.");
            e.printStackTrace();
            return;
        }

        // 30 keys
        Random random = new Random();
        int[] testKeys = new int[30];
        for (int i = 0; i < 30; i++) {
            testKeys[i] = random.nextInt(32654) + 1;
        }

        // i am doing this for better formatting
        DecimalFormat avgFormat = new DecimalFormat("0.00000");
        DecimalFormat stdFormat = new DecimalFormat("0.0000");
        int runs = 30;   // 30 repetitions as required

        
        long totalLinear = 0;
        double totalLinearSq = 0.0;

        for (int run = 0; run < runs; run++) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 30; i++) {
                findLinear(testKeys[i]);
            }

            long duration = System.currentTimeMillis() - startTime;
            totalLinear += duration;
            totalLinearSq += (double) duration * duration;
        }

        double avgLinear = (double) totalLinear / runs;
        double stdLinear = Math.sqrt( (totalLinearSq - runs * avgLinear * avgLinear) / (runs - 1) );

        System.out.println("\nLINEAR SEARCH RESULTS (unsorted array)");
        System.out.println("--------------------------------------------------");
        System.out.println("Average time = " + avgFormat.format(avgLinear) + " ms ± " + stdFormat.format(stdLinear) + " ms");
        System.out.println("Standard deviation = " + stdFormat.format(stdLinear) + " ms");
        System.out.println("Number of runs = " + runs);
        System.out.println("--------------------------------------------------\n");

        // sorted the array
        Arrays.sort(bigList, (a, b) -> Integer.compare(a.number, b.number));
        

        /// binary search Sorted
        long totalBinary = 0;
        double totalBinarySq = 0.0;

        for (int run = 0; run < runs; run++) {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 30; i++) {
                findBinary(testKeys[i]);
            }

            long duration = System.currentTimeMillis() - startTime;
            totalBinary += duration;
            totalBinarySq += (double) duration * duration;
        }

        double avgBinary = (double) totalBinary / runs;
        double stdBinary = Math.sqrt( (totalBinarySq - runs * avgBinary * avgBinary) / (runs - 1) );

        System.out.println("BINARY SEARCH RESULTS (sorted");
        System.out.println("Average time = " + avgFormat.format(avgBinary) + " ms ± " + stdFormat.format(stdBinary) + " ms");
        System.out.println("Standard deviation = " + stdFormat.format(stdBinary) + " ms");
        System.out.println("Number of runs = " + runs);
        System.out.println("--------------------------------------------------");

        // 4 results
        System.out.println("Final Results:");
        System.out.printf("Linear  avg: %.5f ms    std: %.4f ms\n", avgLinear, stdLinear);
        System.out.printf("Binary  avg: %.5f ms    std: %.4f ms\n", avgBinary, stdBinary);
        System.out.println("=======================================");
    }

    // linear search
    static int findLinear(int target) {
        for (int i = 0; i < bigList.length; i++) {
            if (bigList[i].number == target) {
                return i;
            }
        }
        return -1;
    }

    // binary search 
    static int findBinary(int target) {
        int left = 0;
        int right = bigList.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (bigList[mid].number == target) {
                return mid;
            }
            if (bigList[mid].number < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}