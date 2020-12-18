package da.klnq.code.day16;

import da.klnq.util.Tuple2;

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
        return (this.firstInterval.get1() <= value && value <= this.firstInterval.get2())
            || (this.secondInterval.get1() <= value && value <= this.secondInterval.get2());
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format(
            "%s: %s-%s or %s-%s",
            this.name,
            this.firstInterval.get1(),
            this.firstInterval.get2(),
            this.secondInterval.get1(),
            this.secondInterval.get2()
        );
    }
}
