package practical16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;                    
import java.util.List;



public class tryHeapSort {

    public static void main(String[] args) {
        // The reason why I used ulysses.txt instead of the joyce1922 ulysses.text is because I couldnt open
        // the file, nor could I copy the text from the file, so I used the ulysses.text file.
        String inputFile = "practical16/ulysses.text";  
        if (args.length > 0) {
            inputFile = args[0];
        }

        List<String> wordList;
        try {
            wordList = readWordsFromFile(inputFile);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }
        // if nothing is read from the file then we should print a message and exit
        if (wordList.isEmpty()) {
            System.out.println("No words found in file.");
            return;
        }

        String[] words = wordList.toArray(new String[0]);

        System.out.println("Loaded " + words.length + " words from cleaned Ulysses output.");

        // c, Quick correctness test with a short array (<=20 words)
        String[] sampleWords = {
            "pear", "apple", "orange", "banana", "kiwi", "date", "fig", "melon",
            "grape", "lemon", "lime", "plum", "apricot", "cherry", "mango", "berry"
        };
        runHeapTiming("Sample bottom-up", sampleWords, true);
        runHeapTiming("Sample top-down", sampleWords, false);

        // a and b,  Large input timings: bottom-up and top-down builds + sorting
        runHeapTiming("Ulysses bottom-up", words, true);
        runHeapTiming("Ulysses top-down", words, false);
    }

    private static void runHeapTiming(String label, String[] snapshot, boolean bottomUp) {
        String[] data = Arrays.copyOf(snapshot, snapshot.length);

        System.out.println("\n=== " + label + " ===");
        System.out.println("Input size: " + data.length);

        if (bottomUp) {
            long startBuild = System.nanoTime();
            buildMaxHeap(data);
            long endBuild = System.nanoTime();

            System.out.println("Bottom-up heap built. Root = " + (data.length > 0 ? data[0] : "<empty>"));
            System.out.printf("Bottom-up build time: %.3f ms\n", (endBuild - startBuild) / 1_000_000.0);

            long startSort = System.nanoTime();
            heapSortFromHeap(data);
            long endSort = System.nanoTime();
            System.out.printf("Sort time (after bottom-up build): %.3f ms\n", (endSort - startSort) / 1_000_000.0);
        } else {
            long startBuild = System.nanoTime();
            buildMaxHeapTopDown(data);
            long endBuild = System.nanoTime();

            System.out.println("Top-down heap built. Root = " + (data.length > 0 ? data[0] : "<empty>"));
            System.out.printf("Top-down build time: %.3f ms\n", (endBuild - startBuild) / 1_000_000.0);

            long startSort = System.nanoTime();
            heapSortFromHeap(data);
            long endSort = System.nanoTime();
            System.out.printf("Sort time (after top-down build): %.3f ms\n", (endSort - startSort) / 1_000_000.0);
        }
        // Sample sort
        System.out.print("Sorted sample (first 20): ");  
        printSample(data, 20);
        System.out.println("=== End " + label + " ===\n");
    }

    public static void heapSortFromHeap(String[] heap) {
        for (int end = heap.length - 1; end > 0; end--) {
            swap(heap, 0, end);
            siftDown(heap, end, 0);
        }
    }

    public static void buildMaxHeapTopDown(String[] array) {
        for (int i = 1; i < array.length; i++) {
            siftUp(array, i);
        }
    }

    private static void siftUp(String[] heap, int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap[i].compareToIgnoreCase(heap[parent]) > 0) {
                swap(heap, i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private static List<String> readWordsFromFile(String path) throws IOException {
        List<String> words = new ArrayList<>();
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File not found: " + path);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {    // importing buffered reader and 
            String line;                                                            // and file reader to read the file line by line
            while ((line = reader.readLine()) != null) {
                // Split by non-letter characters 
                String[] tokens = line.split("[^A-Za-z']+");
                for (String token : tokens) {
                    String w = token.trim();
                    if (!w.isEmpty()) {
                        words.add(w);
                    }
                }
            }
        }
        return words;
    }

    public static void buildMaxHeap(String[] array) { 
        int n = array.length;
        for (int i = (n - 2) / 2; i >= 0; i--) {
            siftDown(array, n, i);
        }
    }

    private static void siftDown(String[] heap, int size, int i) {
        int largest = i;
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < size && heap[left].compareToIgnoreCase(heap[largest]) > 0) {
                largest = left;
            }
            if (right < size && heap[right].compareToIgnoreCase(heap[largest]) > 0) {
                largest = right;
            }
            if (largest == i) {
                break;
            }
            swap(heap, i, largest);
            i = largest;
        }
    }

    public static void heapSort(String[] array) {
        buildMaxHeap(array);
        for (int end = array.length - 1; end > 0; end--) {
            swap(array, 0, end);
            siftDown(array, end, 0);
        }
    }

    private static void swap(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void printSample(String[] array, int count) {
        int n = Math.min(count, array.length);
        System.out.println("First " + n + " entries:");
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
            if (i < n - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}

