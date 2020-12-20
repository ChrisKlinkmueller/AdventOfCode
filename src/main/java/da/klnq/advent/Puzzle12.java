package da.klnq.advent;

import java.util.List;
import java.util.function.BiFunction;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public final class Puzzle12<T> {
    private static final String RESOURCE = "/12-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static int solvePart1(List<String> input) {
        Puzzle12<Integer> puzzle = new Puzzle12<>();
        puzzle.moveNorth = (config, value) -> config.modify(pos -> pos.modify(lon -> lon, lat -> lat - value), dir -> dir);
        puzzle.moveSouth = (config, value) -> config.modify(pos -> pos.modify(lon -> lon, lat -> lat + value), dir -> dir);
        puzzle.moveEast = (config, value) -> config.modify(pos -> pos.modify(lon -> lon + value, lat -> lat), dir -> dir);
        puzzle.moveWest = (config, value) -> config.modify(pos -> pos.modify(lon -> lon - value, lat -> lat), dir -> dir);
        puzzle.rotateLeft = (config, value) -> config.modify(pos -> pos, dir -> (360 + dir - value) % 360);
        puzzle.rotateRight = (config, value) -> config.modify(pos -> pos, dir -> (dir + value) % 360);
        puzzle.moveForward = (config, value) -> moveForwardPart1(puzzle, config, value); 
        return puzzle.solve(input, 0);
    }

    public static int solvePart2(List<String> input) {
        Puzzle12<Tuple2<Integer, Integer>> puzzle = new Puzzle12<>();
        puzzle.moveNorth = (config, value) -> config.modify(pos -> pos, wp -> wp.modify(lon -> lon, lat -> lat - value));
        puzzle.moveSouth = (config, value) -> config.modify(pos -> pos, wp -> wp.modify(lon -> lon, lat -> lat + value));
        puzzle.moveEast = (config, value) -> config.modify(pos -> pos, wp -> wp.modify(lon -> lon + value, lat -> lat));
        puzzle.moveWest = (config, value) -> config.modify(pos -> pos, wp -> wp.modify(lon -> lon - value, lat -> lat));
        puzzle.rotateLeft = (config, value) -> config.modify(pos -> pos, wp -> rotateLeftPart2(wp, value));
        puzzle.rotateRight = (config, value) -> config.modify(pos -> pos, wp -> rotateRightPart2(wp, value));
        puzzle.moveForward = (config, value) -> config.modify(pos -> pos.modify(lon -> lon + config.get2().get1() * value, lat -> lat + config.get2().get2() * value), wp -> wp);
        return puzzle.solve(input, new Tuple2<>(10, -1));
    }
    
    private static Tuple2<Tuple2<Integer, Integer>, Integer> moveForwardPart1(Puzzle12<Integer> puzzle, Tuple2<Tuple2<Integer, Integer>, Integer> config, int value) {
        switch (config.get2()) {
            case 0 : return puzzle.moveEast.apply(config, value);
            case 90 : return puzzle.moveSouth.apply(config, value);
            case 180 : return puzzle.moveWest.apply(config, value);
            case 270 : return puzzle.moveNorth.apply(config, value);
            default : throw new IllegalStateException("Unknown direction: " + config.get2());
        }
    }

    private static Tuple2<Integer, Integer> rotateLeftPart2(Tuple2<Integer, Integer> wayPoint, int value) {
        if (value == 180) {
            return flipDirectionPart2(wayPoint);
        }
        else if (value == 90) {
            return turnLeftPart2(wayPoint);
        }
        else if (value == 270) {
            return turnRightPart2(wayPoint);
        }
        throw new IllegalStateException("Unsupported value: " + value);
    }

    private static Tuple2<Integer, Integer> rotateRightPart2(Tuple2<Integer, Integer> wayPoint, int value) {
        if (value == 180) {
            return flipDirectionPart2(wayPoint);
        }
        else if (value == 90) {
            return turnRightPart2(wayPoint);
        }
        else if (value == 270) {
            return turnLeftPart2(wayPoint);
        }
        throw new IllegalStateException("Unsupported value: " + value);
    }

    private static Tuple2<Integer, Integer> flipDirectionPart2(Tuple2<Integer, Integer> wp) {
        return wp.modify(lon -> -lon, lat -> -lat);
    }

    private static Tuple2<Integer, Integer> turnLeftPart2(Tuple2<Integer, Integer> wp) {
        return new Tuple2<>(wp.get2(), -wp.get1());
    }

    private static Tuple2<Integer, Integer> turnRightPart2(Tuple2<Integer, Integer> wp) {
        return new Tuple2<>(-wp.get2(), wp.get1());
    }

    private BiFunction<Tuple2<Tuple2<Integer, Integer>, T>, Integer, Tuple2<Tuple2<Integer, Integer>, T>> moveNorth;
    private BiFunction<Tuple2<Tuple2<Integer, Integer>, T>, Integer, Tuple2<Tuple2<Integer, Integer>, T>> moveSouth;
    private BiFunction<Tuple2<Tuple2<Integer, Integer>, T>, Integer, Tuple2<Tuple2<Integer, Integer>, T>> moveEast;
    private BiFunction<Tuple2<Tuple2<Integer, Integer>, T>, Integer, Tuple2<Tuple2<Integer, Integer>, T>> moveWest;
    private BiFunction<Tuple2<Tuple2<Integer, Integer>, T>, Integer, Tuple2<Tuple2<Integer, Integer>, T>> moveForward;
    private BiFunction<Tuple2<Tuple2<Integer, Integer>, T>, Integer, Tuple2<Tuple2<Integer, Integer>, T>> rotateLeft;
    private BiFunction<Tuple2<Tuple2<Integer, Integer>, T>, Integer, Tuple2<Tuple2<Integer, Integer>, T>> rotateRight;

    private int solve(List<String> input, T initValue) {
        Tuple2<Tuple2<Integer, Integer>, T> config = new Tuple2<>(new Tuple2<>(0, 0), initValue);

        for (String line : input) {
            config = this.execute(line, config);
        }

        return Math.abs(config.get1().get1()) + Math.abs(config.get1().get2());
    }

    private Tuple2<Tuple2<Integer, Integer>, T> execute(
        String line, 
        Tuple2<Tuple2<Integer, Integer>, T> config
    ) {
        final int value = Integer.parseInt(line.substring(1));
        final char inst = line.charAt(0);
        switch (inst) {
            case 'N' : return this.moveNorth.apply(config, value);
            case 'S' : return this.moveSouth.apply(config, value);
            case 'E' : return this.moveEast.apply(config, value);
            case 'W' : return this.moveWest.apply(config, value);
            case 'F' : return this.moveForward.apply(config, value);
            case 'L' : return this.rotateLeft.apply(config, value);
            case 'R' : return this.rotateRight.apply(config, value);
            default : throw new IllegalArgumentException("Unsupported instruction type: " + inst);
        }
    }
}
