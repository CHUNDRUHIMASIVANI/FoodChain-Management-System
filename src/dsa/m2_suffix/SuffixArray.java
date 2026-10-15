package dsa.m2_suffix;

import java.util.Arrays;
import java.util.Comparator;

public class SuffixArray {

    private String text;
    private int[] suffixArray;

    // Constructor
    public SuffixArray(String text) {
        this.text = text;
        this.suffixArray = buildSuffixArray(text);
    }

    // Build suffix array
    public static int[] buildSuffixArray(String text) {

        int n = text.length();

        Integer[] suffixes = new Integer[n];

        // Store starting positions
        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }

        // Sort suffixes
        Arrays.sort(suffixes, new Comparator<Integer>() {

            @Override
            public int compare(Integer a, Integer b) {

                return text.substring(a)
                        .compareTo(text.substring(b));
            }
        });

        // Convert Integer[] to int[]
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = suffixes[i];
        }

        return result;
    }

    // Get suffix array
    public int[] getSuffixArray() {
        return suffixArray;
    }

    // Get suffix at a particular index
    public String getSuffix(int index) {

        if (index < 0 || index >= suffixArray.length) {
            return null;
        }

        return text.substring(suffixArray[index]);
    }

    // Display suffix array
    public void displaySuffixArray() {

        System.out.println("Suffix Array:");

        for (int i = 0; i < suffixArray.length; i++) {

            System.out.println(
                    i + " -> "
                    + suffixArray[i]
                    + " -> "
                    + text.substring(suffixArray[i])
            );
        }
    }

    // Search for a pattern using suffix array
    public int search(String pattern) {

        int left = 0;
        int right = suffixArray.length - 1;

        while (left <= right) {

            int middle = (left + right) / 2;

            int suffixIndex = suffixArray[middle];

            String suffix = text.substring(suffixIndex);

            int comparison = comparePattern(pattern, suffix);

            if (comparison == 0) {
                return suffixIndex;
            }

            if (comparison < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return -1;
    }

    // Compare pattern with suffix
    private int comparePattern(
            String pattern, String suffix) {

        int length =
                Math.min(pattern.length(), suffix.length());

        for (int i = 0; i < length; i++) {

            if (pattern.charAt(i)
                    < suffix.charAt(i)) {

                return -1;
            }

            if (pattern.charAt(i)
                    > suffix.charAt(i)) {

                return 1;
            }
        }

        if (pattern.length() == suffix.length()) {
            return 0;
        }

        if (pattern.length() < suffix.length()) {
            return 0;
        }

        return 1;
    }

    // Return all positions where pattern occurs
    public int[] searchAll(String pattern) {

        int count = 0;

        for (int position : suffixArray) {

            if (text.startsWith(pattern, position)) {
                count++;
            }
        }

        if (count == 0) {
            return new int[0];
        }

        int[] positions = new int[count];

        int index = 0;

        for (int position : suffixArray) {

            if (text.startsWith(pattern, position)) {
                positions[index] = position;
                index++;
            }
        }

        Arrays.sort(positions);

        return positions;
    }

    // Get original text
    public String getText() {
        return text;
    }
}