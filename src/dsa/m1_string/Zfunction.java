package dsa.m1_string;

public class Zfunction {

    // Create Z array
    public static int[] calculateZ(String text) {

        int n = text.length();

        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {

            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            while (i + z[i] < n &&
                   text.charAt(z[i]) == text.charAt(i + z[i])) {

                z[i]++;
            }

            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }

    // Search pattern in text
    public static int search(String text, String pattern) {

        if (pattern.length() == 0) {
            return 0;
        }

        String combined = pattern + "$" + text;

        int[] z = calculateZ(combined);

        for (int i = 0; i < z.length; i++) {

            if (z[i] == pattern.length()) {

                return i - pattern.length() - 1;
            }
        }

        return -1;
    }
}