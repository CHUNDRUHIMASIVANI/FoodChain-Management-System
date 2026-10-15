package dsa.m5_np;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

import data.DatabaseConnection;

public class SetCover {

    public static void runDemo() {
        int universeSize = 5;

        int[][] sets = {
            {0, 1},
            {1, 2, 3},
            {2, 4},
            {0, 3, 4}
        };

        int minSets = Integer.MAX_VALUE;
        int bestMask = 0;

        for (int mask = 1; mask < (1 << sets.length); mask++) {
            boolean[] covered = new boolean[universeSize];
            int count = 0;

            for (int i = 0; i < sets.length; i++) {
                if ((mask & (1 << i)) != 0) {
                    count++;

                    for (int element : sets[i]) {
                        covered[element] = true;
                    }
                }
            }

            boolean allCovered = true;
            for (boolean value : covered) {
                if (!value) {
                    allCovered = false;
                    break;
                }
            }

            if (allCovered && count < minSets) {
                minSets = count;
                bestMask = mask;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Set Cover (Brute Force)\n\n");

        if (minSets == Integer.MAX_VALUE) {
            result.append("No valid set cover found.");
        } else {
            result.append("Minimum sets required: ").append(minSets).append("\n");
            result.append("Selected sets:\n");

            for (int i = 0; i < sets.length; i++) {
                if ((bestMask & (1 << i)) != 0) {
                    result.append("Set ").append(i + 1).append(": {");

                    for (int j = 0; j < sets[i].length; j++) {
                        result.append(sets[i][j]);
                        if (j < sets[i].length - 1) {
                            result.append(", ");
                        }
                    }

                    result.append("}\n");
                }
            }
        }

        result.append("\nTime Complexity: O(2^m × U)");

        JOptionPane.showMessageDialog(
            null,
            result.toString(),
            "Set Cover Demo",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    public static void runFoodChainDemo() {

        String sql =
            "SELECT warehouse_id, warehouse_name, location FROM warehouses";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            Map<String, List<String>> warehousesByLocation =
                    new LinkedHashMap<>();

            while (rs.next()) {
                String location = rs.getString("location");
                String warehouse = rs.getString("warehouse_name");

                if (location == null || location.trim().isEmpty()) {
                    location = "Unknown";
                }

                warehousesByLocation
                    .computeIfAbsent(location.trim(), k -> new ArrayList<>())
                    .add(warehouse + " (ID: " + rs.getInt("warehouse_id") + ")");
            }

            if (warehousesByLocation.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No warehouse records found.",
                    "FoodChain Set Cover",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            StringBuilder result = new StringBuilder();
            result.append("FOODCHAIN — WAREHOUSE LOCATION COVERAGE\n\n");
            result.append("Distinct locations: ")
                  .append(warehousesByLocation.size()).append("\n\n");

            for (Map.Entry<String, List<String>> entry :
                    warehousesByLocation.entrySet()) {

                result.append("Location: ")
                      .append(entry.getKey()).append("\n");

                result.append("Warehouse(s): ")
                      .append(String.join(", ", entry.getValue()))
                      .append("\n\n");
            }

            result.append(
                "Note: This groups warehouses by their recorded location. "
                + "A true Set Cover solution requires data showing which "
                + "delivery regions each warehouse serves."
            );

            JOptionPane.showMessageDialog(
                null,
                result.toString(),
                "FoodChain Warehouse Coverage",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                null,
                "Could not load warehouses:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}