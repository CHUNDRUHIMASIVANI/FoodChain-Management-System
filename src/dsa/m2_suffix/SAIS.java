package dsa.m2_suffix;

import java.util.Arrays;

public class SAIS {

    // Public method to build suffix array
    public static int[] buildSuffixArray(String text) {

        if (text == null || text.length() == 0) {
            return new int[0];
        }

        int n = text.length();

        // Convert characters to integer values
        int[] data = new int[n + 1];

        for (int i = 0; i < n; i++) {
            data[i] = text.charAt(i) + 1;
        }

        // Sentinel character
        data[n] = 0;

        int[] result = sais(data);

        // Remove sentinel position
        int[] suffixArray = new int[n];

        int index = 0;

        for (int value : result) {

            if (value != n) {
                suffixArray[index] = value;
                index++;
            }
        }

        return suffixArray;
    }

    // SA-IS algorithm
    private static int[] sais(int[] data) {

        int n = data.length;

        if (n == 1) {
            return new int[]{0};
        }

        // Find maximum symbol
        int max = 0;

        for (int value : data) {
            if (value > max) {
                max = value;
            }
        }

        // Classify characters as S-type or L-type
        boolean[] isSType = new boolean[n];

        isSType[n - 1] = true;

        for (int i = n - 2; i >= 0; i--) {

            if (data[i] < data[i + 1]) {
                isSType[i] = true;

            } else if (data[i] > data[i + 1]) {
                isSType[i] = false;

            } else {
                isSType[i] = isSType[i + 1];
            }
        }

        // Find LMS positions
        int[] lmsPositions = new int[n];
        int lmsCount = 0;

        for (int i = 1; i < n; i++) {

            if (isLMS(isSType, i)) {
                lmsPositions[lmsCount] = i;
                lmsCount++;
            }
        }

        int[] lms = Arrays.copyOf(lmsPositions, lmsCount);

        // First induced sorting
        int[] suffixArray = inducedSort(
                data,
                isSType,
                lms,
                max
        );

        // Extract sorted LMS positions
        int[] sortedLMS = new int[lmsCount];

        int index = 0;

        for (int position : suffixArray) {

            if (isLMS(isSType, position)) {
                sortedLMS[index] = position;
                index++;
            }
        }

        // Assign names to LMS substrings
        int[] names = new int[n];
        Arrays.fill(names, -1);

        int name = 0;
        names[sortedLMS[0]] = name;

        for (int i = 1; i < sortedLMS.length; i++) {

            int previous = sortedLMS[i - 1];
            int current = sortedLMS[i];

            if (!sameLMSSubstring(
                    data,
                    isSType,
                    previous,
                    current)) {

                name++;
            }

            names[current] = name;
        }

        // Create reduced problem
        int reducedSize = lmsCount;
        int[] reducedText = new int[reducedSize];

        for (int i = 0; i < lmsCount; i++) {
            reducedText[i] = names[lms[i]];
        }

        int[] reducedSuffixArray;

        // If every name is unique, no recursion is needed
        if (name + 1 == reducedSize) {

            reducedSuffixArray =
                    new int[reducedSize];

            for (int i = 0; i < reducedSize; i++) {
                reducedSuffixArray[reducedText[i]] = i;
            }

        } else {

            reducedSuffixArray = sais(
                    addSentinel(reducedText)
            );

            reducedSuffixArray =
                    removeSentinel(
                            reducedSuffixArray
                    );
        }

        // Order LMS positions according to reduced suffix array
        int[] orderedLMS = new int[lmsCount];

        for (int i = 0; i < lmsCount; i++) {
            orderedLMS[i] =
                    lms[reducedSuffixArray[i]];
        }

        // Final induced sorting
        return inducedSort(
                data,
                isSType,
                orderedLMS,
                max
        );
    }

