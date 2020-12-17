package da.klnq.code.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Puzzle15b {
    private static final String INPUT = "12,1,16,3,11,0";
    private static final int LAST_ROUND = 30000000;

    public static void main(String[] args) {
        final List<Integer> numbers = initNumbers();
        
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

            if (round == LAST_ROUND) {
                System.out.println(lastValue);
                return;
            }
        }
    }

    private static List<Integer> initNumbers() {
        return new ArrayList<>(
            Arrays.stream(INPUT.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList())
        );
    }
}
