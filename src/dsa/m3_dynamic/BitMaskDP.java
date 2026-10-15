package dsa.m3_dynamic;

import java.util.Arrays;

public class BitMaskDP {

    private static final int INF = 1000000000;

    // Find minimum assignment cost
    //
    // cost[i][j] =
    // cost of assigning worker/supplier i
    // to warehouse/task j
    public static int minimumAssignmentCost(
            int[][] cost) {

        int n = cost.length;

        int totalMasks = 1 << n;

        int[] dp =
                new int[totalMasks];

        Arrays.fill(dp, INF);

        // No supplier/task selected
        dp[0] = 0;

        for (int mask = 0;
                mask < totalMasks;
                mask++) {

            if (dp[mask] == INF) {
                continue;
            }

            // Number of selected items
            int assigned =
                    Integer.bitCount(mask);

            if (assigned >= n) {
                continue;
            }

            // Try assigning the next supplier
            // to every unused warehouse
            for (int warehouse = 0;
                    warehouse < n;
                    warehouse++) {

                // Check whether warehouse is unused
                if ((mask & (1 << warehouse)) == 0) {

                    int newMask =
                            mask | (1 << warehouse);

                    int newCost =
                            dp[mask]
                            + cost[assigned][warehouse];

                    if (newCost < dp[newMask]) {

                        dp[newMask] =
                                newCost;
                    }
                }
            }
        }

        return dp[totalMasks - 1];
    }

    // Display DP values
    public static void displayDP(
            int[][] cost) {

        int n = cost.length;

        int totalMasks = 1 << n;

        int[] dp =
                new int[totalMasks];

        Arrays.fill(dp, INF);

        dp[0] = 0;

        for (int mask = 0;
                mask < totalMasks;
                mask++) {

            if (dp[mask] == INF) {
                continue;
            }

            int assigned =
                    Integer.bitCount(mask);

            if (assigned >= n) {
                continue;
            }

            for (int warehouse = 0;
                    warehouse < n;
                    warehouse++) {

                if ((mask &
                        (1 << warehouse)) == 0) {

                    int newMask =
                            mask |
                            (1 << warehouse);

                    int newCost =
                            dp[mask]
                            + cost[
                                assigned
                            ][warehouse];

                    if (newCost <
                            dp[newMask]) {

                        dp[newMask] =
                                newCost;
                    }
                }
            }
        }

        System.out.println(
                "Bitmask DP:"
        );

        for (int mask = 0;
                mask < totalMasks;
                mask++) {

            String binary =
                    String.format(
                            "%" + n + "s",
                            Integer.toBinaryString(mask)
                    ).replace(' ', '0');

            if (dp[mask] == INF) {

                System.out.println(
                        binary + " -> INF"
                );

            } else {

                System.out.println(
                        binary + " -> "
                        + dp[mask]
                );
            }
        }
    }

    // Find the selected warehouse for each supplier
    public static int[] getAssignment(
            int[][] cost) {

        int n = cost.length;

        int totalMasks = 1 << n;

        int[] dp =
                new int[totalMasks];

        int[][] parent =
                new int[totalMasks][2];

        for (int i = 0; i < totalMasks; i++) {

            dp[i] = INF;
            parent[i][0] = -1;
            parent[i][1] = -1;
        }

        dp[0] = 0;

        for (int mask = 0;
                mask < totalMasks;
                mask++) {

            if (dp[mask] == INF) {
                continue;
            }

            int supplier =
                    Integer.bitCount(mask);

            if (supplier >= n) {
                continue;
            }

            for (int warehouse = 0;
                    warehouse < n;
                    warehouse++) {

                if ((mask &
                        (1 << warehouse)) == 0) {

                    int newMask =
                            mask |
                            (1 << warehouse);

                    int newCost =
                            dp[mask]
                            + cost[supplier][warehouse];

                    if (newCost <
                            dp[newMask]) {

                        dp[newMask] =
                                newCost;

                        parent[newMask][0] =
                                mask;

                        parent[newMask][1] =
                                warehouse;
                    }
                }
            }
        }

        int[] assignment =
                new int[n];

        int mask =
                totalMasks - 1;

        for (int supplier = n - 1;
                supplier >= 0;
                supplier--) {

            assignment[supplier] =
                    parent[mask][1];

            mask =
                    parent[mask][0];
        }

        return assignment;
    }
}