package dsa.m1_string;

public class RabinKarp {

    private static final int BASE = 256;
    private static final int PRIME = 101;

    // Search pattern in text
    public static int search(String text, String pattern) {

        int textLength = text.length();
        int patternLength = pattern.length();

        if (patternLength == 0) {
            return 0;
        }

        if (patternLength > textLength) {
            return -1;
        }

        int patternHash = 0;
        int textHash = 0;
        int highestPower = 1;

        // Calculate BASE^(patternLength - 1)
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower =
                    (highestPower * BASE) % PRIME;
        }

        // Calculate initial hashes
        for (int i = 0; i < patternLength; i++) {

            patternHash =
                    (BASE * patternHash + pattern.charAt(i))
                    % PRIME;

            textHash =
                    (BASE * textHash + text.charAt(i))
                    % PRIME;
        }

        // Slide pattern over text
        for (int i = 0;
             i <= textLength - patternLength;
             i++) {

            // If hash values match, compare characters
            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < patternLength; j++) {

                    if (text.charAt(i + j)
                            != pattern.charAt(j)) {

                        match = false;
                        break;
                    }
                }

                if (match) {
                    return i;
                }
            }

            // Calculate next window hash
            if (i < textLength - patternLength) {

                textHash =
                        (BASE * (textHash
                        - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength))
                        % PRIME;

                if (textHash < 0) {
                    textHash = textHash + PRIME;
                }
            }
        }

        return -1;
    }
}