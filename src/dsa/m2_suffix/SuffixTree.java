package dsa.m2_suffix;

import java.util.ArrayList;
import java.util.List;

public class SuffixTree {

    // Node of suffix tree
    private static class Node {

        int start;
        int end;
        int suffixStart;
        Node[] children;

        Node(int start, int end) {
            this(start, end, -1);
        }

        Node(int start, int end, int suffixStart) {
            this.start = start;
            this.end = end;
            this.suffixStart = suffixStart;
            this.children = new Node[256];
        }

        int length() {
            return end - start;
        }
    }

    private String text;
    private Node root;

    // Constructor
    public SuffixTree(String text) {

        if (text == null) {
            text = "";
        }

        this.text = text + "$";
        this.root = new Node(0, 0);

        buildTree();
    }

    // Build suffix tree by inserting every suffix
    private void buildTree() {

        for (int i = 0; i < text.length(); i++) {
            insertSuffix(i);
        }
    }

    // Insert one suffix
    private void insertSuffix(int suffixStart) {

        Node current = root;
        int position = suffixStart;

        while (position < text.length()) {

            char currentChar = text.charAt(position);
            int index = currentChar;

            Node child = current.children[index];

            // No matching edge
            if (child == null) {

                current.children[index] =
                        new Node(
                                position,
                                text.length(),
                                suffixStart
                        );

                return;
            }

            // Compare characters on the edge
            int edgePosition = child.start;
            int matched = 0;

            while (
                    edgePosition + matched < child.end
                    &&
                    position + matched < text.length()
                    &&
                    text.charAt(edgePosition + matched)
                            == text.charAt(position + matched)
            ) {
                matched++;
            }

            // Entire edge matched
            if (edgePosition + matched == child.end) {

                position += matched;
                current = child;

            } else {

                // Split the existing edge
                Node split =
                        new Node(
                                child.start,
                                child.start + matched
                        );

                current.children[index] = split;

                // Move old child below split
                child.start =
                        child.start + matched;

                split.children[
                        text.charAt(child.start)
                ] = child;

                // Add new suffix part
                if (position + matched < text.length()) {

                    Node newNode =
                            new Node(
                                    position + matched,
                                    text.length(),
                                    suffixStart
                            );

                    split.children[
                            text.charAt(position + matched)
                    ] = newNode;
                }

                return;
            }
        }

        // Suffix ended at an existing node
        current.suffixStart = suffixStart;
    }

    // Search whether a pattern exists
    public boolean search(String pattern) {

        if (pattern == null || pattern.length() == 0) {
            return true;
        }

        Node current = root;
        int patternPosition = 0;

        while (patternPosition < pattern.length()) {

            char firstChar = pattern.charAt(patternPosition);

            Node child = current.children[firstChar];

            if (child == null) {
                return false;
            }

            int edgePosition = child.start;

            while (
                    edgePosition < child.end
                    &&
                    patternPosition < pattern.length()
            ) {

                if (text.charAt(edgePosition)
                        != pattern.charAt(patternPosition)) {

                    return false;
                }

                edgePosition++;
                patternPosition++;
            }

            // Pattern has been completely matched
            if (patternPosition == pattern.length()) {
                return true;
            }

            // Edge was completely consumed
            if (edgePosition == child.end) {
                current = child;
            } else {
                return false;
            }
        }

        return true;
    }

    // Find all starting positions of a pattern
    public List<Integer> searchAll(String pattern) {

        List<Integer> result = new ArrayList<>();

        if (pattern == null || pattern.length() == 0) {
            return result;
        }

        findOccurrences(
                root,
                pattern,
                0,
                result
        );

        return result;
    }

    // Find occurrences recursively
    private void findOccurrences(
            Node node,
            String pattern,
            int matched,
            List<Integer> result) {

        if (matched == pattern.length()) {

            collectPositions(node, result);

            return;
        }

        char nextChar = pattern.charAt(matched);

        Node child = node.children[nextChar];

        if (child == null) {
            return;
        }

        int edgePosition = child.start;
        int patternPosition = matched;

        while (
                edgePosition < child.end
                &&
                patternPosition < pattern.length()
        ) {

            if (text.charAt(edgePosition)
                    != pattern.charAt(patternPosition)) {

                return;
            }

            edgePosition++;
            patternPosition++;
        }

        // Pattern ends inside this edge
        if (patternPosition == pattern.length()) {

            collectPositions(child, result);

        }
        // Entire edge matched, continue down the tree
        else if (edgePosition == child.end) {

            findOccurrences(
                    child,
                    pattern,
                    patternPosition,
                    result
            );
        }
    }

    // Collect suffix positions below a matched node
    private void collectPositions(
            Node node,
            List<Integer> result) {

        // If this node represents a suffix ending here
        if (node.suffixStart >= 0
                && node.suffixStart < text.length() - 1) {

            result.add(node.suffixStart);
        }

        // Visit all children
        for (Node child : node.children) {

            if (child != null) {

                collectPositions(
                        child,
                        result
                );
            }
        }
    }

    // Display suffix tree
    public void displayTree() {

        System.out.println("Suffix Tree:");

        displayNode(root, 0);
    }

    // Recursive display
    private void displayNode(
            Node node,
            int level) {

        for (Node child : node.children) {

            if (child != null) {

                String edge =
                        text.substring(
                                child.start,
                                child.end
                        );

                for (int i = 0; i < level; i++) {
                    System.out.print("  ");
                }

                System.out.println(
                        "└── " + edge
                );

                displayNode(
                        child,
                        level + 1
                );
            }
        }
    }

    // Get original text
    public String getText() {

        return text.substring(
                0,
                text.length() - 1
        );
    }
}