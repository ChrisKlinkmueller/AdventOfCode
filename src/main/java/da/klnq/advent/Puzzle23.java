package da.klnq.advent;

import java.math.BigInteger;
import java.util.List;
import java.util.TreeSet;

public class Puzzle23 {
    private static final String INPUT = "538914762";

    public static void main(String[] args) {
        System.out.println("Solution part 1: " + solvePart1(INPUT));
        System.out.println("Solution part 2: " + solvePart2(INPUT));
    }

    public static String solvePart1(String input) {
        final Node one = solve(input, 100, input.length());
        return toString(one);        
    }

    private static String toString(Node node) {
        final StringBuilder builder = new StringBuilder();
        Node current = node.next;
        while (current != node) {
            builder.append(current.value);
            current = current.next;
        }
        return builder.toString();
    }

    public static BigInteger solvePart2(String input) {
        final Node one = solve(input, 10000000, 1000000);
        return BigInteger.valueOf(one.next.value).multiply(
            BigInteger.valueOf(one.next.next.value)
        );
    }

    public static Node solve(String input, int moves, int numCups) {
        final TreeSet<Node> cups = new TreeSet<>();
        Node current = createLinkedList(input, cups, numCups);
        for (int i = 0; i < moves; i++) {
            final List<Node> threeCups = List.of(current.next, current.next.next, current.next.next.next);            
            Node dest = current;
            do {
                dest = cups.floor(new Node(dest.value - 1));
                if (dest == null) {
                    dest = cups.last();
                }
            } while (dest != null && !isValid(dest, threeCups));
            
            current.next = threeCups.get(2).next;
            threeCups.get(2).next = dest.next;
            dest.next = threeCups.get(0);
            current = current.next;
        }

        return cups.floor(new Node(1));
    }

    private static boolean isValid(Node node, List<Node> nodes) {
        return nodes.stream().allMatch(n -> n.value != node.value);
    }

    private static Node createLinkedList(String input, TreeSet<Node> cups, int numCups) {
        Node head = null;
        Node current = null;
        for (int i = 1; i <= numCups; i++) {
            int cup = i <= input.length() ? input.charAt(i - 1) - '0' : i;
            if (head == null) {
                head = current = new Node(cup);
            }
            else {
                current.next = new Node(cup);
                current = current.next;
            }

            cups.add(current);
        }
        current.next = head;
        return head;
    } 

    private static class Node implements Comparable<Node> {
        private final int value;
        private Node next;

        private Node(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(Node node) {
            return Integer.compare(this.value, node.value);
        }
    }
}
