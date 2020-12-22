package da.klnq.advent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple3;

public class Puzzle22 {
    private final static String RESOURCE = "/22-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static int solvePart1(List<String> input) {
        return solve(input, Puzzle22::playStandard);
    }

    public static int solvePart2(List<String> input) {
        return solve(input, Puzzle22::playRecursive);
    }

    private static int solve(
        List<String> input,
        Function<Tuple3<Queue<Integer>, Queue<Integer>, Boolean>, 
        Tuple3<Queue<Integer>, Queue<Integer>, Boolean>> rules
    ) {
        Tuple3<Queue<Integer>, Queue<Integer>, Boolean> players = parseInput(input);
        players = rules.apply(players);
        return players.get3() ? computeScore(players.get1()) : computeScore(players.get2());
    }

    private static Tuple3<Queue<Integer>, Queue<Integer>, Boolean> playStandard(
        Tuple3<Queue<Integer>, Queue<Integer>, Boolean> players
    ) {
        while (!players.get1().isEmpty() && !players.get2().isEmpty()) {
            final int c1 = players.get1().poll();
            final int c2 = players.get2().poll();
            addCards(players, c1, c2, c1 > c2);
        }
        return new Tuple3<>(players.get1(), players.get2(), !players.get1().isEmpty());
    }

    private static Tuple3<Queue<Integer>, Queue<Integer>, Boolean> playRecursive(
        Tuple3<Queue<Integer>, Queue<Integer>, Boolean> players
    ) {
        final Set<Integer> history1 = new HashSet<>();
        final Set<Integer> history2 = new HashSet<>();

        while (!players.get1().isEmpty() && !players.get2().isEmpty()) {
            if (!history1.add(players.get1().hashCode()) || !history2.add(players.get2().hashCode())) {
                return new Tuple3<>(players.get1(), players.get2(), true);
            }

            final int c1 = players.get1().poll();
            final int c2 = players.get2().poll();
            boolean firstPlayerWon = c1 > c2;
            if (c1 <= players.get1().size() && c2 <= players.get2().size()) {
                firstPlayerWon = playRecursive(copy(players, c1, c2)).get3();
            }
            addCards(players, c1, c2, firstPlayerWon);
        }
        return new Tuple3<>(players.get1(), players.get2(), !players.get1().isEmpty());
    }

    private static Tuple3<Queue<Integer>, Queue<Integer>, Boolean> copy(
        Tuple3<Queue<Integer>, Queue<Integer>, Boolean> original,
        int c1,
        int c2
    ) {
        return new Tuple3<>(
            new LinkedList<>(original.get1().stream().limit(c1).collect(Collectors.toList())),
            new LinkedList<>(original.get2().stream().limit(c2).collect(Collectors.toList())),
            original.get3()
        );
    }

    private static void addCards(Tuple3<Queue<Integer>, Queue<Integer>, Boolean> players, int c1, int c2, boolean firstPlayerWon) {
        if (firstPlayerWon) {
            players.get1().add(c1);
            players.get1().add(c2);
        }
        else {
            players.get2().add(c2);
            players.get2().add(c1);
        }
    }

    private static Tuple3<Queue<Integer>, Queue<Integer>, Boolean> parseInput(List<String> input) {
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
        return new Tuple3<>(player1, player2, null);
    }

    private static int computeScore(Queue<Integer> queue) {
        int score = 0;
        while (!queue.isEmpty()) {
            score += queue.size() * queue.poll();
        }
        return score;
    }
}
