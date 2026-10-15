package dsa.m4_network;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class EdmondsKarp {

    private int[][] capacity;
    private int[][] flow;
    private int vertices;

    public EdmondsKarp(int vertices) {

        this.vertices = vertices;

        capacity =
                new int[vertices][vertices];

        flow =
                new int[vertices][vertices];
    }

    // Add a directed edge
    public void addEdge(
            int from,
            int to,
            int capacityValue) {

        capacity[from][to] =
                capacityValue;
    }

    // Find augmenting path using BFS
    private boolean bfs(
            int source,
            int sink,
            int[] parent) {

        boolean[] visited =
                new boolean[vertices];

        Arrays.fill(parent, -1);

        Queue<Integer> queue =
                new LinkedList<>();

        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {

            int current =
                    queue.poll();

            for (int next = 0;
                    next < vertices;
                    next++) {

                int residual =
                        getResidualCapacity(
                                current,
                                next
                        );

                if (!visited[next]
                        && residual > 0) {

                    parent[next] =
                            current;

                    visited[next] = true;

                    queue.add(next);

                    if (next == sink) {
                        return true;
                    }
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

        while (bfs(
                source,
                sink,
                parent
        )) {

            int pathFlow =
                    Integer.MAX_VALUE;

            int current = sink;

            // Find bottleneck capacity
            while (current != source) {

                int previous =
                        parent[current];

                int residual =
                        getResidualCapacity(
                                previous,
                                current
                        );

                pathFlow =
                        Math.min(
                                pathFlow,
                                residual
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
                "Edmonds-Karp Flow:"
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