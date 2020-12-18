package da.klnq.advent;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Tuple2;

public class Puzzle01 {
    private static final String RESOURCE = "/01-task-input.txt";
    private static final int SUM = 2020;

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE, IOUtils::parseString).get();
        
        final OptionalInt resultPart1 = solvePart1(input, SUM);
        System.out.println(String.format("Solution for part 1: %s", resultPart1.getAsInt()));

        final OptionalInt resultPart2 = solvePart2(input, SUM);
        System.out.println(String.format("Solution for part 2: %s", resultPart2.getAsInt()));
    }

    public static OptionalInt solvePart1(List<String> input, int sum) {
        return nestedLoop(input, (exs, idxs) -> checkExpenses1(exs, idxs, sum));
    }

    public static OptionalInt solvePart2(List<String> input, int sum) {
        return nestedLoop(input, (exs, idxs) -> checkExpenses2(exs, idxs, sum));
    }
    
    private static OptionalInt nestedLoop(
        List<String> input, 
        BiFunction<List<Integer>, Tuple2<Integer, Integer>, OptionalInt> checker
    ) {
        final List<Integer> expenses = input.stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        return IntStream.range(0, expenses.size() - 1)
            .mapToObj(i -> IntStream.range(i + 1, expenses.size())
                .mapToObj(j -> new Tuple2<Integer, Integer>(i, j))
            )
            .flatMap(stream -> stream)
            .map(indecies -> checker.apply(expenses, indecies))
            .filter(OptionalInt::isPresent)
            .findFirst()
            .orElseGet(() -> OptionalInt.empty());
    }

    private static OptionalInt checkExpenses1(List<Integer> expenses, Tuple2<Integer, Integer> indecies, int sum) {
        final int expense1 = expenses.get(indecies.getValue1());
        final int expense2 = expenses.get(indecies.getValue2());
        return expense1 + expense2 == sum
            ? OptionalInt.of(expense1 * expense2)
            : OptionalInt.empty();
    } 

    private static OptionalInt checkExpenses2(List<Integer> expenses, Tuple2<Integer, Integer> indecies, int sum) {
        final int expense1 = expenses.get(indecies.getValue1());
        final int expense2 = expenses.get(indecies.getValue2());

        for (int index3 = indecies.getValue2() + 1; index3 < expenses.size(); index3++) {
            final int expense3 = expenses.get(index3);
            if (expense1 + expense2 + expense3 == sum) {
                return OptionalInt.of(expense1 * expense2 * expense3);
            }
        }

        return OptionalInt.empty();
    }

}
