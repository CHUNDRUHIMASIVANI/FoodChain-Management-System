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

public class ThreeSATToClique {

    // Represents one literal
    // from one clause
    public static class LiteralNode {

        int clause;
        int literal;

        public LiteralNode(
                int clause,
                int literal) {

            this.clause = clause;
            this.literal = literal;
        }

        @Override
        public String toString() {

            if (literal < 0) {

                return "C"
                        + (clause + 1)
                        + ": NOT X"
                        + Math.abs(literal);

            } else {

                return "C"
                        + (clause + 1)
                        + ": X"
                        + literal;
            }
        }
    }

    // Graph representation
    private boolean[][] graph;

    private LiteralNode[] nodes;

    // Convert 3-SAT formula into graph
    public void buildGraph(
            int[][] clauses) {

        int clauseCount =
                clauses.length;

        int nodeCount =
                clauseCount * 3;

        graph =
                new boolean[nodeCount][nodeCount];

        nodes =
                new LiteralNode[nodeCount];

        int index = 0;

        // Create one vertex for
        // every literal
        for (int clause = 0;
                clause < clauseCount;
                clause++) {

            for (int j = 0;
                    j < 3;
                    j++) {

                nodes[index] =
                        new LiteralNode(
                                clause,
                                clauses[clause][j]
                        );

                index++;
            }
        }

        // Add edges between
        // compatible literals
        for (int i = 0;
                i < nodeCount;
                i++) {

            for (int j = i + 1;
                    j < nodeCount;
                    j++) {

                if (isCompatible(
                        nodes[i],
                        nodes[j])) {

                    graph[i][j] = true;
                    graph[j][i] = true;
                }
            }
        }
    }

    // Two literals are compatible if:
    //
    // 1. They belong to different clauses.
    // 2. They are not opposite literals.
    private boolean isCompatible(
            LiteralNode first,
            LiteralNode second) {

        if (first.clause ==
                second.clause) {

            return false;
        }

        if (first.literal ==
                -second.literal) {

            return false;
        }

        return true;
    }

    // Check whether two vertices
    // are connected
    public boolean hasEdge(
            int first,
            int second) {

        if (graph == null) {
            return false;
        }

        return graph[first][second];
    }

    // Find a clique of required size
    public List<LiteralNode> findClique(
            int requiredSize) {

        List<Integer> selected =
                new ArrayList<>();

        if (findCliqueRecursive(
                0,
                requiredSize,
                selected)) {

            List<LiteralNode> result =
                    new ArrayList<>();

            for (int index : selected) {

                result.add(
                        nodes[index]
                );
            }

            return result;
        }

        return new ArrayList<>();
    }

    // Backtracking search for clique
    private boolean findCliqueRecursive(
            int start,
            int requiredSize,
            List<Integer> selected) {

        if (selected.size()
                == requiredSize) {

            return true;
        }

        for (int i = start;
                i < nodes.length;
                i++) {

            if (canAdd(
                    i,
                    selected)) {

                selected.add(i);

                if (findCliqueRecursive(
                        i + 1,
                        requiredSize,
                        selected)) {

                    return true;
                }

                selected.remove(
                        selected.size() - 1
                );
            }
        }

        return false;
    }

    // Check whether a vertex can be
    // added to the current clique
    private boolean canAdd(
            int vertex,
            List<Integer> selected) {

        for (int existing :
                selected) {

            if (!graph[vertex][existing]) {
                return false;
            }
        }

        return true;
    }

    // Display graph
    public void displayGraph() {

        if (graph == null) {

            System.out.println(
                    "Graph has not been built."
            );

            return;
        }

        System.out.println(
                "3-SAT to CLIQUE Graph:"
        );

        for (int i = 0;
                i < nodes.length;
                i++) {

            System.out.print(
                    i + " -> "
                    + nodes[i]
                    + " : "
            );

            for (int j = 0;
                    j < nodes.length;
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

    // Display a found clique
    public void displayClique(
            List<LiteralNode> clique) {

        if (clique.isEmpty()) {

            System.out.println(
                    "No required clique found."
            );

            return;
        }

        System.out.println(
                "Clique:"
        );

        for (LiteralNode node :
                clique) {

            System.out.println(
                    node
            );
        }
    }

    // Get number of graph vertices
    public int getVertexCount() {

        if (nodes == null) {
            return 0;
        }

        return nodes.length;
    }
    public static void runDemo() {

        ThreeSATToClique demo = new ThreeSATToClique();

        // Example 3-SAT formula:
        // (X1 OR X2 OR X3)
        // (NOT X1 OR X2 OR X3)
        // (X1 OR NOT X2 OR X3)

        int[][] clauses = {
            {1, 2, 3},
            {-1, 2, 3},
            {1, -2, 3}
        };

        demo.buildGraph(clauses);

        List<LiteralNode> clique = demo.findClique(clauses.length);

        StringBuilder result = new StringBuilder();

        result.append("3-SAT to CLIQUE Reduction\n\n");
        result.append("Number of clauses: ").append(clauses.length).append("\n");
        result.append("Graph vertices: ").append(demo.getVertexCount()).append("\n\n");

        result.append("Formula:\n");
        for (int i = 0; i < clauses.length; i++) {
            result.append("Clause ").append(i + 1).append(": ");

            for (int j = 0; j < 3; j++) {
                int literal = clauses[i][j];

                if (literal < 0) {
                    result.append("NOT X").append(Math.abs(literal));
                } else {
                    result.append("X").append(literal);
                }

                if (j < 2) {
                    result.append(" OR ");
                }
            }

            result.append("\n");
        }

        result.append("\n");

        if (clique.size() == clauses.length) {
            result.append("Clique of size ")
                  .append(clique.size())
                  .append(" found!\n\n");

            result.append("Selected vertices:\n");

            for (LiteralNode node : clique) {
                result.append(node).append("\n");
            }

            result.append("\nThe formula is SAT.");
        } else {
            result.append("No clique of size ")
                  .append(clauses.length)
                  .append(" found.\n");

            result.append("The formula is UNSAT.");
        }

        result.append("\n\nTime Complexity: Exponential in the worst case.");

        JOptionPane.showMessageDialog(
            null,
            result.toString(),
            "3-SAT to CLIQUE Demo",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    public static void runFoodChainDemo() {

        String sql =
            "SELECT order_id, product_id, quantity, status FROM orders";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            List<String> orderRecords = new ArrayList<>();

            while (rs.next()) {
                orderRecords.add(
                    "Order ID: " + rs.getInt("order_id") +
                    "\nProduct ID: " + rs.getInt("product_id") +
                    "\nQuantity: " + rs.getInt("quantity") +
                    "\nStatus: " + rs.getString("status") + "\n"
                );
            }

            if (orderRecords.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No orders found in the database.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            StringBuilder output = new StringBuilder();
            output.append("FOODCHAIN ORDERS\n\n");
            output.append("Total records: ")
                  .append(orderRecords.size()).append("\n\n");

            for (String order : orderRecords) {
                output.append(order).append("--------------------\n");
            }

            output.append(
                "\nThese order records are loaded from the database. " +
                "To perform a meaningful 3-SAT-to-CLIQUE reduction, " +
                "we need to define a Boolean formula representing a " +
                "specific FoodChain constraint."
            );

            JOptionPane.showMessageDialog(
                null,
                output.toString(),
                "3-SAT to CLIQUE Reduction",
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