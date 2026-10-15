package dsa.m3_dynamic;

public class OptimalBST {

    // Find minimum search cost
    public static int minimumCost(
            int[] keys,
            int[] frequency) {

        int n = keys.length;

        if (n == 0) {
            return 0;
        }

        int[][] dp =
                new int[n][n];

        // One key
        for (int i = 0; i < n; i++) {
            dp[i][i] = frequency[i];
        }

        // Different tree lengths
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

                int frequencySum =
                        sumFrequency(
                                frequency,
                                i,
                                j
                        );

                // Try every key as root
                for (int root = i;
                        root <= j;
                        root++) {

                    int leftCost = 0;
                    int rightCost = 0;

                    if (root > i) {
                        leftCost =
                                dp[i][root - 1];
                    }

                    if (root < j) {
                        rightCost =
                                dp[root + 1][j];
                    }

                    int cost =
                            leftCost
                            + rightCost
                            + frequencySum;

                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                    }
                }
            }
        }

        return dp[0][n - 1];
    }

    // Find the root arrangement
    public static int[][] buildOptimalTree(
            int[] keys,
            int[] frequency) {

        int n = keys.length;

        int[][] dp =
                new int[n][n];

        int[][] root =
                new int[n][n];

        for (int i = 0; i < n; i++) {

            dp[i][i] =
                    frequency[i];

            root[i][i] =
                    i;
        }

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

                int frequencySum =
                        sumFrequency(
                                frequency,
                                i,
                                j
                        );

                for (int r = i;
                        r <= j;
                        r++) {

                    int leftCost = 0;
                    int rightCost = 0;

                    if (r > i) {
                        leftCost =
                                dp[i][r - 1];
                    }

                    if (r < j) {
                        rightCost =
                                dp[r + 1][j];
                    }

                    int cost =
                            leftCost
                            + rightCost
                            + frequencySum;

                    if (cost < dp[i][j]) {

                        dp[i][j] = cost;
                        root[i][j] = r;
                    }
                }
            }
        }

        return root;
    }

    // Calculate frequency sum
    private static int sumFrequency(
            int[] frequency,
            int start,
            int end) {

        int sum = 0;

        for (int i = start;
                i <= end;
                i++) {

            sum += frequency[i];
        }

        return sum;
    }

    // Display optimal root structure
    public static void displayTree(
            int[] keys,
            int[] frequency) {

        int[][] root =
                buildOptimalTree(
                        keys,
                        frequency
                );

        System.out.println(
                "Optimal BST Structure:"
        );

        displaySubtree(
                root,
                keys,
                0,
                keys.length - 1,
                0
        );
    }

    // Display tree recursively
    private static void displaySubtree(
            int[][] root,
            int[] keys,
            int start,
            int end,
            int level) {

        if (start > end) {
            return;
        }

        int rootIndex =
                root[start][end];

        for (int i = 0;
                i < level;
                i++) {

            System.out.print("  ");
        }

        System.out.println(
                "Key: " + keys[rootIndex]
        );

        displaySubtree(
                root,
                keys,
                start,
                rootIndex - 1,
                level + 1
        );

        displaySubtree(
                root,
                keys,
                rootIndex + 1,
                end,
                level + 1
        );
    }

    // Display DP table
    public static void displayTable(
            int[] keys,
            int[] frequency) {

        int n = keys.length;

        int[][] dp =
                new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] =
                    frequency[i];
        }

        for (int length = 2;
                length <= n;
                length++) {

            for (int i = 0;
                    i <= n - length;
                    i++) {

                int j =
                        i + length - 1;

                int frequencySum =
                        sumFrequency(
                                frequency,
                                i,
                                j
                        );

                dp[i][j] =
                        Integer.MAX_VALUE;

                for (int r = i;
                        r <= j;
                        r++) {

                    int left = 0;
                    int right = 0;

                    if (r > i) {
                        left =
                                dp[i][r - 1];
                    }

                    if (r < j) {
                        right =
                                dp[r + 1][j];
                    }

                    int cost =
                            left
                            + right
                            + frequencySum;

                    dp[i][j] =
                            Math.min(
                                    dp[i][j],
                                    cost
                            );
                }
            }
        }

        System.out.println(
                "Optimal BST DP Table:"
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