package da.klnq.code.day11;

import java.util.function.Function;
import java.util.stream.IntStream;

import da.klnq.util.Tuple2;

public class Puzzle11b extends SeatPlanChecker {

    protected Puzzle11b() {
        super(Puzzle11b::countOccupiedSeats, 4);
    }

    public static void main(String[] args) {
        final SeatPlan plan = SeatPlan.readPlan();
        final Puzzle11b puzzle = new Puzzle11b();
        System.out.println(puzzle.solve(plan));
    }

    private static long countOccupiedSeats(SeatPlan plan, Tuple2<Integer, Integer> pos) {
        return IntStream.range(0, 8)
            .filter(direction -> checkDirection(pos, plan, direction))
            .count();
    }

    private static boolean checkDirection(Tuple2<Integer, Integer> pos, SeatPlan plan, int direction) {
        final Tuple2<Function<Integer, Integer>, Function<Integer, Integer>> modifier = getDirectionModifier(direction);
        
        Tuple2<Integer, Integer> newPos = pos.modify(modifier);
        while (plan.assertValidity(newPos)) {
            if (plan.getSeat(newPos) == SeatPlan.OCCUPIED) {
                return true;
            }
            if (plan.getSeat(newPos) == SeatPlan.EMPTY) {
                return false;
            }
            newPos = newPos.modify(modifier);
        }

        return false;
    }

    private static Tuple2<Function<Integer, Integer>, Function<Integer, Integer>> getDirectionModifier(int direction) {
        switch (direction) {
            case 0 : return new Tuple2<>(Puzzle11b::decrement, Puzzle11b::decrement);
            case 1 : return new Tuple2<>(Puzzle11b::decrement, Puzzle11b::doNothing);
            case 2 : return new Tuple2<>(Puzzle11b::decrement, Puzzle11b::increment);
            case 3 : return new Tuple2<>(Puzzle11b::doNothing, Puzzle11b::decrement);
            case 4 : return new Tuple2<>(Puzzle11b::doNothing, Puzzle11b::increment);
            case 5 : return new Tuple2<>(Puzzle11b::increment, Puzzle11b::decrement);
            case 6 : return new Tuple2<>(Puzzle11b::increment, Puzzle11b::doNothing);
            case 7 : return new Tuple2<>(Puzzle11b::increment, Puzzle11b::increment);
            default : throw new IllegalArgumentException("Unsupported direction: " + direction);
        }
    }

    private static int decrement(int value) {
        return value - 1;
    }

    private static int doNothing(int value) {
        return value;
    }

    private static int increment(int value) {
        return value + 1;
    }
}
