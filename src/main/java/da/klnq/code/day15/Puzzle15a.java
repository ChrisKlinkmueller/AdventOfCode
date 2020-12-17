package da.klnq.code.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle15a {
    private static final String INPUT = "12,1,16,3,11,0";
    private static final int LAST_ROUND = 30000000;

    public static void main(String[] args) {
        final List<Integer> numbers = initNumbers();

        while (numbers.size() < LAST_ROUND) {
            int nextNumber = determineNext(numbers);
            numbers.add(nextNumber);
        }

        System.out.println(numbers.get(LAST_ROUND - 1));
    }

    private static int determineNext(List<Integer> numbers) {
        int recent = numbers.size() - 1;
        for (int previous = recent - 1; previous >= 0; previous--) {
            if (numbers.get(previous).equals(numbers.get(recent))) {
                return recent - previous;
            }
        }
        return 0;
    }

    private static List<Integer> initNumbers() {
        return new ArrayList<>(
            Arrays.stream(INPUT.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList())
        );
    }
}
