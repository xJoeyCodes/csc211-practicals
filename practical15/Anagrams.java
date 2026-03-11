import java.io.*;
import java.util.*;

public class Anagrams {

    private static String signature(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(c);
            }
        }
        char[] chars = sb.toString().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private static String clean(String w) {
        return w.replaceAll("^[-0-9(),.;:_!?\\[\\]]+", "")
                .replaceAll("[-0-9(),.;:_!?\\[\\]]+$", "")
                .toLowerCase()
                .trim();
    }

    public static void main(String[] args) {
        String inputFile = "ulysses.text";

        if (args.length == 1) {
            inputFile = args[0];
        }

        System.out.println("Reading: " + inputFile);

        Map<String, List<String>> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), "ISO-8859-1"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String w : words) {
                    if (w.isEmpty()) continue;

                    String cleaned = clean(w);
                    if (cleaned.isEmpty() || cleaned.length() < 2) continue;

                    String sig = signature(cleaned);

                    map.computeIfAbsent(sig, k -> new ArrayList<>()).add(cleaned);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputFile);
            System.err.println("Current directory: " + new File(".").getAbsolutePath());
            return;
        } catch (IOException e) {
            System.err.println("IO error reading file: " + e.getMessage());
            return;
        }

        List<String> lines = new ArrayList<>();

        for (List<String> group : map.values()) {
            if (group.size() < 2) continue;

            Collections.sort(group);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < group.size(); i++) {
                if (i > 0) sb.append(" ");
                sb.append(group.get(i));
            }
            String base = sb.toString();
            lines.add(base + "\\\\");

            String current = base;
            for (int i = 1; i < group.size(); i++) {
                int spacePos = current.indexOf(' ');
                if (spacePos < 0) break;
                String rotated = current.substring(spacePos + 1) + " " + current.substring(0, spacePos);
                lines.add(rotated + "\\\\");
                current = rotated;
            }
        }

        Collections.sort(lines);

        File dir = new File("latex");
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Cannot create folder: latex");
            return;
        }

        String outPath = "latex/theAnagrams.tex";

        try (PrintWriter pw = new PrintWriter(new FileWriter(outPath))) {
            char prevLetter = 0;

            for (String line : lines) {
                if (line.length() < 3) continue;

                char first = Character.toUpperCase(line.charAt(0));

                if (first != prevLetter && Character.isLetter(first)) {
                    if (prevLetter != 0) {
                        pw.println();
                    }
                    pw.printf("\n\\vspace{14pt}\n\\noindent\\textbf{\\Large %c}\\\\[+12pt]\n", first);
                    prevLetter = first;
                }

                pw.println(line);
            }

            System.out.println("Written: " + outPath);
            System.out.println("Number of anagram lines (including rotations): " + lines.size());

        } catch (IOException e) {
            System.err.println("Error writing " + outPath + ": " + e.getMessage());
        }
    }
}