package da.klnq.advent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import da.klnq.util.IOUtils;

public class Puzzle09 {
    private static final String RESOURCE = "/09-task-input.txt";
    private static final int PREAMBLE_LENGTH = 25;
    
    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        System.out.println("Solution for part 1: " + solvePart1(input, PREAMBLE_LENGTH));
        System.out.println("Solution for part 2: " + solvePart2(input, PREAMBLE_LENGTH));
    }

    public static long solvePart1(List<String> input, int preambleLength) {
        final List<Long> numbers = readNumbers(input);
        final int index = findIndexOfInvalidNumber(numbers, preambleLength);
        return numbers.get(index);
    }

    public static long solvePart2(List<String> input, int preambleLength) {
        final List<Long> numbers = readNumbers(input);
        final int invalidIndex = findIndexOfInvalidNumber(numbers, preambleLength);

        int startIndex = invalidIndex - 1;
        int endIndex = invalidIndex - 1;
        long sum = 0;

        while (0 < startIndex && sum != numbers.get(invalidIndex)) {
            sum += numbers.get(startIndex);

            if (numbers.get(invalidIndex) < sum) {
                sum -= numbers.get(endIndex);
                endIndex--;
            }

            startIndex--;
        }

        startIndex++;
        endIndex++;
        long min = IntStream.range(startIndex, endIndex)
            .mapToLong(numbers::get)
            .min().getAsLong();
        long max = IntStream.range(startIndex, endIndex)
            .mapToLong(numbers::get)
            .max().getAsLong();

        return min + max;
    }

    private static int findIndexOfInvalidNumber(List<Long> numbers, int preambleLength) {       
        int index = preambleLength;
        while (index < numbers.size()) {
            if (!isValid(numbers, preambleLength, index)) {
                return index;
            }

            index++;
        }

        throw new IllegalStateException("No number found!");
    }

    private static boolean isValid(List<Long> numbers, int preambleLength, int index) {
        for (int i = index - preambleLength; i < index - 1; i++) {
            for (int j = i + 1; j < index; j++) {
                if (numbers.get(i) + numbers.get(j) == numbers.get(index)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static List<Long> readNumbers(List<String> input) {
        return input.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
    }

}
