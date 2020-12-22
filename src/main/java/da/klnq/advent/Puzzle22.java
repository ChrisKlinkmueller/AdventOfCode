package da.klnq.advent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle22 {
    private final static String RESOURCE = "/22-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static int solvePart1(List<String> input) {
        return solve(input, false);
    }

    public static int solvePart2(List<String> input) {
        return solve(input, true);
    }

    private static int solve(List<String> input, boolean recursive) {
        final Tuple2<Queue<Integer>, Queue<Integer>> players = parseInput(input);
        final Tuple2<Queue<Integer>, Boolean> result = play(players.get1(), players.get2(), recursive);
        return computeScore(result.get1());
    }

    private static Tuple2<Queue<Integer>, Queue<Integer>> parseInput(List<String> input) {
        final Queue<Integer> player1 = new LinkedList<>();
        final Queue<Integer> player2 = new LinkedList<>();
        Queue<Integer> current = player1;
        for (String line : input) {
            if (line.isBlank()) {
                current = player2;
            }
            else if (!line.startsWith("Player")) {
                current.add(Integer.parseInt(line));
            }
        }
        return new Tuple2<>(player1, player2);
    }

    private static Tuple2<Queue<Integer>, Boolean> play(Queue<Integer> player1, Queue<Integer> player2, boolean recursive) {
        final Set<Integer> history1 = recursive ? new HashSet<>() : null;
        final Set<Integer> history2 = recursive ? new HashSet<>() : null;

        while (!player1.isEmpty() && !player2.isEmpty()) {
            if (recursive && (!history1.add(player1.hashCode()) || !history2.add(player2.hashCode()))) {
                return new Tuple2<>(player1, true);
            }

            final int c1 = player1.poll();
            final int c2 = player2.poll();
            final boolean firstPlayerWon = recursive && c1 <= player1.size() && c2 <= player2.size()
                ? play(copy(player1, c1), copy(player2, c2), recursive).get2()
                : c1 > c2;

            if (firstPlayerWon) {
                player1.add(c1);
                player1.add(c2);
            }
            else {
                player2.add(c2);
                player2.add(c1);
            }
        }
        return new Tuple2<>(!player1.isEmpty() ? player1 : player2, !player1.isEmpty());
    }

    private static Queue<Integer> copy(Queue<Integer> original, int count ) {
        return new LinkedList<>(original.stream().limit(count).collect(Collectors.toList()));
    }

    private static int computeScore(Queue<Integer> queue) {
        int score = 0;
        while (!queue.isEmpty()) {
            score += queue.size() * queue.poll();
        }
        return score;
    }
}
