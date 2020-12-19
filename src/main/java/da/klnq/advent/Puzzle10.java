package da.klnq.advent;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;

public class Puzzle10 {
    private static final String RESOURCE = "/10-task-input.txt";
    
    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        System.err.println("Solution for part 1: " + solvePart1(input));
        System.err.println("Solution for part 2: " + solvePart2(input));
    }

    public static int solvePart1(List<String> input) {
        final List<Integer> adapters = readAdapters(input);

        int difference1 = 0;
        int difference3 = 1;

        for (int index = 0; index < adapters.size(); index++) {
            int difference = index == 0 
                ? adapters.get(index)
                : adapters.get(index) - adapters.get(index - 1);
            if (difference == 1) {
                difference1++;
            }
            else if (difference == 3) {
                difference3++;
            }
        }

        return difference1 * difference3;
    }

    public static BigInteger solvePart2(List<String> input) {
        final List<Integer> adapters = readAdapters(input);

        final Map<Integer, BigInteger> pathCounts = new HashMap<>();
        pathCounts.put(0, BigInteger.ONE);

        for (int adapter : adapters) {
            addPathCount(adapter, pathCounts);
        }

        return pathCounts.get(adapters.get(adapters.size() - 1));
    }

    private static void addPathCount(int number, Map<Integer, BigInteger> pathCounts) {
        final BigInteger paths1 = pathCounts.getOrDefault(number - 1, BigInteger.ZERO);
        final BigInteger paths2 = pathCounts.getOrDefault(number - 2, BigInteger.ZERO);
        final BigInteger paths3 = pathCounts.getOrDefault(number - 3, BigInteger.ZERO);
        final BigInteger sum = paths1.add(paths2).add(paths3);
        pathCounts.put(number, sum);
    }

    private static List<Integer> readAdapters(List<String> input) {
        return input.stream()
            .map(Integer::parseInt)
            .sorted()
            .collect(Collectors.toList());
    }
}
