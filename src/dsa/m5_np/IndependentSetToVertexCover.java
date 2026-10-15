package dsa.m5_np;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import data.DatabaseConnection;

import javax.swing.JOptionPane;

public class IndependentSetToVertexCover {

    private boolean[][] graph;
    private int vertices;

    public IndependentSetToVertexCover(
            int vertices) {

        this.vertices = vertices;

        graph =
                new boolean[vertices][vertices];
    }

    // Add an edge
    public void addEdge(
            int first,
            int second) {

        graph[first][second] = true;
        graph[second][first] = true;
    }

    // Convert an independent set
    // into a vertex cover
    public List<Integer> convert(
            List<Integer> independentSet) {

        boolean[] isIndependent =
                new boolean[vertices];

        for (int vertex :
                independentSet) {

            if (vertex >= 0 &&
                    vertex < vertices) {

                isIndependent[vertex] = true;
            }
        }

        List<Integer> vertexCover =
                new ArrayList<>();

        for (int vertex = 0;
                vertex < vertices;
                vertex++) {

            if (!isIndependent[vertex]) {

                vertexCover.add(vertex);
            }
        }

        return vertexCover;
    }

    // Check whether a set is a vertex cover
    public boolean isVertexCover(
            List<Integer> vertexCover) {

        boolean[] selected =
                new boolean[vertices];

        for (int vertex :
                vertexCover) {

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

    // Check whether a set is independent
    public boolean isIndependentSet(
            List<Integer> independentSet) {

        boolean[] selected =
                new boolean[vertices];

        for (int vertex :
                independentSet) {

            if (vertex >= 0 &&
                    vertex < vertices) {

                selected[vertex] = true;
            }
        }

        for (int i = 0;
                i < vertices;
                i++) {

            if (!selected[i]) {
                continue;
            }

            for (int j = i + 1;
                    j < vertices;
                    j++) {

                if (selected[j]
                        && graph[i][j]) {

                    return false;
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

    // Display a set
    public void displaySet(
            String name,
            List<Integer> set) {

        System.out.print(
                name + ": "
        );

        for (int vertex : set) {

            System.out.print(
                    vertex + " "
            );
        }

        System.out.println();

        System.out.println(
                "Size: " + set.size()
        );
    }

    public int getVertexCount() {
        return vertices;
    }
    public static void runDemo() {

        IndependentSetToVertexCover demo =
                new IndependentSetToVertexCover(5);

        demo.addEdge(0, 1);
        demo.addEdge(0, 2);
        demo.addEdge(1, 2);
        demo.addEdge(1, 3);
        demo.addEdge(2, 4);
        demo.addEdge(3, 4);

        List<Integer> independentSet =
                new ArrayList<>();

        independentSet.add(0);
        independentSet.add(4);

        List<Integer> vertexCover =
                demo.convert(independentSet);

        StringBuilder result = new StringBuilder();

        result.append("Independent Set to Vertex Cover\n\n");
        result.append("Independent Set: ")
              .append(independentSet).append("\n");
        result.append("Vertex Cover: ")
              .append(vertexCover).append("\n\n");

        result.append("Independent Set Valid: ")
              .append(demo.isIndependentSet(independentSet))
              .append("\n");

        result.append("Vertex Cover Valid: ")
              .append(demo.isVertexCover(vertexCover))
              .append("\n");

        result.append("\nRelationship: |Independent Set| + ")
              .append("|Vertex Cover| = Number of Vertices");

        JOptionPane.showMessageDialog(
                null,
                result.toString(),
                "Independent Set to Vertex Cover",
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
            Map<Integer, Integer> productIndexes = new LinkedHashMap<>();
            List<int[]> records = new ArrayList<>();

            while (rs.next()) {
                int warehouseId = rs.getInt("warehouse_id");
                int productId = rs.getInt("product_id");

                productIndexes.putIfAbsent(
                    productId, productIndexes.size()
                );

                records.add(new int[]{warehouseId, productId});
            }

            if (productIndexes.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No inventory records found.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            int vertexCount = productIndexes.size();

            IndependentSetToVertexCover demo =
                new IndependentSetToVertexCover(vertexCount);

            // Connect products stocked in the same warehouse.
            for (int i = 0; i < records.size(); i++) {
                for (int j = i + 1; j < records.size(); j++) {

                    int warehouseA = records.get(i)[0];
                    int productA = records.get(i)[1];

                    int warehouseB = records.get(j)[0];
                    int productB = records.get(j)[1];

                    if (warehouseA == warehouseB &&
                        productA != productB) {

                        demo.addEdge(
                            productIndexes.get(productA),
                            productIndexes.get(productB)
                        );
                    }
                }
            }

            // Build a valid independent set greedily.
            List<Integer> independentSet = new ArrayList<>();

            for (int vertex = 0; vertex < vertexCount; vertex++) {

                List<Integer> candidate = new ArrayList<>(independentSet);
                candidate.add(vertex);

                if (demo.isIndependentSet(candidate)) {
                    independentSet.add(vertex);
                }
            }

            // Convert the independent set into a vertex cover.
            List<Integer> vertexCover = demo.convert(independentSet);

            StringBuilder result = new StringBuilder();

            result.append("FOODCHAIN: INDEPENDENT SET → VERTEX COVER\n\n");
            result.append("Products (vertices): ")
                  .append(vertexCount).append("\n");
            result.append("Inventory records: ")
                  .append(records.size()).append("\n\n");

            result.append("Product mapping:\n");

            for (Map.Entry<Integer, Integer> entry :
                    productIndexes.entrySet()) {

                result.append("Vertex ")
                      .append(entry.getValue())
                      .append(" = Product ID ")
                      .append(entry.getKey())
                      .append("\n");
            }

            result.append("\nIndependent Set (vertex indexes): ")
                  .append(independentSet).append("\n");

            result.append("Vertex Cover (vertex indexes): ")
                  .append(vertexCover).append("\n\n");

            result.append("Independent Set Valid: ")
                  .append(demo.isIndependentSet(independentSet))
                  .append("\n");

            result.append("Vertex Cover Valid: ")
                  .append(demo.isVertexCover(vertexCover))
                  .append("\n\n");

            result.append("Conversion relationship verified: ")
                  .append(independentSet.size() + vertexCover.size()
                          == vertexCount);

            JOptionPane.showMessageDialog(
                null,
                result.toString(),
                "FoodChain — Independent Set to Vertex Cover",
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