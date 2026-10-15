package dsa.m5_np;

public class SAT {

    // Check whether a Boolean formula is satisfiable.
    //
    // Each clause is represented as an array.
    //
    // Positive number  -> variable is true
    // Negative number  -> variable is false
    //
    // Example:
    // {1, -2} means:
    // X1 OR NOT X2
    //
    // The complete formula is:
    // Clause1 AND Clause2 AND ...

    public static boolean isSatisfiable(
            int variables,
            int[][] clauses) {

        int totalAssignments =
                1 << variables;

        // Try every possible assignment
        for (int mask = 0;
                mask < totalAssignments;
                mask++) {

            if (satisfiesFormula(
                    mask,
                    variables,
                    clauses)) {

                return true;
            }
        }

        return false;
    }

    // Check whether one assignment
    // satisfies the complete formula
    private static boolean satisfiesFormula(
            int mask,
            int variables,
            int[][] clauses) {

        for (int[] clause : clauses) {

            boolean clauseSatisfied = false;

            for (int literal : clause) {

                int variable =
                        Math.abs(literal) - 1;

                boolean value =
                        ((mask >> variable) & 1) == 1;

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

            // One false clause makes
            // the complete formula false
            if (!clauseSatisfied) {

                return false;
            }
        }

        return true;
    }

    // Find one satisfying assignment
    public static int[] findAssignment(
            int variables,
            int[][] clauses) {

        int totalAssignments =
                1 << variables;

        for (int mask = 0;
                mask < totalAssignments;
                mask++) {

            if (satisfiesFormula(
                    mask,
                    variables,
                    clauses)) {

                int[] assignment =
                        new int[variables];

                for (int i = 0;
                        i < variables;
                        i++) {

                    if (((mask >> i) & 1) == 1) {

                        assignment[i] = 1;

                    } else {

                        assignment[i] = 0;
                    }
                }

                return assignment;
            }
        }

        return null;
    }

    // Display assignment
    public static void displayAssignment(
            int[] assignment) {

        if (assignment == null) {

            System.out.println(
                    "No satisfying assignment found."
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

    // Display clauses
    public static void displayClauses(
            int[][] clauses) {

        System.out.println(
                "Boolean Formula:"
        );

        for (int i = 0;
                i < clauses.length;
                i++) {

            System.out.print("(");

            for (int j = 0;
                    j < clauses[i].length;
                    j++) {

                int literal =
                        clauses[i][j];

                if (literal < 0) {
                    System.out.print("NOT ");
                }

                System.out.print(
                        "X" + Math.abs(literal)
                );

                if (j < clauses[i].length - 1) {
                    System.out.print(" OR ");
                }
            }

            System.out.print(")");

            if (i < clauses.length - 1) {
                System.out.print(" AND ");
            }
        }

        System.out.println();
    }
}