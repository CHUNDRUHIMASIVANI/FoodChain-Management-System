package dsa.m6_random_parallel;

import java.util.Random;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import data.DatabaseConnection;

public class ReservoirSampling {

    private static Random random =
            new Random();

    // Select k random elements
    // from an integer array
    public static int[] sample(
            int[] data,
            int k) {

        if (data == null ||
                k <= 0 ||
                k > data.length) {

            return new int[0];
        }

        int[] reservoir =
                new int[k];

        // First k elements
        // are placed into reservoir
        for (int i = 0;
                i < k;
                i++) {

            reservoir[i] =
                    data[i];
        }

        // Process remaining elements
        for (int i = k;
                i < data.length;
                i++) {

            int randomIndex =
                    random.nextInt(i + 1);

            // Replace an existing
            // element with probability k/i
            if (randomIndex < k) {

                reservoir[randomIndex] =
                        data[i];
            }
        }

        return reservoir;
    }

    // Display sample
    public static void displaySample(
            int[] sample) {

        System.out.println(
                "Random Sample:"
        );

        for (int value : sample) {

            System.out.print(
                    value + " "
            );
        }

        System.out.println();
    }
    public static void runDemo() {

        int[] data = {
            10, 20, 30, 40, 50,
            60, 70, 80, 90, 100
        };

        int k = 3;

        int[] selected = sample(data, k);

        StringBuilder result = new StringBuilder();

        result.append("Reservoir Sampling Demo\n\n");
        result.append("Input data:\n");
        result.append(java.util.Arrays.toString(data));
        result.append("\n\nSample size: ").append(k);
        result.append("\nSelected random elements:\n");
        result.append(java.util.Arrays.toString(selected));
        result.append("\n\nAlgorithm: Reservoir Sampling");
        result.append("\nTime Complexity: O(n)");
        result.append("\nExtra Space: O(k)");

        javax.swing.JOptionPane.showMessageDialog(
            null,
            result.toString(),
            "Reservoir Sampling",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }
    public static void runFoodChainDemo() {

        String sql = "SELECT shipment_id FROM shipments";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            List<Integer> shipmentIds = new ArrayList<>();

            while (rs.next()) {
                shipmentIds.add(rs.getInt("shipment_id"));
            }

            if (shipmentIds.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No shipments found for inspection.",
                    "FoodChain Quality Inspection",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            int k = Math.min(3, shipmentIds.size());

            int[] data = new int[shipmentIds.size()];

            for (int i = 0; i < shipmentIds.size(); i++) {
                data[i] = shipmentIds.get(i);
            }

            int[] selected = sample(data, k);

            String message =
                "FOODCHAIN — RANDOM SHIPMENT INSPECTION\n\n"
                + "Total shipments: " + data.length + "\n"
                + "Shipments selected for inspection: " + k + "\n\n"
                + "Selected shipment IDs:\n"
                + java.util.Arrays.toString(selected)
                + "\n\nSelection method: Reservoir Sampling";

            JOptionPane.showMessageDialog(
                null,
                message,
                "FoodChain Quality Inspection",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                null,
                "Could not load shipments:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}