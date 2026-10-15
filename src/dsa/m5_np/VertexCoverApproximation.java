package dsa.m5_np;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import data.DatabaseConnection;
import javax.swing.JOptionPane;

public class VertexCoverApproximation {

    private boolean[][] graph;
    private int vertices;

    public VertexCoverApproximation(
            int vertices) {

        this.vertices = vertices;

        graph =
                new boolean[vertices][vertices];
    }

    // Add an undirected edge
    public void addEdge(
            int first,
            int second) {

        graph[first][second] = true;
        graph[second][first] = true;
    }

    // Find a 2-approximate vertex cover
    public List<Integer> findApproximateCover() {

        boolean[][] remaining =
                copyGraph();

        boolean[] selected =
                new boolean[vertices];

        List<Integer> cover =
                new ArrayList<>();

        while (true) {

            int first = -1;
            int second = -1;

            // Find any remaining edge
            for (int i = 0;
                    i < vertices;
                    i++) {

                for (int j = i + 1;
                        j < vertices;
                        j++) {

                    if (remaining[i][j]) {

                        first = i;
                        second = j;

                        break;
                    }
                }

                if (first != -1) {
                    break;
                }
            }

            // No edges remain
            if (first == -1) {
                break;
            }

            // Select both endpoints
            if (!selected[first]) {

                selected[first] = true;
                cover.add(first);
            }

            if (!selected[second]) {

                selected[second] = true;
                cover.add(second);
            }

            // Remove all edges connected
            // to the selected vertices
            removeIncidentEdges(
                    remaining,
                    first
            );

            removeIncidentEdges(
                    remaining,
                    second
            );
        }

        return cover;
    }

    // Remove all edges incident
    // to one vertex
    private void removeIncidentEdges(
            boolean[][] graph,
            int vertex) {

        for (int i = 0;
                i < vertices;
                i++) {

            graph[vertex][i] = false;
            graph[i][vertex] = false;
        }
    }

    // Copy original graph
    private boolean[][] copyGraph() {

        boolean[][] copy =
                new boolean[vertices][vertices];

        for (int i = 0;
                i < vertices;
                i++) {

            for (int j = 0;
                    j < vertices;
                    j++) {

                copy[i][j] =
                        graph[i][j];
            }
        }

        return copy;
    }

    // Verify that a set is a vertex cover
    public boolean isVertexCover(
            List<Integer> cover) {

        boolean[] selected =
                new boolean[vertices];

        for (int vertex : cover) {

            if (vertex >= 0 &&
                    vertex < vertices) {

                selected[vertex] = true;
            }
        }

        for (int i = 0;
                i < vertices;
                i++) {

            for (int j = i + 1;
                    j < vertices;
                    j++) {

                if (graph[i][j]) {

                    if (!selected[i]
                            && !selected[j]) {

                        return false;
                    }
                }
            }
        }

        return true;
    }

    // Display graph
    public void displayGraph() {

        System.out.println(
                "Graph:"
        );

        for (int i = 0;
                i < vertices;
                i++) {

            System.out.print(
                    i + " -> "
            );

            for (int j = 0;
                    j < vertices;
                    j++) {

                if (graph[i][j]) {

                    System.out.print(
                            j + " "
                    );
                }
            }

            System.out.println();
        }
    }

    // Display vertex cover
    public void displayCover(
            List<Integer> cover) {

        System.out.println(
                "Approximate Vertex Cover:"
        );

        for (int vertex : cover) {

            System.out.print(
                    vertex + " "
            );
        }

        System.out.println();

        System.out.println(
                "Cover Size: "
                + cover.size()
        );
    }

    public int getVertexCount() {
        return vertices;
    }
    public static void runDemo() {

        VertexCoverApproximation demo =
                new VertexCoverApproximation(5);

        demo.addEdge(0, 1);
        demo.addEdge(0, 2);
        demo.addEdge(1, 2);
        demo.addEdge(1, 3);
        demo.addEdge(2, 4);
        demo.addEdge(3, 4);

        List<Integer> cover = demo.findApproximateCover();

        StringBuilder result = new StringBuilder();

        result.append("Vertex Cover Approximation (2-Approximation)\n\n");
        result.append("Number of vertices: ")
              .append(demo.getVertexCount()).append("\n");
        result.append("Approximate vertex cover: ")
              .append(cover).append("\n");
        result.append("Cover size: ")
              .append(cover.size()).append("\n");
        result.append("Valid vertex cover: ")
              .append(demo.isVertexCover(cover)).append("\n\n");
        result.append("Approximation ratio: 2\n");
        result.append("Time complexity: O(V²)");

        JOptionPane.showMessageDialog(
                null,
                result.toString(),
                "Vertex Cover Approximation",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    public static void runFoodChainDemo() {

        String sql =
            "SELECT warehouse_id, product_id " +
            "FROM inventory WHERE quantity > 0";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            List<String> inventoryRecords = new ArrayList<>();

            while (rs.next()) {
                inventoryRecords.add(
                    "Warehouse " + rs.getInt("warehouse_id") +
                    " - Product " + rs.getInt("product_id")
                );
            }

            if (inventoryRecords.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No inventory records found.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            StringBuilder output = new StringBuilder();
            output.append("FOODCHAIN INVENTORY\n\n");

            for (String record : inventoryRecords) {
                output.append(record).append("\n");
            }

            output.append(
                "\nInventory records loaded: "
            ).append(inventoryRecords.size());

            output.append(
                "\n\nNote: These are database records, not yet a graph. " +
                "To run the vertex-cover approximation meaningfully, " +
                "we must define what an edge represents."
            );

            JOptionPane.showMessageDialog(
                null,
                output.toString(),
                "Vertex Cover Approximation",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                null,
                "Database error: " + ex.getMessage(),
                "FoodChain Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}