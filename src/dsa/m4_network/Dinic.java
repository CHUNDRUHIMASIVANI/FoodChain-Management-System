package dsa.m4_network;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Dinic {

    private int[][] capacity;
    private int[][] flow;

    private int[] level;
    private int[] next;

    private int vertices;

    public Dinic(int vertices) {

        this.vertices = vertices;

        capacity =
                new int[vertices][vertices];

        flow =
                new int[vertices][vertices];

        level =
                new int[vertices];

        next =
                new int[vertices];
    }

    // Add directed edge
    public void addEdge(
            int from,
            int to,
            int capacityValue) {

        capacity[from][to] =
                capacityValue;
    }

    // BFS creates level graph
    private boolean buildLevelGraph(
            int source,
            int sink) {

        Arrays.fill(level, -1);

        Queue<Integer> queue =
                new LinkedList<>();

        level[source] = 0;
        queue.add(source);

        while (!queue.isEmpty()) {

            int current =
                    queue.poll();

            for (int nextVertex = 0;
                    nextVertex < vertices;
                    nextVertex++) {

                int residual =
                        getResidualCapacity(
                                current,
                                nextVertex
                        );

                if (residual > 0
                        && level[nextVertex] == -1) {

                    level[nextVertex] =
                            level[current] + 1;

                    queue.add(nextVertex);
                }
            }
        }

        return level[sink] != -1;
    }

    // Calculate residual capacity
    private int getResidualCapacity(
            int from,
            int to) {

        return capacity[from][to]
                - flow[from][to]
                + flow[to][from];
    }

    // DFS sends flow through level graph
    private int sendFlow(
            int current,
            int sink,
            int availableFlow) {

        if (current == sink) {
            return availableFlow;
        }

        for (; next[current] < vertices;
                next[current]++) {

            int nextVertex =
                    next[current];

            int residual =
                    getResidualCapacity(
                            current,
                            nextVertex
                    );

            if (residual <= 0) {
                continue;
            }

            if (level[nextVertex]
                    != level[current] + 1) {

                continue;
            }

            int flowSent =
                    sendFlow(
                            nextVertex,
                            sink,
                            Math.min(
                                    availableFlow,
                                    residual
                            )
                    );

            if (flowSent > 0) {

                flow[current][nextVertex]
                        += flowSent;

                flow[nextVertex][current]
                        -= flowSent;

                return flowSent;
            }
        }

        return 0;
    }

    // Calculate maximum flow
    public int maxFlow(
            int source,
            int sink) {

        int maximumFlow = 0;

        while (buildLevelGraph(
                source,
                sink)) {

            Arrays.fill(next, 0);

            while (true) {

                int flowSent =
                        sendFlow(
                                source,
                                sink,
                                Integer.MAX_VALUE
                        );

                if (flowSent == 0) {
                    break;
                }

                maximumFlow += flowSent;
            }
        }

        return maximumFlow;
    }

    // Display level graph
    public void displayLevels() {

        System.out.println(
                "Level Graph:"
        );

        for (int i = 0;
                i < vertices;
                i++) {

            System.out.println(
                    "Vertex " + i
                    + " -> Level "
                    + level[i]
            );
        }
    }

    // Display final flow
    public void displayFlow() {

        System.out.println(
                "Dinic Flow:"
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