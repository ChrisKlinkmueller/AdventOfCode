package da.klnq.code.day11;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;
import da.klnq.code.util.Tuple2;

public class SeatPlan {
    private static final String RESOURCE = "/day11/input1.txt";
    public static final char FLOOR = '.';
    public static final char OCCUPIED = '#';
    public static final char EMPTY = 'L';
    private final char[][] seats;
    private final int rowCount;
    private final int columnCount;

    public SeatPlan(List<String> seats) {
        this.rowCount = seats.size();
        this.columnCount = seats.get(0).length();
        this.seats = new char[this.rowCount][this.columnCount];
        this.positionStream()
            .forEach(pos -> this.setSeat(pos, seats.get(pos.getValue1()).charAt(pos.getValue2())));
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public char getSeat(Tuple2<Integer, Integer> position) {
        this.assertValidity(position);
        return this.seats[position.getValue1()][position.getValue2()];
    }

    public void setSeat(Tuple2<Integer, Integer> position, char seat) {
        this.assertValidity(position);
        this.seats[position.getValue1()][position.getValue2()] = seat;
    }

    public boolean assertValidity(Tuple2<Integer, Integer> position) {
        final boolean isValidRow = 0 <= position.getValue1() && position.getValue1() < this.rowCount;
        assert isValidRow;
        final boolean isValidCol = 0 <= position.getValue2() && position.getValue2() < this.columnCount;
        assert isValidCol;
        return isValidCol && isValidRow;
    }

    public long seatCount(char seat) {
        return this.seatCount(this::positionStream, seat);
    }

    public long adjacentSeatCount(Tuple2<Integer, Integer> position, char seat) {
        return this.seatCount(() -> adjacentPositionStream(position), seat);
    }

    private long seatCount(Supplier<Stream<Tuple2<Integer, Integer>>> positionStream, char seat) {
        return positionStream.get()
            .filter(pos -> this.getSeat(pos) == seat)
            .count();
    }

    public Stream<Tuple2<Integer, Integer>> positionStream() {
        return IntStream.range(0, this.rowCount * this.columnCount)
            .mapToObj(seat -> new Tuple2<>(seat / this.columnCount, seat % this.columnCount));            
    }

    public Stream<Tuple2<Integer, Integer>> adjacentPositionStream(Tuple2<Integer, Integer> position) {
        final int row = position.getValue1();
        final int column = position.getValue2();
        return IntStream.range(0, 9)
            .mapToObj(seat -> new Tuple2<>(row - 1 + seat / 3, column - 1 + seat % 3))
            .filter(pos -> 0 <= pos.getValue1() && pos.getValue1() < this.rowCount)
            .filter(pos -> 0 <= pos.getValue2() && pos.getValue2() < this.columnCount)
            .filter(pos -> pos.getValue1() != row || pos.getValue2() != column);
    }

    public static SeatPlan readPlan() {
        final Try<List<String>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseString);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        return new SeatPlan(readResult.get());
    }
}
