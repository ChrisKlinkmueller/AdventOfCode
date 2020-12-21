package da.klnq.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle19 {
    private static final String RESOURCE = "/19-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final Puzzle19 puzzle = new Puzzle19();
        
        System.out.println("Solution for part 1: " + puzzle.solvePart1(input));
        System.out.println("Solution for part 2: " + puzzle.solvePart2(input));
    }

    private final Map<String, BiFunction<String, Integer, Integer>> rules;

    public Puzzle19() {
        this.rules = new HashMap<>();
    }

    public long solvePart1(List<String> input) {
        final List<String> messages = new ArrayList<>(input);
        this.extractRules(messages);
        return messages.stream()
            .filter(message -> this.rules.get("0").apply(message, 0) == message.length())
            .count();
    }

    public long solvePart2(List<String> input) {
        final List<String> messages = new ArrayList<>(input);
        this.extractRules(messages);
        return messages.stream()
            .filter(this::isValidPart2)
            .count();
    }

    private boolean isValidPart2(String message) {
        final Tuple2<Integer,Integer> matches31 = this.matches(message, "31");
        
        if (matches31.get1() == 0) {
            return false;
        }
                
        message = message.substring(0, matches31.get2());
        final Tuple2<Integer,Integer> matches42 = this.matches(message, "42");
        return matches42.get2() == 0 && matches31.get1() < matches42.get1();
    }

    private Tuple2<Integer, Integer> matches(String message, String rule) {
        int matches = 0;
        int startIndex = message.length() - 1;
        int endIndex = message.length();

        while (0 <= startIndex) {
            final String submessage = message.substring(startIndex, endIndex);
            if (this.rules.get(rule).apply(submessage, 0) == submessage.length()) {
                matches++;
                endIndex = startIndex;
            }
            startIndex--;
        }
        return new Tuple2<Integer,Integer>(matches, endIndex);
    }

    private void extractRules(List<String> input) {
        this.rules.clear();

        String line;
        while (!(line = input.remove(0)).isBlank()) {
            String[] parts = line.split(": ");
            if (parts[1].charAt(0) == '"') {
                this.rules.put(parts[0], (m, i) -> check(m, i, parts[1].charAt(1)));
            }
            else {
                final List<String[]> patterns = Arrays.stream(parts[1].split(" \\| "))
                    .map(pattern -> pattern.split("\\s+"))
                    .collect(Collectors.toList());
                this.rules.put(parts[0], (m, i) -> check(m, i, patterns));
            }
        }
    }

    private int check(String message, int index, List<String[]> patterns) {
        for (String[] pattern : patterns) {
            final int newIndex = this.check(message, index, pattern);
            if (0 < newIndex) {
                return newIndex;
            }
            
        }
        return -1;
    }

    private int check(String message, int index, String[] pattern) {
        for (String part : pattern) {
            index = this.rules.get(part).apply(message, index);
            if (index < 0) {
                return -1;
            }
        }
        return index;
    }

    private int check(String message, int index, char c) {
        return index != message.length() && message.charAt(index) == c ? index + 1 : -1;
    }
    
}
