package da.klnq.advent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import da.klnq.util.IOUtils;

public class Puzzle06 {
    private static final String RESOURCE = "/06-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static long solvePart1(List<String> input) {
        return solve(input, Puzzle06::countAnswersPart1);
    }

    public static long solvePart2(List<String> input) {
        return solve(input, Puzzle06::countAnswersPart2);
    }

    private static long solve(List<String> input, Function<Map<Integer, String>, Long> counter) {
        return readGroups(input)
            .stream()
            .mapToLong(group -> (long)counter.apply(group))
            .sum();
    }

    private static long countAnswersPart1(Map<Integer, String> group) {
        return group.values()
            .stream()
            .flatMapToInt(String::chars)
            .distinct()
            .count();
    }

    private static long countAnswersPart2(Map<Integer, String> group) {
        Map<Integer, Integer> answers = new HashMap<>();
        group.values().stream()
            .flatMapToInt(String::chars)
            .forEach(i -> answers.compute(i, (key, val) -> val == null ? 1 : val + 1));
        return answers.values().stream()
            .filter(val -> val == group.size())
            .count();
    }

    private static List<Map<Integer, String>> readGroups(List<String> input) {    
        final LinkedList<Map<Integer, String>> groups = new LinkedList<>();
        
        groups.add(new HashMap<>());
        for (String answers : input) {
            if (answers.isBlank()) {
                groups.addFirst(new HashMap<>());
            }
            else {
                final int index = groups.getFirst().size();
                groups.getFirst().put(index, answers);
            }
        }

        return groups;
    }
}
