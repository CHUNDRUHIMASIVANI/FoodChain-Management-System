package dsa.m3_dynamic;

public class MatrixChainMultiplication {

    // Find minimum multiplication cost
    public static int minimumCost(int[] dimensions) {

        int n = dimensions.length - 1;

        int[][] dp =
                new int[n][n];

        // Chain length
        for (int length = 2;
                length <= n;
                length++) {

            for (int i = 0;
                    i <= n - length;
                    i++) {

                int j =
                        i + length - 1;

                dp[i][j] =
                        Integer.MAX_VALUE;

                // Try every possible split
                for (int k = i;
                        k < j;
                        k++) {

                    int cost =
                            dp[i][k]
                            + dp[k + 1][j]
                            + dimensions[i]
                            * dimensions[k + 1]
                            * dimensions[j + 1];

                    if (cost < dp[i][j]) {

                        dp[i][j] = cost;
                    }
                }
            }
        }

        return dp[0][n - 1];
    }

    // Find the best parenthesization
    public static String optimalOrder(
            int[] dimensions) {

        int n = dimensions.length - 1;

        int[][] dp =
                new int[n][n];

        int[][] split =
                new int[n][n];

        for (int length = 2;
                length <= n;
                length++) {

            for (int i = 0;
                    i <= n - length;
                    i++) {

                int j =
                        i + length - 1;

                dp[i][j] =
                        Integer.MAX_VALUE;

                for (int k = i;
                        k < j;
                        k++) {

                    int cost =
                            dp[i][k]
                            + dp[k + 1][j]
                            + dimensions[i]
                            * dimensions[k + 1]
                            * dimensions[j + 1];

                    if (cost < dp[i][j]) {

                        dp[i][j] = cost;
                        split[i][j] = k;
                    }
                }
            }
        }

        return buildOrder(
                split,
                0,
                n - 1
        );
    }

    // Build optimal parenthesization
    private static String buildOrder(
            int[][] split,
            int i,
            int j) {

        if (i == j) {

            return "A" + (i + 1);
        }

        int k =
                split[i][j];

        String left =
                buildOrder(
                        split,
                        i,
                        k
                );

        String right =
                buildOrder(
                        split,
                        k + 1,
                        j
                );

        return "(" + left + " × " + right + ")";
    }

    // Display DP table
    public static void displayTable(
            int[] dimensions) {

        int n =
                dimensions.length - 1;

        int[][] dp =
                new int[n][n];

        for (int length = 2;
                length <= n;
                length++) {

            for (int i = 0;
                    i <= n - length;
                    i++) {

                int j =
                        i + length - 1;

                dp[i][j] =
                        Integer.MAX_VALUE;

                for (int k = i;
                        k < j;
                        k++) {

                    int cost =
                            dp[i][k]
                            + dp[k + 1][j]
                            + dimensions[i]
                            * dimensions[k + 1]
                            * dimensions[j + 1];

                    dp[i][j] =
                            Math.min(
                                    dp[i][j],
                                    cost
                            );
                }
            }
        }

        System.out.println(
                "Matrix Chain DP Table:"
        );

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                if (i > j) {

                    System.out.print(
                            "- "
                    );

                } else {

                    System.out.print(
                            dp[i][j] + " "
                    );
                }
            }

            System.out.println();
        }
    }
}