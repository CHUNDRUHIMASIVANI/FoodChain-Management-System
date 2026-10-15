package dsa.m1_string;

import java.util.*;

public class AhoCorasick {

    // Trie Node
    private static class Node {

        Map<Character, Integer> next;
        int fail;
        ArrayList<String> output;

        Node() {
            next = new HashMap<>();
            fail = 0;
            output = new ArrayList<>();
        }
    }

    private ArrayList<Node> trie;

    public AhoCorasick() {
        trie = new ArrayList<>();
        trie.add(new Node());
    }

    // Add a pattern
    public void addPattern(String pattern) {

        int current = 0;

        for (char ch : pattern.toCharArray()) {

            if (!trie.get(current).next.containsKey(ch)) {

                trie.get(current).next.put(
                        ch, trie.size());

                trie.add(new Node());
            }

            current = trie.get(current).next.get(ch);
        }

        trie.get(current).output.add(pattern);
    }

    // Build failure links
    public void build() {

        Queue<Integer> queue = new LinkedList<>();

        for (int child : trie.get(0).next.values()) {

            trie.get(child).fail = 0;
            queue.add(child);
        }

        while (!queue.isEmpty()) {

            int current = queue.poll();

            for (Map.Entry<Character, Integer> entry :
                    trie.get(current).next.entrySet()) {

                char ch = entry.getKey();
                int child = entry.getValue();

                queue.add(child);

                int failure = trie.get(current).fail;

                while (failure != 0 &&
                        !trie.get(failure).next.containsKey(ch)) {

                    failure = trie.get(failure).fail;
                }

                if (trie.get(failure).next.containsKey(ch)
                        && trie.get(failure).next.get(ch) != child) {

                    failure =
                            trie.get(failure).next.get(ch);
                }

                trie.get(child).fail = failure;

                trie.get(child).output.addAll(
                        trie.get(failure).output);
            }
        }
    }

    // Search multiple patterns
    public Map<String, Integer> search(String text) {

        Map<String, Integer> results =
                new LinkedHashMap<>();

        int current = 0;

        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);

            while (current != 0 &&
                    !trie.get(current).next.containsKey(ch)) {

                current = trie.get(current).fail;
            }

            if (trie.get(current).next.containsKey(ch)) {
                current = trie.get(current).next.get(ch);
            }

            for (String pattern :
                    trie.get(current).output) {

                int position =
                        i - pattern.length() + 1;

                if (!results.containsKey(pattern)) {
                    results.put(pattern, position);
                }
            }
        }

        return results;
    }
}