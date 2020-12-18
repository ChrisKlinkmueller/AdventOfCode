package da.klnq.advent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle05 {
    private static final String RESOURCE = "/05-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        final int maxSeatNumber = solvePart1(input);
        System.out.println("Solution for part 1: " + maxSeatNumber);

        final int seatId = solvePart2(input);
        System.out.println("Solution for part 2: " + seatId);
    }
    
    public static int solvePart1(List<String> input) {
        return parseInput(input).stream()
            .mapToInt(Puzzle05::computeSeatNumber)
            .max()
            .getAsInt();
    }

    public static int solvePart2(List<String> input) {
        final List<Tuple2<Integer, Integer>> passes = parseInput(input);
        return IntStream.range(0, 938)
            .filter(seat -> isValid(seat, passes))
            .findFirst()
            .getAsInt();
    }

    private static boolean isValid(int seat, List<Tuple2<Integer, Integer>> passes) {
        return !existsPass(seat, passes) && existsPass(seat - 1, passes) && existsPass(seat + 1, passes);
    } 

    private static boolean existsPass(int seat, List<Tuple2<Integer, Integer>> passes) {
        return passes.stream().anyMatch(pass -> computeSeatNumber(pass) == seat);
    }

    private static int computeSeatNumber(Tuple2<Integer, Integer> pass) {
        return pass.get1() * 8 + pass.get2();
    }

    private static List<Tuple2<Integer, Integer>> parseInput(List<String> input) {
        return input.stream()
            .map(Puzzle05::parseBoardingPass)
            .collect(Collectors.toList());
    }

    private static Tuple2<Integer, Integer> parseBoardingPass(String line) {
        return new Tuple2<>(
            converToNumber(line.substring(0, 7), 127, 'F'),
            converToNumber(line.substring(7, 10), 7, 'L')
        );
    }

    private static int converToNumber(String code, int max, char lower) {
        int low = 0;
        int high = max;

        final char[] codes = code.toCharArray();
        for (int i = 0; i < codes.length - 1; i++) {
            if (codes[i] == lower) {
                high = low + (high - low) / 2;
            }
            else {
                low = high - ((high - low) / 2);
            }
        }

        return codes[codes.length - 1] == lower ? low : high;
    }

}
