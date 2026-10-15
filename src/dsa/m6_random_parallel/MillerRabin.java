package dsa.m6_random_parallel;

import java.util.Random;

public class MillerRabin {

    private static Random random =
            new Random();

    // Check whether n is probably prime
    public static boolean isPrime(
            long n,
            int iterations) {

        // Small numbers
        if (n < 2) {
            return false;
        }

        if (n == 2 ||
                n == 3) {

            return true;
        }

        // Even numbers greater than 2
        if (n % 2 == 0) {
            return false;
        }

        // Write:
        //
        // n - 1 = d * 2^s
        //
        long d = n - 1;

        int s = 0;

        while (d % 2 == 0) {

            d = d / 2;
            s++;
        }

        // Repeat the test
        for (int i = 0;
                i < iterations;
                i++) {

            long a =
                    2 + random.nextLong(
                            n - 3
                    );

            long x =
                    modularPower(
                            a,
                            d,
                            n
                    );

            if (x == 1 ||
                    x == n - 1) {

                continue;
            }

            boolean probablyPrime =
                    false;

            for (int r = 1;
                    r < s;
                    r++) {

                x =
                        modularMultiply(
                                x,
                                x,
                                n
                        );

                if (x == n - 1) {

                    probablyPrime = true;
                    break;
                }
            }

            if (!probablyPrime) {

                return false;
            }
        }

        return true;
    }

    // Fast modular exponentiation
    private static long modularPower(
            long base,
            long exponent,
            long modulus) {

        long result = 1;

        base =
                base % modulus;

        while (exponent > 0) {

            if ((exponent & 1) == 1) {

                result =
                        modularMultiply(
                                result,
                                base,
                                modulus
                        );
            }

            base =
                    modularMultiply(
                            base,
                            base,
                            modulus
                    );

            exponent =
                    exponent >> 1;
        }

        return result;
    }

    // Safe modular multiplication
    private static long modularMultiply(
            long a,
            long b,
            long modulus) {

        long result = 0;

        a = a % modulus;

        while (b > 0) {

            if ((b & 1) == 1) {

                result =
                        (result + a)
                        % modulus;
            }

            a =
                    (a + a)
                    % modulus;

            b =
                    b >> 1;
        }

        return result;
    }

    // Display result
    public static void check(
            long number,
            int iterations) {

        boolean result =
                isPrime(
                        number,
                        iterations
                );

        if (result) {

            System.out.println(
                    number
                    + " is probably prime."
            );

        } else {

            System.out.println(
                    number
                    + " is composite."
            );
        }
    }
    public static void runDemo() {

        long[] numbers = {17, 21, 97, 100, 7919};
        int iterations = 10;

        StringBuilder result = new StringBuilder();

        result.append("Miller-Rabin Primality Test\n\n");
        result.append("Iterations: ").append(iterations).append("\n\n");

        for (long number : numbers) {

            boolean prime = isPrime(number, iterations);

            result.append(number)
                  .append(prime
                      ? " is probably prime."
                      : " is composite.")
                  .append("\n");
        }

        result.append("\nNote: This is a probabilistic primality test.");

        javax.swing.JOptionPane.showMessageDialog(
            null,
            result.toString(),
            "Miller-Rabin Demo",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }
}