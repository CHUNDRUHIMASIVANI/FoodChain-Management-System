package dsa.m5_np;

public class ThreeSAT {

    // Check whether a 3-SAT formula
    // is satisfiable.
    //
    // Every clause must contain
    // exactly 3 literals.
    public static boolean isSatisfiable(
            int variables,
            int[][] clauses) {

        if (!validClauses(clauses)) {
            return false;
        }

        int totalAssignments =
                1 << variables;

        // Try every possible assignment
        for (int mask = 0;
                mask < totalAssignments;
                mask++) {

            if (satisfiesFormula(
                    mask,
                    clauses)) {

                return true;
            }
        }

        return false;
    }

    // Find one satisfying assignment
    public static int[] findAssignment(
            int variables,
            int[][] clauses) {

        if (!validClauses(clauses)) {
            return null;
        }

        int totalAssignments =
                1 << variables;

        for (int mask = 0;
                mask < totalAssignments;
                mask++) {

            if (satisfiesFormula(
                    mask,
                    clauses)) {

                int[] assignment =
                        new int[variables];

                for (int i = 0;
                        i < variables;
                        i++) {

                    assignment[i] =
                            (mask >> i) & 1;
                }

                return assignment;
            }
        }

        return null;
    }

    // Check complete formula
    private static boolean satisfiesFormula(
            int mask,
            int[][] clauses) {

        for (int[] clause : clauses) {

            boolean clauseSatisfied =
                    false;

            // Each clause has 3 literals
            for (int literal : clause) {

                int variable =
                        Math.abs(literal) - 1;

                boolean value =
                        ((mask >> variable) & 1)
                        == 1;

                // Positive literal
                if (literal > 0 && value) {

                    clauseSatisfied = true;
                    break;
                }

                // Negative literal
                if (literal < 0 && !value) {

                    clauseSatisfied = true;
                    break;
                }
            }

            // All 3 literals are false
            if (!clauseSatisfied) {

                return false;
            }
        }

        return true;
    }

    // Verify that every clause
    // contains exactly 3 literals
    private static boolean validClauses(
            int[][] clauses) {

        if (clauses == null) {
            return false;
        }

        for (int[] clause : clauses) {

            if (clause == null ||
                    clause.length != 3) {

                return false;
            }
        }

        return true;
    }

    // Display the formula
    public static void displayFormula(
            int[][] clauses) {

        System.out.println(
                "3-SAT Formula:"
        );

        for (int i = 0;
                i < clauses.length;
                i++) {

            System.out.print("(");

            for (int j = 0;
                    j < 3;
                    j++) {

                int literal =
                        clauses[i][j];

                if (literal < 0) {

                    System.out.print(
                            "NOT "
                    );
                }

                System.out.print(
                        "X" + Math.abs(literal)
                );

                if (j < 2) {

                    System.out.print(
                            " OR "
                    );
                }
            }

            System.out.print(")");

            if (i < clauses.length - 1) {

                System.out.print(
                        " AND "
                );
            }
        }

        System.out.println();
    }

    // Display satisfying assignment
    public static void displayAssignment(
            int[] assignment) {

        if (assignment == null) {

            System.out.println(
                    "Formula is not satisfiable."
            );

            return;
        }

        System.out.println(
                "Satisfying Assignment:"
        );

        for (int i = 0;
                i < assignment.length;
                i++) {

            System.out.println(
                    "X" + (i + 1)
                    + " = "
                    + (assignment[i] == 1
                            ? "TRUE"
                            : "FALSE")
            );
        }
    }
}