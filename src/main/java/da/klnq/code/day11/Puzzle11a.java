package da.klnq.code.day11;

public class Puzzle11a extends SeatPlanChecker {

    protected Puzzle11a() {
        super((plan, pos) -> plan.adjacentSeatCount(pos, SeatPlan.OCCUPIED), 3);
    }

    public static void main(String[] args) {
        final SeatPlan plan = SeatPlan.readPlan();
        final Puzzle11a puzzle = new Puzzle11a();
        System.out.println(puzzle.solve(plan));
    }
}
