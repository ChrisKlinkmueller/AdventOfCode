package da.klnq.advent;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle11 {    
    private static final String RESOURCE = "/11-task-input.txt";
    private static final char FLOOR = '.';
    private static final char OCCUPIED = '#';
    private static final char EMPTY = 'L';
    private static final int SEAT_THRESHOLD_PART_1 = 3;
    private static final int SEAT_THRESHOLD_PART_2 = 4;

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        final Puzzle11 puzzle = new Puzzle11();
        System.out.println("Solution for part 1: " + puzzle.solvePart1(input));
        System.out.println("Solution for part 2: " + puzzle.solvePart2(input));
    }

    private char[][] plan;
    private Function<Tuple2<Integer, Integer>, Long> seatCounter;
    private int seatThreshold;
     
    public long solvePart1(List<String> input) {
        this.seatPlan(input);
        return this.solve(pos -> this.adjacentSeatCount(pos, OCCUPIED), SEAT_THRESHOLD_PART_1);
    }
    
    public long solvePart2(List<String> input) {
        this.seatPlan(input);
        return this.solve(this::countOccupiedSeats, SEAT_THRESHOLD_PART_2);
    }

    private void seatPlan(List<String> input) {
        this.plan = input.stream()
            .map(String::toCharArray)
            .toArray(length -> new char[length][]);
    }

    private long solve(Function<Tuple2<Integer, Integer>, Long> seatCounter, int seatThreshold) {
        this.seatCounter = seatCounter;
        this.seatThreshold = seatThreshold;

        boolean modified = true;

        do {
            modified = this.modify();
        } while (modified);


        final long count = this.seatCount(this::positionStream, OCCUPIED);
        this.plan = null;
        return count;
    }

    private boolean modify() {
        final List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications = new LinkedList<>();
        this.positionStream().forEach(pos -> this.checkSeat(pos, modifications));

        modifications.forEach(mod -> 
            this.plan[mod.get1().get1()][mod.get1().get2()] = mod.get2()
        );

        return !modifications.isEmpty();
    }

    private void checkSeat(
        Tuple2<Integer, Integer> pos,
        List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications
    ) {
        char seat = this.getSeat(pos);
        switch (seat) {
            case FLOOR : break;
            case EMPTY : checkEmptySeat(pos, modifications); break;
            case OCCUPIED : checkOccupiedSeat(pos, modifications); break;
            default : throw new IllegalArgumentException("Unknown seat category: " + seat);
        }
    }

    private void checkEmptySeat(
        Tuple2<Integer, Integer> pos, 
        List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications
    ) {
        if (this.seatCounter.apply(pos) == 0) {
            modifications.add(new Tuple2<>(pos, OCCUPIED));
        }
    }

    private void checkOccupiedSeat(
        Tuple2<Integer, Integer> pos, 
        List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications
    ) {
        if (this.seatThreshold < this.seatCounter.apply(pos)) {
            modifications.add(new Tuple2<>(pos, EMPTY));
        }
    }

    private long adjacentSeatCount(Tuple2<Integer, Integer> position, char seat) {
        return this.seatCount(() -> adjacentPositionStream(position), seat);
    }

    private long seatCount(Supplier<Stream<Tuple2<Integer, Integer>>> positionStream, char seat) {
        return positionStream.get()
            .filter(pos -> this.getSeat(pos) == seat)
            .count();
    }

    private Stream<Tuple2<Integer, Integer>> positionStream() {
        return IntStream.range(0, this.rowCount() * this.columnCount())
            .mapToObj(seat -> new Tuple2<>(seat / this.columnCount(), seat % this.columnCount()));            
    }

    private Stream<Tuple2<Integer, Integer>> adjacentPositionStream(Tuple2<Integer, Integer> position) {
        final int row = position.get1();
        final int column = position.get2();
        return IntStream.range(0, 9)
            .mapToObj(seat -> new Tuple2<>(row - 1 + seat / 3, column - 1 + seat % 3))
            .filter(pos -> 0 <= pos.get1() && pos.get1() < this.rowCount())
            .filter(pos -> 0 <= pos.get2() && pos.get2() < this.columnCount())
            .filter(pos -> pos.get1() != row || pos.get2() != column);
    }

    private char getSeat(Tuple2<Integer, Integer> pos) {
        return this.plan[pos.get1()][pos.get2()];
    }

    private int rowCount() {
        return this.plan.length;
    }

    private int columnCount() {
        return this.plan[0].length;
    }

    private long countOccupiedSeats(Tuple2<Integer, Integer> pos) {
        return IntStream.range(0, 8)
            .filter(direction -> checkDirection(pos, direction))
            .count();
    }

    private boolean checkDirection(Tuple2<Integer, Integer> pos, int direction) {
        final Tuple2<Function<Integer, Integer>, Function<Integer, Integer>> modifier = this.getDirectionModifier(direction);
        
        Tuple2<Integer, Integer> newPos = pos.modify(modifier);
        while (this.isValidPosition(newPos)) {
            if (this.getSeat(newPos) == OCCUPIED) {
                return true;
            }
            if (this.getSeat(newPos) == EMPTY) {
                return false;
            }
            newPos = newPos.modify(modifier);
        }

        return false;
    }

    private boolean isValidPosition(Tuple2<Integer, Integer> position) {
        return 0 <= position.get1() && position.get1() < this.rowCount()
            && 0 <= position.get2() && position.get2() < this.columnCount();
    }

    private Tuple2<Function<Integer, Integer>, Function<Integer, Integer>> getDirectionModifier(int direction) {
        switch (direction) {
            case 0 : return new Tuple2<>(this::decrement, this::decrement);
            case 1 : return new Tuple2<>(this::decrement, this::doNothing);
            case 2 : return new Tuple2<>(this::decrement, this::increment);
            case 3 : return new Tuple2<>(this::doNothing, this::decrement);
            case 4 : return new Tuple2<>(this::doNothing, this::increment);
            case 5 : return new Tuple2<>(this::increment, this::decrement);
            case 6 : return new Tuple2<>(this::increment, this::doNothing);
            case 7 : return new Tuple2<>(this::increment, this::increment);
            default : throw new IllegalArgumentException("Unsupported direction: " + direction);
        }
    }

    private int decrement(int value) {
        return value - 1;
    }

    private int doNothing(int value) {
        return value;
    }

    private int increment(int value) {
        return value + 1;
    }
}
