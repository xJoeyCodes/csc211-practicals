package practical15;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class Anagrams {
    public static void main(String[] args) {
        String filename = "practical15/ulysses.text";
        Map<String, List<String>> dict = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String w : words) {
                    String cleaned = cleanWord(w);
                    if (cleaned.isEmpty())
                        continue;
                    String key = signature(cleaned);
                    dict.computeIfAbsent(key, k -> new ArrayList<>()).add(cleaned);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // print only entries that have more than one word
        for (Map.Entry<String, List<String>> entry : dict.entrySet()) {
            List<String> list = entry.getValue();
            if (list.size() > 1) {
                System.out.println(entry.getKey() + " -> " + list);
            }
        }
    }

    // remove leading/trailing punctuation but keep apostrophes inside words
    private static String cleanWord(String w) {
        // strip non-letter/apostrophe characters from start and end
        String s = w.replaceAll("^[^a-zA-Z']+|[^a-zA-Z']+$", "");
        return s.toLowerCase();
    }

    private static String signature(String w) {
        char[] chars = w.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}

 