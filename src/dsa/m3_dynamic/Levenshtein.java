package dsa.m3_dynamic;

public class Levenshtein {

    // Calculate Levenshtein distance
    public static int distance(
            String first,
            String second) {

        int m = first.length();
        int n = second.length();

        // DP table
        int[][] dp = new int[m + 1][n + 1];

        // Convert empty first string
        // into second string
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Convert first string
        // into empty second string
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // Fill DP table
        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (first.charAt(i - 1)
                        == second.charAt(j - 1)) {

                    dp[i][j] =
                            dp[i - 1][j - 1];

                } else {

                    int insert =
                            dp[i][j - 1];

                    int delete =
                            dp[i - 1][j];

                    int replace =
                            dp[i - 1][j - 1];

                    dp[i][j] =
                            1 + Math.min(
                                    insert,
                                    Math.min(
                                            delete,
                                            replace
                                    )
                            );
                }
            }
        }

        return dp[m][n];
    }

    // Display the complete DP table
    public static void displayTable(
            String first,
            String second) {

        int m = first.length();
        int n = second.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (first.charAt(i - 1)
                        == second.charAt(j - 1)) {

                    dp[i][j] =
                            dp[i - 1][j - 1];

                } else {

                    dp[i][j] =
                            1 + Math.min(
                                    dp[i][j - 1],
                                    Math.min(
                                            dp[i - 1][j],
                                            dp[i - 1][j - 1]
                                    )
                            );
                }
            }
        }

        System.out.println(
                "Levenshtein DP Table:"
        );

        for (int i = 0; i <= m; i++) {

            for (int j = 0; j <= n; j++) {

                System.out.print(
                        dp[i][j] + " "
                );
            }

            System.out.println();
        }
    }

    // Check whether two strings are similar
    // within a given distance
    public static boolean isSimilar(
            String first,
            String second,
            int maximumDistance) {

        return distance(first, second)
                <= maximumDistance;
    }
}