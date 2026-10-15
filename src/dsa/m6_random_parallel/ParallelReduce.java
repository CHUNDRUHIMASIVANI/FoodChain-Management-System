package dsa.m6_random_parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import data.DatabaseConnection;

public class ParallelReduce {

    // Threshold decides when
    // the calculation becomes sequential
    private static final int THRESHOLD = 2;

    // Main parallel sum method
    public static int sum(
            int[] array) {

        if (array == null ||
                array.length == 0) {

            return 0;
        }

        ForkJoinPool pool =
                ForkJoinPool.commonPool();

        SumTask task =
                new SumTask(
                        array,
                        0,
                        array.length
                );

        return pool.invoke(task);
    }

    // Recursive task
    private static class SumTask
            extends RecursiveTask<Integer> {

        private int[] array;
        private int start;
        private int end;

        public SumTask(
                int[] array,
                int start,
                int end) {

            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {

            int length =
                    end - start;

            // Small section:
            // calculate directly
            if (length <= THRESHOLD) {

                int total = 0;

                for (int i = start;
                        i < end;
                        i++) {

                    total += array[i];
                }

                return total;
            }

            // Divide the array
            int middle =
                    (start + end) / 2;

            SumTask leftTask =
                    new SumTask(
                            array,
                            start,
                            middle
                    );

            SumTask rightTask =
                    new SumTask(
                            array,
                            middle,
                            end
                    );

            // Run left side in parallel
            leftTask.fork();

            // Calculate right side
            int rightResult =
                    rightTask.compute();

            // Get left result
            int leftResult =
                    leftTask.join();

            return leftResult
                    + rightResult;
        }
    }

    // Sequential sum for comparison
    public static int sequentialSum(
            int[] array) {

        if (array == null) {
            return 0;
        }

        int total = 0;

        for (int value : array) {

            total += value;
        }

        return total;
    }

    // Display result
    public static void displayResult(
            int result) {

        System.out.println(
                "Reduced Sum: "
                + result
        );
    }
    public static void runDemo() {

        int[] array = {
            10, 20, 30, 40, 50,
            60, 70, 80, 90, 100
        };

        int parallelResult = sum(array);
        int sequentialResult = sequentialSum(array);

        StringBuilder result = new StringBuilder();

        result.append("Parallel Reduction Demo\n\n");

        result.append("Input Array:\n");
        result.append(java.util.Arrays.toString(array));

        result.append("\n\nSequential Sum: ")
              .append(sequentialResult);

        result.append("\nParallel Sum: ")
              .append(parallelResult);

        result.append("\nResults Match: ")
              .append(parallelResult == sequentialResult);

        result.append("\n\nTechnique: ForkJoinPool");
        result.append("\nOperation: Sum of array elements");

        javax.swing.JOptionPane.showMessageDialog(
            null,
            result.toString(),
            "Parallel Reduction",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }
    public static void runFoodChainDemo() {

        String sql = "SELECT quantity FROM inventory";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            java.util.List<Integer> quantities =
                    new java.util.ArrayList<>();

            while (rs.next()) {
                quantities.add(rs.getInt("quantity"));
            }

            if (quantities.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No inventory records found.",
                    "FoodChain Inventory",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            int[] values = new int[quantities.size()];

            for (int i = 0; i < quantities.size(); i++) {
                values[i] = quantities.get(i);
            }

            int total = sum(values);
            int sequentialTotal = sequentialSum(values);

            JOptionPane.showMessageDialog(
                null,
                "FOODCHAIN — PARALLEL INVENTORY REDUCTION\n\n"
                    + "Inventory records: " + values.length + "\n"
                    + "Total inventory quantity: " + total + "\n"
                    + "Sequential verification: " + sequentialTotal + "\n"
                    + "Results match: " + (total == sequentialTotal),
                "FoodChain Inventory Analysis",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                null,
                "Could not load inventory data:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}