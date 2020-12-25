package da.klnq.advent;

import java.math.BigInteger;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import da.klnq.util.IOUtils;

public class Puzzle23 {
    private static final String RESOURCE = "/23-task-input.txt";

    public static void main(String[] args) {
        final String input = IOUtils.readResource(RESOURCE).get(0);
        System.out.println("Solution part 1: " + solvePart1(input));
        System.out.println("Solution part 2: " + solvePart2(input));
    }

    public static String solvePart1(String input) {
        final Cup one = solve(input, 100, input.length());
        return toString(one);        
    }

    private static String toString(Cup cup) {
        final StringBuilder builder = new StringBuilder();
        Cup current = cup.next;
        while (!current.equals(cup)) {
            builder.append(current.value);
            current = current.next;
        }
        return builder.toString();
    }

    public static BigInteger solvePart2(String input) {
        final Cup one = solve(input, 10000000, 1000000);
        return BigInteger.valueOf(one.next.value).multiply(
            BigInteger.valueOf(one.next.next.value)
        );
    }

    public static Cup solve(String input, int moves, int numCups) {
        final NavigableSet<Cup> cups = new TreeSet<>();
        Cup current = createLinkedList(input, cups, numCups);
        for (int i = 0; i < moves; i++) {
            final List<Cup> threeCups = List.of(current.next, current.next.next, current.next.next.next);            
            
            Cup dest = current;
            do {
                dest = cups.floor(new Cup(dest.value - 1));
                if (dest == null) {
                    dest = cups.last();
                }
            } while (dest != null && !isValid(dest, threeCups));
            
            current.next = threeCups.get(2).next;
            threeCups.get(2).next = dest.next;
            dest.next = threeCups.get(0);
            current = current.next;
        }

        return cups.floor(new Cup(1));
    }

    private static boolean isValid(Cup node, List<Cup> nodes) {
        return nodes.stream().allMatch(n -> n.value != node.value);
    }

    private static Cup createLinkedList(String input, NavigableSet<Cup> cups, int numCups) {
        Cup head = null;
        Cup current = null;
        for (int i = 1; i <= numCups; i++) {
            int cup = i <= input.length() ? input.charAt(i - 1) - '0' : i;
            if (head == null) {
                head = current = new Cup(cup);
            }
            else {
                current.next = new Cup(cup);
                current = current.next;
            }

            cups.add(current);
        }
        current.next = head;
        return head;
    } 

    private static class Cup implements Comparable<Cup> {
        private final int value;
        private Cup next;

        private Cup(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(Cup cup) {
            return Integer.compare(this.value, cup.value);
        }
    }
}
