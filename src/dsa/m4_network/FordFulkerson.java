package dsa.m4_network;

import java.util.Arrays;

public class FordFulkerson {

    // Store the original capacities
    private int[][] capacity;

    // Store the current flow
    private int[][] flow;

    // Number of vertices
    private int vertices;

    public FordFulkerson(int vertices) {

        this.vertices = vertices;

        capacity =
                new int[vertices][vertices];

        flow =
                new int[vertices][vertices];
    }

    // Add an edge with capacity
    public void addEdge(
            int from,
            int to,
            int capacityValue) {

        capacity[from][to] =
                capacityValue;
    }

    // Find an augmenting path using DFS
    private boolean findPath(
            int source,
            int sink,
            int[] parent) {

        boolean[] visited =
                new boolean[vertices];

        Arrays.fill(parent, -1);

        return dfs(
                source,
                sink,
                visited,
                parent
        );
    }

    // DFS for finding a path
    private boolean dfs(
            int current,
            int sink,
            boolean[] visited,
            int[] parent) {

        visited[current] = true;

        if (current == sink) {
            return true;
        }

        for (int next = 0;
                next < vertices;
                next++) {

            int residualCapacity =
                    getResidualCapacity(
                            current,
                            next
                    );

            if (!visited[next]
                    && residualCapacity > 0) {

                parent[next] =
                        current;

                if (dfs(
                        next,
                        sink,
                        visited,
                        parent
                )) {

                    return true;
                }
            }
        }

        return false;
    }

    // Calculate residual capacity
    private int getResidualCapacity(
            int from,
            int to) {

        return capacity[from][to]
                - flow[from][to]
                + flow[to][from];
    }

    // Calculate maximum flow
    public int maxFlow(
            int source,
            int sink) {

        int maximumFlow = 0;

        int[] parent =
                new int[vertices];

        while (findPath(
                source,
                sink,
                parent
        )) {

            int pathFlow =
                    Integer.MAX_VALUE;

            // Find minimum capacity
            // along the path
            int current = sink;

            while (current != source) {

                int previous =
                        parent[current];

                int residualCapacity =
                        getResidualCapacity(
                                previous,
                                current
                        );

                pathFlow =
                        Math.min(
                                pathFlow,
                                residualCapacity
                        );

                current = previous;
            }

            // Update flow
            current = sink;

            while (current != source) {

                int previous =
                        parent[current];

                flow[previous][current]
                        += pathFlow;

                flow[current][previous]
                        -= pathFlow;

                current = previous;
            }

            maximumFlow += pathFlow;
        }

        return maximumFlow;
    }

    // Display final flow
    public void displayFlow() {

        System.out.println(
                "Network Flow:"
        );

        for (int i = 0;
                i < vertices;
                i++) {

            for (int j = 0;
                    j < vertices;
                    j++) {

                if (flow[i][j] > 0) {

                    System.out.println(
                            i + " -> " + j
                            + " : "
                            + flow[i][j]
                    );
                }
            }
        }
    }

    // Display capacity graph
    public void displayCapacity() {

        System.out.println(
                "Capacity Graph:"
        );

        for (int i = 0;
                i < vertices;
                i++) {

            for (int j = 0;
                    j < vertices;
                    j++) {

                if (capacity[i][j] > 0) {

                    System.out.println(
                            i + " -> " + j
                            + " : "
                            + capacity[i][j]
                    );
                }
            }
        }
    }
}