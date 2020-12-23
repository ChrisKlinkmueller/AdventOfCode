package da.klnq.advent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Puzzle23 {
    private static final String INPUT = "538914762";

    public static void main(String[] args) {
        //System.out.println("Solution part 1: " + solvePart1(INPUT));
        //System.out.println("Solution part 1: " + solve("389125467", 10, 9));
        //System.out.println("Solution part 2: " + solvePart2("389125467"));
        System.out.println("Solution part 2: " + solvePart2(INPUT));
    }

    public static String solvePart1(String input) {
        final ArrayList<Integer> cups = readInput(input);
        for (int i = 0; i < 100; i++) {
            final int currentCup = cups.remove(0);
            final int[] threeCups = { cups.remove(0), cups.remove(0), cups.remove(0) };
            final int destCup = getDestinationCupPosition(cups, currentCup) + 1;
            cups.add(destCup, threeCups[2]);
            cups.add(destCup, threeCups[1]);
            cups.add(destCup, threeCups[0]);
            cups.add(currentCup);            
        }
        cups.add(cups.remove(0));

        while (cups.get(0) != 1) {
            cups.add(cups.remove(0));
        }

        return cups.subList(1, cups.size()).stream().map(Object::toString).collect(Collectors.joining());
    }

    private static Integer getDestinationCupPosition(ArrayList<Integer> cups, Integer currentCup) {
        int cup = currentCup - 1;
        while (0 < cup) {
            for (int i = 0; i < cups.size(); i++) {
                if (cups.get(i) == cup) {
                    return i;
                }
            }
            cup--;
        }

        cup = 0;
        int index = 0;
        for (int i = 0; i < cups.size(); i++) {
            if (cup < cups.get(i)) {
                index = i;
                cup = cups.get(i);
            }
        }

        return index;
    }

    private static ArrayList<Integer> readInput(String input) {
        final ArrayList<Integer> cups = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            cups.add(Integer.parseInt(input.substring(i, i+1)));
        }
        return cups;
    }

    public static BigInteger solvePart2(String input) {
        return solve(input, 10000000, 1000000);
    }

    public static BigInteger solve(String input, int moves, int numCups) {
        final TreeSet<Node> cups = new TreeSet<>();
        Node current = createLinkedList(input, cups, numCups);
        for (int i = 0; i < moves; i++) {
            List<Node> threeCups = List.of(current.next, current.next.next, current.next.next.next);
            
            Node dest = current;
            do {
                dest = cups.floor(new Node(dest.value - 1));
                if (dest == null) {
                    dest = cups.last();
                }
            } while (dest != null && !isValid(dest, threeCups));
            
            
            //System.out.println(current.value + " | " + threeCups.get(0).value + " | " + threeCups.get(1).value + " | " + threeCups.get(2).value + " | " + dest.value);

            current.next = threeCups.get(2).next;
            threeCups.get(2).next = dest.next;
            dest.next = threeCups.get(0);
            current = current.next;
        }

        Node one = cups.floor(new Node(1));
        System.out.println(one.next.value + " * " + one.next.next.value);
        return BigInteger.valueOf(one.next.value).multiply(
            BigInteger.valueOf(one.next.next.value)
        );
    }

    private static boolean isValid(Node node, List<Node> nodes) {
        return nodes.stream().allMatch(n -> n.value != node.value);
    }

    public static Node findMax(Node node) {
        Node current = node.next;
        Node max = node;
        while (current != node) {
            max = max.value < current.value ? current : max;
            current = current.next;            
        }
        return current;
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
