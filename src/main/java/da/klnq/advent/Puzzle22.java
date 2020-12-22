package da.klnq.advent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle22 {
    private final static String RESOURCE = "/22-task-input.txt";
    
    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution for part 1: " + solvePart1(input));
    }    

    public static long solvePart1(List<String> input) {
        final Tuple2<Queue<Long>,Queue<Long>> players = parseInput(input);
        while (!players.get1().isEmpty() && !players.get2().isEmpty()) {
            final long v1 = players.get1().poll();
            final long v2 = players.get2().poll();
            if (v1 < v2) {
                players.get2().add(v2);
                players.get2().add(v1);
            }
            else {
                players.get1().add(v1);
                players.get1().add(v2);
            }
        }

        return players.get1().isEmpty()
            ? computeScore(players.get2())
            : computeScore(players.get1());
    }

    private static long computeScore(Queue<Long> queue) {
        long score = 0;
        while (!queue.isEmpty()) {
            score += queue.size() * queue.poll();
        }
        return score;
    }

    private static Tuple2<Queue<Long>,Queue<Long>> parseInput(List<String> input) {
        final Queue<Long> player1 = new LinkedList<>();
        final Queue<Long> player2 = new LinkedList<>();
        Queue<Long> current = player1;
        for (String line : input) {
            if (line.isBlank()) {
                current = player2;
            }
            else if (!line.startsWith("Player")) {
                current.add(Long.parseLong(line));
            }
        }
        return new Tuple2<>(player1, player2);
    }
}
