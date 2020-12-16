package da.klnq.code.day03;

import java.util.List;

import da.klnq.code.util.Tuple2;

public class Puzzle03b {
    private static List<Tuple2<Integer, Integer>> SLOPES = List.of(
        new Tuple2<>(1, 1),
        new Tuple2<>(3, 1),
        new Tuple2<>(5, 1),
        new Tuple2<>(7, 1),
        new Tuple2<>(1, 2)
    );

    public static void main(String[] args) {
        final FlightMap map = FlightMap.readMap();
        final long product = SLOPES.stream()
            .mapToLong(slope -> map.countTrees(slope.getValue1(), slope.getValue2()))
            .reduce(1, (x,y) -> x * y);
        System.out.println(product);
    }
}
