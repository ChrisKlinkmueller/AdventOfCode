package da.klnq.code.day05;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle05b {
    public static void main(String[] args) {
        final List<BoardingPass> passes = BoardingPass.readPasses();
        IntStream.range(0, 938)
            .filter(seat -> isValid(seat, passes))
            .forEach(System.out::println);
    }

    private static boolean isValid(int seat, List<BoardingPass> passes) {
        return !existsPass(seat, passes) && existsPass(seat - 1, passes) && existsPass(seat + 1, passes);
    } 

    private static boolean existsPass(int seat, List<BoardingPass> passes) {
        return passes.stream().anyMatch(p -> p.getSeat() == seat);
    }
}
