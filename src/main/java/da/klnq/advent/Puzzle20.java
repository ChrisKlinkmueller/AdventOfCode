package da.klnq.advent;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import da.klnq.util.IOUtils;

public class Puzzle20 {
    private static final String RESOURCE = "/20-task-input.txt";
    private static final int DIMENSION = 10;

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution part 1: " + solvePart1(input));
    }
    
    public static BigInteger solvePart1(List<String> input) {
        final Map<BigInteger, char[][]> tiles = parseTiles(input);
        return tiles.entrySet()
            .stream()
            .filter(e -> countNeighbors(e.getKey(), e.getValue(), tiles.entrySet()) == 2)
            .map(e -> e.getKey())
            .reduce(BigInteger.ONE, (v1, v2) -> v1.multiply(v2));
    }

    private static long countNeighbors(BigInteger id, char[][] tile, Set<Entry<BigInteger, char[][]>> tiles) {
        return tiles.stream()
            .filter(e -> !e.getKey().equals(id))
            .filter(e -> sharesSide(tile, e.getValue()))
            .count();
    }

    private static final List<BiFunction<char[][], Integer, Character>> ACCESSORS_1 = List.of(
        Puzzle20::leftFromTop,
        Puzzle20::rightFromTop,
        Puzzle20::topFromLeft,
        Puzzle20::bottomFromLeft
    );
    private static final List<BiFunction<char[][], Integer, Character>> ACCESSORS_2 = List.of(
        Puzzle20::leftFromBottom,
        Puzzle20::leftFromTop,
        Puzzle20::rightFromBottom,
        Puzzle20::rightFromTop,
        Puzzle20::topFromLeft,
        Puzzle20::topFromRight,
        Puzzle20::bottomFromLeft,
        Puzzle20::bottomFromRight
    );

    private static boolean sharesSide(char[][] tile1, char[][] tile2) {
        for (BiFunction<char[][], Integer, Character> accessor1 : ACCESSORS_1) {
            for (BiFunction<char[][], Integer, Character> accessor2 : ACCESSORS_2) {
                if (areEqual(tile1, tile2, accessor1, accessor2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean areEqual(
        char[][] tile1,
        char[][] tile2,
        BiFunction<char[][], Integer, Character> accessor1,
        BiFunction<char[][], Integer, Character> accessor2
    ) {
        return IntStream.range(0, DIMENSION)
            .allMatch(i -> accessor1.apply(tile1, i) == accessor2.apply(tile2, i));
    }

    private static char leftFromTop(char[][] tile, int index) {
        return tile[index][0];
    }

    private static char leftFromBottom(char[][] tile, int index) {
        return tile[DIMENSION - 1 - index][0];
    }

    private static char rightFromTop(char[][] tile, int index) {
        return tile[index][DIMENSION - 1];
    }

    private static char rightFromBottom(char[][] tile, int index) {
        return tile[DIMENSION - 1 - index][DIMENSION - 1];
    }

    private static char topFromLeft(char[][] tile, int index) {
        return tile[0][index];
    }

    private static char topFromRight(char[][] tile, int index) {
        return tile[0][DIMENSION - 1 - index];
    }

    private static char bottomFromLeft(char[][] tile, int index) {
        return tile[DIMENSION - 1][index];
    }

    private static char bottomFromRight(char[][] tile, int index) {
        return tile[DIMENSION - 1][DIMENSION - 1 - index];
    }    

    private static Map<BigInteger, char[][]> parseTiles(List<String> input) {
        final Map<BigInteger, char[][]> tiles = new HashMap<>();

        for (int i = 0; i < input.size(); i += 12) {
            final BigInteger id = new BigInteger(input.get(i).replaceAll("[Tile :]", ""));
            final char[][] idTiles = IntStream.range(i + 1, i + 11)
                .mapToObj(input::get)
                .map(String::toCharArray)
                .toArray(char[][]::new);
            tiles.put(id, idTiles);
        }

        return tiles;
    }

}
