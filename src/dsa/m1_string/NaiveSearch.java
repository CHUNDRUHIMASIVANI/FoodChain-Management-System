package dsa.m1_string;

public class NaiveSearch {

    // Search pattern in text using Naive String Matching
    public static int search(String text, String pattern) {

        if (text == null || pattern == null) {
            return -1;
        }

        if (pattern.length() == 0) {
            return 0;
        }

        if (pattern.length() > text.length()) {
            return -1;
        }

        int textLength = text.length();
        int patternLength = pattern.length();

        // Try every possible starting position
        for (int i = 0; i <= textLength - patternLength; i++) {

            int j = 0;

            // Compare pattern with current text window
            while (j < patternLength
                    && text.charAt(i + j) == pattern.charAt(j)) {

                j++;
            }

            // Complete pattern matched
            if (j == patternLength) {
                return i;
            }
        }

        // Pattern not found
        return -1;
    }
}