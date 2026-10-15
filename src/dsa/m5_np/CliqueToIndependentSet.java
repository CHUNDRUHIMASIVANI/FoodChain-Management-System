package dsa.m5_np;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import data.DatabaseConnection;

public class CliqueToIndependentSet {

    private boolean[][] graph;
    private boolean[][] complement;

    private int vertices;

    public CliqueToIndependentSet(
            int vertices) {

        this.vertices = vertices;

        graph =
                new boolean[vertices][vertices];

        complement =
                new boolean[vertices][vertices];
    }

    // Add an edge to the original graph
    public void addEdge(
            int first,
            int second) {

        graph[first][second] = true;
        graph[second][first] = true;
    }

    // Create complement graph
    public void buildComplement() {

        for (int i = 0;
                i < vertices;
                i++) {

            for (int j = 0;
                    j < vertices;
                    j++) {

                if (i == j) {

                    complement[i][j] = false;

                } else {

                    complement[i][j] =
                            !graph[i][j];
                }
            }
        }
    }

    // Check whether a set of vertices
    // is an independent set
    private boolean isIndependent(
            List<Integer> selected) {

        for (int i = 0;
                i < selected.size();
                i++) {

            for (int j = i + 1;
                    j < selected.size();
                    j++) {

                int first =
                        selected.get(i);

                int second =
                        selected.get(j);

                if (complement[first][second]) {

                    return false;
                }
            }
        }

        return true;
    }

    // Find an independent set
    // of required size
    public List<Integer> findIndependentSet(
            int requiredSize) {

        List<Integer> selected =
                new ArrayList<>();

        if (findSet(
                0,
                requiredSize,
                selected)) {

            return new ArrayList<>(
                    selected
            );
        }

        return new ArrayList<>();
    }

    // Backtracking search
    private boolean findSet(
            int start,
            int requiredSize,
            List<Integer> selected) {

        if (selected.size()
                == requiredSize) {

            return true;
        }

        for (int vertex = start;
                vertex < vertices;
                vertex++) {

            selected.add(vertex);

            if (isIndependent(
                    selected)) {

                if (findSet(
                        vertex + 1,
                        requiredSize,
                        selected)) {

                    return true;
                }
            }

            selected.remove(
                    selected.size() - 1
            );
        }

        return false;
    }

    // Display original graph
    public void displayGraph() {

        System.out.println(
                "Original Graph:"
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

    // Display complement graph
    public void displayComplement() {

        System.out.println(
                "Complement Graph:"
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

                if (complement[i][j]) {

                    System.out.print(
                            j + " "
                    );
                }
            }

            System.out.println();
        }
    }

    // Display independent set
    public void displayIndependentSet(
            List<Integer> independentSet) {

        if (independentSet.isEmpty()) {

            System.out.println(
                    "No required independent set found."
            );

            return;
        }

        System.out.println(
                "Independent Set:"
        );

        for (int vertex :
                independentSet) {

            System.out.print(
                    vertex + " "
            );
        }

        System.out.println();
    }

    public boolean[][] getComplement() {
        return complement;
    }
    public static void runDemo() {
        CliqueToIndependentSet demo =
                new CliqueToIndependentSet(5);

        demo.addEdge(0, 1);
        demo.addEdge(0, 2);
        demo.addEdge(1, 2);
        demo.addEdge(1, 3);
        demo.addEdge(2, 3);
        demo.addEdge(3, 4);

        demo.buildComplement();

        List<Integer> independentSet =
                demo.findIndependentSet(2);

        StringBuilder result = new StringBuilder();

        result.append("Clique to Independent Set Demo\n\n");
        result.append("Graph vertices: 5\n");
        result.append("Required independent set size: 2\n\n");

        if (independentSet.isEmpty()) {
            result.append("No independent set of the required size found.");
        } else {
            result.append("Independent set found: ")
                  .append(independentSet);
        }

        result.append("\n\nRelationship: An independent set in a graph's ")
              .append("complement corresponds to a clique in the original graph.");

        JOptionPane.showMessageDialog(
                null,
                result.toString(),
                "Clique to Independent Set",
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
            List<String> records = new ArrayList<>();

            while (rs.next()) {
                records.add(
                    "Warehouse " + rs.getInt("warehouse_id") +
                    " - Product " + rs.getInt("product_id")
                );
            }

            if (records.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No inventory records found.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            StringBuilder output = new StringBuilder();
            output.append("FOODCHAIN INVENTORY GRAPH\n\n");
            output.append("Inventory records: ")
                  .append(records.size()).append("\n\n");

            for (String record : records) {
                output.append(record).append("\n");
            }

            output.append(
                "\nThese records can be used to construct a graph. " +
                "A valid Clique-to-Independent-Set reduction requires " +
                "an explicitly defined graph and its complement."
            );

            JOptionPane.showMessageDialog(
                null,
                output.toString(),
                "Clique to Independent Set",
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