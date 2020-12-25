package da.klnq.advent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;

public class Puzzle15 {
    private static final String RESOURCE = "/15-task-input.txt";

    public static void main(String[] args) {
        final String input = IOUtils.readResource(RESOURCE).get(0);
        System.out.println("Solution for part 1: " + solve(input, 2020));
        System.out.println("Solution for part 2: " + solve(input, 30000000));
    }

    public static int solve(String input, int lastRound) {
        final List<Integer> numbers = initNumbers(input);
        
        final Map<Integer, Integer> positions = new HashMap<>();
        int round = 0;
        int lastValue = -1;

        while (true) {
            int newLastValue = 0;
            if (!numbers.isEmpty()) {
                newLastValue = numbers.remove(0);
            }
            else if (positions.containsKey(lastValue)) {
                newLastValue = round - positions.get(lastValue);
            }
            else {
                newLastValue = 0;
            }
            
            positions.put(lastValue, round);
            round++;
            lastValue = newLastValue;

            if (round == lastRound) {
                return lastValue;
            }
        }
    }

    private static List<Integer> initNumbers(String input) {
        return Arrays.stream(input.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }
}
