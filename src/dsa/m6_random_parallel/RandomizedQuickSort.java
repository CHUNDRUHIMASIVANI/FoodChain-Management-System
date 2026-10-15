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

public class RandomizedQuickSort {

    private static Random random =
            new Random();

    // Main sorting method
    public static void sort(int[] array) {

        if (array == null ||
                array.length <= 1) {

            return;
        }

        quickSort(
                array,
                0,
                array.length - 1
        );
    }

    // Recursive QuickSort
    private static void quickSort(
            int[] array,
            int low,
            int high) {

        if (low >= high) {
            return;
        }

        int pivotIndex =
                chooseRandomPivot(
                        low,
                        high
                );

        swap(
                array,
                pivotIndex,
                high
        );

        int partitionIndex =
                partition(
                        array,
                        low,
                        high
                );

        quickSort(
                array,
                low,
                partitionIndex - 1
        );

        quickSort(
                array,
                partitionIndex + 1,
                high
        );
    }

    // Choose a random pivot
    private static int chooseRandomPivot(
            int low,
            int high) {

        return low +
                random.nextInt(
                        high - low + 1
                );
    }

    // Partition the array
    private static int partition(
            int[] array,
            int low,
            int high) {

        int pivot =
                array[high];

        int smallerIndex =
                low - 1;

        for (int j = low;
                j < high;
                j++) {

            if (array[j] <= pivot) {

                smallerIndex++;

                swap(
                        array,
                        smallerIndex,
                        j
                );
            }
        }

        swap(
                array,
                smallerIndex + 1,
                high
        );

        return smallerIndex + 1;
    }

    // Swap two elements
    private static void swap(
            int[] array,
            int first,
            int second) {

        int temp =
                array[first];

        array[first] =
                array[second];

        array[second] =
                temp;
    }

    // Display array
    public static void display(
            int[] array) {

        for (int value : array) {

            System.out.print(
                    value + " "
            );
        }

        System.out.println();
    }
    public static void runDemo() {

        int[] array = {45, 12, 78, 3, 56, 23, 9, 34};

        StringBuilder result = new StringBuilder();

        result.append("Randomized Quick Sort\n\n");
        result.append("Original Array:\n");
        result.append(java.util.Arrays.toString(array));
        result.append("\n\n");

        sort(array);

        result.append("Sorted Array:\n");
        result.append(java.util.Arrays.toString(array));
        result.append("\n\n");
        result.append("Average Time Complexity: O(n log n)\n");
        result.append("Worst-case Time Complexity: O(n²)");

        javax.swing.JOptionPane.showMessageDialog(
            null,
            result.toString(),
            "Randomized Quick Sort Demo",
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
                    "No shipment records found.",
                    "FoodChain Shipments",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            int[] ids = new int[shipmentIds.size()];

            for (int i = 0; i < shipmentIds.size(); i++) {
                ids[i] = shipmentIds.get(i);
            }

            String original = java.util.Arrays.toString(ids);

            RandomizedQuickSort.sort(ids);

            String message =
                "FOODCHAIN — RANDOMIZED QUICK SORT\n\n"
                + "Shipment records: " + ids.length + "\n\n"
                + "Original shipment IDs:\n" + original + "\n\n"
                + "Sorted shipment IDs:\n"
                + java.util.Arrays.toString(ids);

            JOptionPane.showMessageDialog(
                null,
                message,
                "FoodChain Shipment Sorting",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                null,
                "Could not load shipment records:\n"
                    + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}