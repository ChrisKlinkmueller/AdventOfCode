package da.klnq.advent;

import java.util.List;
import java.util.stream.Stream;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle03 {
    private static final String RESOURCE = "/03-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        
        long trees = solvePart1(input);
        System.out.println("Solution for part 1: " + trees);

        trees = solvePart2(input);
        System.out.println("Solution for part 2: " + trees);
    }
    
    public static long solvePart1(List<String> input) {
        return countTrees(input, new Tuple2<>(3, 1));
    }

    public static long solvePart2(List<String> input) {
        return Stream.of(
            new Tuple2<>(1, 1),
            new Tuple2<>(3, 1),
            new Tuple2<>(5, 1),
            new Tuple2<>(7, 1),
            new Tuple2<>(1, 2)
        )
        .map(move -> countTrees(input, move))
        .reduce(1L, (x, y) -> x * y);
    }

    private static long countTrees(List<String> map, Tuple2<Integer, Integer> move) {
        final int height = map.size();
        final int width = map.get(0).length();

        Tuple2<Integer, Integer> position = new Tuple2<>(0, 0);
        long trees = 0;
        while (position.get2() < height) {
            if (map.get(position.get2()).charAt(position.get1()) == '#') {
                trees++;
            }

            position = position.modify(
                x -> (x + move.get1()) % width, 
                y -> y + move.get2()
            );
        }

        return trees;
    }
}
