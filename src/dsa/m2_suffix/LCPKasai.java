package dsa.m2_suffix;

public class LCPKasai {

    private String text;
    private int[] suffixArray;
    private int[] lcpArray;

    // Constructor
    public LCPKasai(String text, int[] suffixArray) {

        this.text = text;
        this.suffixArray = suffixArray;

        this.lcpArray =
                buildLCP(text, suffixArray);
    }

    // Build LCP array using Kasai's algorithm
    public static int[] buildLCP(
            String text, int[] suffixArray) {

        int n = text.length();

        int[] lcp = new int[n];

        // Rank of each suffix
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[suffixArray[i]] = i;
        }

        int commonLength = 0;

        for (int i = 0; i < n; i++) {

            int currentRank = rank[i];

            // First suffix has no previous suffix
            if (currentRank == 0) {
                commonLength = 0;
                continue;
            }

            int previousSuffix =
                    suffixArray[currentRank - 1];

            // Compare suffixes
            while (i + commonLength < n &&
                    previousSuffix + commonLength < n &&
                    text.charAt(i + commonLength)
                    == text.charAt(
                            previousSuffix + commonLength)) {

                commonLength++;
            }

            lcp[currentRank] = commonLength;

            // Reuse previous comparison
            if (commonLength > 0) {
                commonLength--;
            }
        }

        return lcp;
    }

    // Get LCP array
    public int[] getLCPArray() {
        return lcpArray;
    }

    // Get suffix array
    public int[] getSuffixArray() {
        return suffixArray;
    }

    // Display suffix and LCP information
    public void displayLCP() {

        System.out.println("Suffix Array and LCP:");

        for (int i = 0;
                i < suffixArray.length;
                i++) {

            int position = suffixArray[i];

            System.out.println(
                    "Index: " + i
                    + ", Position: " + position
                    + ", LCP: " + lcpArray[i]
                    + ", Suffix: "
                    + text.substring(position)
            );
        }
    }

    // Find longest repeated substring
    public String longestRepeatedSubstring() {

        int maxLength = 0;
        int position = -1;

        for (int i = 1;
                i < lcpArray.length;
                i++) {

            if (lcpArray[i] > maxLength) {

                maxLength = lcpArray[i];
                position = suffixArray[i];
            }
        }

        if (maxLength == 0) {
            return "";
        }

        return text.substring(
                position,
                position + maxLength
        );
    }

    // Get original text
    public String getText() {
        return text;
    }
}