    // Induced sorting
    private static int[] inducedSort(
            int[] data,
            boolean[] isSType,
            int[] lms,
            int max) {

        int n = data.length;

        int[] suffixArray = new int[n];

        Arrays.fill(suffixArray, -1);

        int[] bucketSize =
                calculateBucketSizes(data, max);

        int[] bucketEnd =
                calculateBucketEnds(bucketSize);

        // Place LMS suffixes
        int[] end = bucketEnd.clone();

        for (int i = lms.length - 1; i >= 0; i--) {

            int position = lms[i];

            suffixArray[
                    end[data[position]]--
            ] = position;
        }

        // Place L-type suffixes
        int[] bucketStart =
                calculateBucketStarts(bucketSize);

        for (int i = 0; i < n; i++) {

            int position = suffixArray[i];

            if (position > 0 &&
                    !isSType[position - 1]) {

                int previous = position - 1;

                suffixArray[
                        bucketStart[data[previous]]++
                ] = previous;
            }
        }

        // Place S-type suffixes
        end = bucketEnd.clone();

        for (int i = n - 1; i >= 0; i--) {

            int position = suffixArray[i];

            if (position > 0 &&
                    isSType[position - 1]) {

                int previous = position - 1;

                suffixArray[
                        end[data[previous]]--
                ] = previous;
            }
        }

        return suffixArray;
    }

    // Check LMS position
    private static boolean isLMS(
            boolean[] isSType, int position) {

        if (position == 0) {
            return false;
        }

        return isSType[position]
                && !isSType[position - 1];
    }

    // Check whether two LMS substrings are equal
    private static boolean sameLMSSubstring(
            int[] data,
            boolean[] isSType,
            int first,
            int second) {

        int i = 0;

        while (true) {

            boolean firstLMS =
                    isLMS(isSType, first + i);

            boolean secondLMS =
                    isLMS(isSType, second + i);

            if (data[first + i]
                    != data[second + i]) {

                return false;
            }

            if (i > 0 &&
                    firstLMS &&
                    secondLMS) {

                return true;
            }

            if (firstLMS != secondLMS) {
                return false;
            }

            i++;
        }
    }

    // Calculate bucket sizes
    private static int[] calculateBucketSizes(
            int[] data, int max) {

        int[] bucketSize = new int[max + 1];

        for (int value : data) {
            bucketSize[value]++;
        }

        return bucketSize;
    }

    // Calculate bucket starts
    private static int[] calculateBucketStarts(
            int[] bucketSize) {

        int[] starts =
                new int[bucketSize.length];

        int sum = 0;

        for (int i = 0; i < bucketSize.length; i++) {

            starts[i] = sum;
            sum += bucketSize[i];
        }

        return starts;
    }

    // Calculate bucket ends
    private static int[] calculateBucketEnds(
            int[] bucketSize) {

        int[] ends =
                new int[bucketSize.length];

        int sum = -1;

        for (int i = 0; i < bucketSize.length; i++) {

            sum += bucketSize[i];
            ends[i] = sum;
        }

        return ends;
    }

    // Add sentinel
    private static int[] addSentinel(
            int[] array) {

        int[] result =
                new int[array.length + 1];

        System.arraycopy(
                array, 0,
                result, 0,
                array.length
        );

        result[array.length] = 0;

        // Shift values to avoid conflict with sentinel
        for (int i = 0; i < array.length; i++) {
            result[i]++;
        }

        return result;
    }

    // Remove sentinel
    private static int[] removeSentinel(
            int[] array) {

        int count = 0;

        for (int value : array) {

            if (value != array.length - 1) {
                count++;
            }
        }

        int[] result = new int[count];

        int index = 0;

        for (int value : array) {

            if (value != array.length - 1) {
                result[index++] = value;
            }
        }

        return result;
    }

    // Display suffix array
    public static void displaySuffixArray(
            String text) {

        int[] suffixArray =
                buildSuffixArray(text);

        System.out.println("SA-IS Suffix Array:");

        for (int position : suffixArray) {

            System.out.println(
                    position + " -> "
                    + text.substring(position)
            );
        }
    }
}