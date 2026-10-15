package dsa.m4_network;

import java.util.Arrays;

public class BipartiteMatching {

    private int suppliers;
    private int warehouses;

    private boolean[][] graph;

    private int[] warehouseMatch;

    public BipartiteMatching(
            int suppliers,
            int warehouses) {

        this.suppliers = suppliers;
        this.warehouses = warehouses;

        graph =
                new boolean[suppliers][warehouses];

        warehouseMatch =
                new int[warehouses];

        Arrays.fill(
                warehouseMatch,
                -1
        );
    }

    // Add a possible supplier-warehouse assignment
    public void addConnection(
            int supplier,
            int warehouse) {

        graph[supplier][warehouse] = true;
    }

    // Find an augmenting path
    private boolean findMatch(
            int supplier,
            boolean[] visited) {

        for (int warehouse = 0;
                warehouse < warehouses;
                warehouse++) {

            if (!graph[supplier][warehouse]) {
                continue;
            }

            if (visited[warehouse]) {
                continue;
            }

            visited[warehouse] = true;

            // Warehouse is free
            if (warehouseMatch[warehouse] == -1) {

                warehouseMatch[warehouse] =
                        supplier;

                return true;
            }

            // Try moving the current supplier
            // to another warehouse
            int previousSupplier =
                    warehouseMatch[warehouse];

            if (findMatch(
                    previousSupplier,
                    visited
            )) {

                warehouseMatch[warehouse] =
                        supplier;

                return true;
            }
        }

        return false;
    }

    // Calculate maximum matching
    public int maximumMatching() {

        Arrays.fill(
                warehouseMatch,
                -1
        );

        int matching = 0;

        for (int supplier = 0;
                supplier < suppliers;
                supplier++) {

            boolean[] visited =
                    new boolean[warehouses];

            if (findMatch(
                    supplier,
                    visited
            )) {

                matching++;
            }
        }

        return matching;
    }

    // Display supplier-warehouse assignments
    public void displayMatching() {

        System.out.println(
                "Supplier-Warehouse Matching:"
        );

        boolean found = false;

        for (int warehouse = 0;
                warehouse < warehouses;
                warehouse++) {

            if (warehouseMatch[warehouse] != -1) {

                System.out.println(
                        "Supplier "
                        + warehouseMatch[warehouse]
                        + " -> Warehouse "
                        + warehouse
                );

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No matching found."
            );
        }
    }

    // Display possible connections
    public void displayConnections() {

        System.out.println(
                "Possible Supplier-Warehouse Connections:"
        );

        for (int supplier = 0;
                supplier < suppliers;
                supplier++) {

            for (int warehouse = 0;
                    warehouse < warehouses;
                    warehouse++) {

                if (graph[supplier][warehouse]) {

                    System.out.println(
                            "Supplier "
                            + supplier
                            + " -> Warehouse "
                            + warehouse
                    );
                }
            }
        }
    }

    // Get matched supplier for a warehouse
    public int getMatchedSupplier(
            int warehouse) {

        if (warehouse < 0 ||
                warehouse >= warehouses) {

            return -1;
        }

        return warehouseMatch[warehouse];
    }
}