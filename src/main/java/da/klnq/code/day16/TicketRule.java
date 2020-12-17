package da.klnq.code.day16;

import da.klnq.code.util.Tuple2;

public class TicketRule {
    private final String name;
    private Tuple2<Integer, Integer> firstInterval;
    private Tuple2<Integer, Integer> secondInterval;

    public TicketRule(
        String name, 
        Tuple2<Integer, Integer> firstInterval, 
        Tuple2<Integer, Integer> secondInterval
    ) {
        this.name = name;
        this.firstInterval = firstInterval;
        this.secondInterval = secondInterval;
    }

    public boolean isValidValue(int value) {
        return (this.firstInterval.getValue1() <= value && value <= this.firstInterval.getValue2())
            || (this.secondInterval.getValue1() <= value && value <= this.secondInterval.getValue2());
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format(
            "%s: %s-%s or %s-%s",
            this.name,
            this.firstInterval.getValue1(),
            this.firstInterval.getValue2(),
            this.secondInterval.getValue1(),
            this.secondInterval.getValue2()
        );
    }
}
