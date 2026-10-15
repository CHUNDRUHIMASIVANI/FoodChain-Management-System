package dsa.m2_suffix;

import java.util.HashMap;
import java.util.Map;

public class SuffixAutomation {

    // State of the suffix automaton
    private static class State {

        int length;
        int link;

        Map<Character, Integer> next;

        State() {
            length = 0;
            link = -1;
            next = new HashMap<>();
        }
    }

    private State[] states;
    private int size;
    private int last;

    // Constructor
    public SuffixAutomation(String text) {

        states = new State[2 * text.length() + 1];

        for (int i = 0; i < states.length; i++) {
            states[i] = new State();
        }

        size = 1;
        last = 0;

        for (char ch : text.toCharArray()) {
            extend(ch);
        }
    }

    // Extend automaton by one character
    private void extend(char ch) {

        int current = size++;

        states[current].length =
                states[last].length + 1;

        int previous = last;

        while (previous != -1 &&
                !states[previous].next.containsKey(ch)) {

            states[previous].next.put(
                    ch, current);

            previous = states[previous].link;
        }

        if (previous == -1) {

            states[current].link = 0;

        } else {

            int nextState =
                    states[previous].next.get(ch);

            if (states[previous].length + 1
                    == states[nextState].length) {

                states[current].link = nextState;

            } else {

                int clone = size++;

                states[clone].length =
                        states[previous].length + 1;

                states[clone].next.putAll(
                        states[nextState].next);

                states[clone].link =
                        states[nextState].link;

                while (previous != -1 &&
                        states[previous].next.getOrDefault(
                                ch, -1) == nextState) {

                    states[previous].next.put(
                            ch, clone);

                    previous =
                            states[previous].link;
                }

                states[nextState].link = clone;
                states[current].link = clone;
            }
        }

        last = current;
    }

    // Check whether a substring exists
    public boolean contains(String pattern) {

        int current = 0;

        for (char ch : pattern.toCharArray()) {

            if (!states[current].next.containsKey(ch)) {
                return false;
            }

            current =
                    states[current].next.get(ch);
        }

        return true;
    }

    // Find the state reached by a pattern
    private int getState(String pattern) {

        int current = 0;

        for (char ch : pattern.toCharArray()) {

            if (!states[current].next.containsKey(ch)) {
                return -1;
            }

            current =
                    states[current].next.get(ch);
        }

        return current;
    }

    // Get length of the longest substring
    // represented by the state
    public int getStateLength(String pattern) {

        int state = getState(pattern);

        if (state == -1) {
            return 0;
        }

        return states[state].length;
    }

    // Count different substrings
    public long countDistinctSubstrings() {

        long count = 0;

        for (int i = 1; i < size; i++) {

            count +=
                    states[i].length
                    - states[states[i].link].length;
        }

        return count;
    }

    // Get number of states
    public int getNumberOfStates() {
        return size;
    }

    // Display automaton states
    public void displayAutomaton() {

        System.out.println(
                "Suffix Automaton:");

        for (int i = 0; i < size; i++) {

            System.out.println(
                    "State " + i
                    + " | Length = "
                    + states[i].length
                    + " | Link = "
                    + states[i].link
                    + " | Transitions = "
                    + states[i].next
            );
        }
    }
}