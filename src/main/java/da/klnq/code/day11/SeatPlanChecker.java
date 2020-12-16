package da.klnq.code.day11;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import da.klnq.code.util.Tuple2;

public abstract class SeatPlanChecker {
    private final int occupiedSeatThreshold;
    private final BiFunction<SeatPlan, Tuple2<Integer, Integer>, Long> occupiedSeatCounter;    
    private SeatPlan plan = null;

    protected SeatPlanChecker(
        BiFunction<SeatPlan, Tuple2<Integer, Integer>, Long> occupiedSeatCounter,
        int occupiedSeatThreshold
    ) {
        assert occupiedSeatCounter != null;
        assert 0 <= occupiedSeatThreshold;
        this.occupiedSeatCounter = occupiedSeatCounter;
        this.occupiedSeatThreshold = occupiedSeatThreshold;
    }
    
    public long solve(SeatPlan plan) {
        this.plan = plan;

        boolean modified = true;
        do {
            modified = this.modify();
        } while (modified);


        final long count = plan.seatCount(SeatPlan.OCCUPIED);
        this.plan = null;
        return count;
    }

    private boolean modify() {
        final List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications = new LinkedList<>();
        plan.positionStream().forEach(pos -> this.checkSeat(pos, modifications));

        modifications.forEach(mod -> 
            plan.setSeat(mod.getValue1(), mod.getValue2())
        );

        return !modifications.isEmpty();
    }

    private void checkSeat(
        Tuple2<Integer, Integer> pos,
        List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications
    ) {
        char seat = plan.getSeat(pos);
        switch (seat) {
            case SeatPlan.FLOOR : break;
            case SeatPlan.EMPTY : checkEmptySeat(pos, modifications); break;
            case SeatPlan.OCCUPIED : checkOccupiedSeat(pos, modifications); break;
            default : throw new IllegalArgumentException("Unknown seat category: " + seat);
        }
    }

    private void checkEmptySeat(
        Tuple2<Integer, Integer> pos, 
        List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications
    ) {
        if (this.occupiedSeatCounter.apply(this.plan, pos) == 0) {
            modifications.add(new Tuple2<>(pos, SeatPlan.OCCUPIED));
        }
    }

    private void checkOccupiedSeat(
        Tuple2<Integer, Integer> pos, 
        List<Tuple2<Tuple2<Integer, Integer>, Character>> modifications
    ) {
        if (this.occupiedSeatThreshold < this.occupiedSeatCounter.apply(this.plan, pos)) {
            modifications.add(new Tuple2<>(pos, SeatPlan.EMPTY));
        }
    }
}
