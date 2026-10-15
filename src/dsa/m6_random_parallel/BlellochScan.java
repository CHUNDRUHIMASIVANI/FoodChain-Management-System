package dsa.m6_random_parallel;

public class BlellochScan {

    // Perform exclusive prefix scan
    public static int[] exclusiveScan(
            int[] input) {

        if (input == null ||
                input.length == 0) {

            return new int[0];
        }

        int n =
                nextPowerOfTwo(
                        input.length
                );

        int[] tree =
                new int[n];

        // Copy input values
        for (int i = 0;
                i < input.length;
                i++) {

            tree[i] =
                    input[i];
        }

        // -------------------------
        // UP-SWEEP
        // -------------------------

        for (int step = 1;
                step < n;
                step = step * 2) {

            for (int i = 0;
                    i < n;
                    i = i + 2 * step) {

                int right =
                        i + 2 * step - 1;

                int left =
                        i + step - 1;

                tree[right] =
                        tree[right]
                        + tree[left];
            }
        }

        // Total sum becomes root
        tree[n - 1] = 0;

        // -------------------------
        // DOWN-SWEEP
        // -------------------------

        for (int step = n / 2;
                step >= 1;
                step = step / 2) {

            for (int i = 0;
                    i < n;
                    i = i + 2 * step) {

                int left =
                        i + step - 1;

                int right =
                        i + 2 * step - 1;

                int temporary =
                        tree[left];

                tree[left] =
                        tree[right];

                tree[right] =
                        tree[right]
                        + temporary;
            }
        }

        // Return only original length
        int[] result =
                new int[input.length];

        for (int i = 0;
                i < input.length;
                i++) {

            result[i] =
                    tree[i];
        }

        return result;
    }

    // Convert exclusive scan
    // into inclusive scan
    public static int[] inclusiveScan(
            int[] input) {

        if (input == null ||
                input.length == 0) {

            return new int[0];
        }

        int[] exclusive =
                exclusiveScan(input);

        int[] result =
                new int[input.length];

        for (int i = 0;
                i < input.length;
                i++) {

            result[i] =
                    exclusive[i]
                    + input[i];
        }

        return result;
    }

    // Find next power of two
    private static int nextPowerOfTwo(
            int number) {

        int power = 1;

        while (power < number) {

            power =
                    power * 2;
        }

        return power;
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

        int[] input = {1, 2, 3, 4, 5};

        int[] exclusive = exclusiveScan(input);
        int[] inclusive = inclusiveScan(input);

        StringBuilder result = new StringBuilder();

        result.append("Blelloch Prefix Scan Demo\n\n");

        result.append("Input Array:\n");
        result.append(java.util.Arrays.toString(input));

        result.append("\n\nExclusive Prefix Scan:\n");
        result.append(java.util.Arrays.toString(exclusive));

        result.append("\n\nInclusive Prefix Scan:\n");
        result.append(java.util.Arrays.toString(inclusive));

        result.append("\n\nExclusive Scan: excludes the current element.");
        result.append("\nInclusive Scan: includes the current element.");

        javax.swing.JOptionPane.showMessageDialog(
            null,
            result.toString(),
            "Blelloch Scan",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }
}