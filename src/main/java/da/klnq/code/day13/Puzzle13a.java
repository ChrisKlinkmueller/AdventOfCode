package da.klnq.code.day13;

import java.math.BigInteger;

import da.klnq.util.Tuple2;

public class Puzzle13a {
    
    public static void main(String[] args) {
        final BusSchedule schedule = BusSchedule.readSchedule();

        final Tuple2<BigInteger, BigInteger> preference = schedule.getBusIds().stream()
            .map(bus -> computeBusWaitTime(schedule.getTimestamp(), bus))
            .min((b1, b2) -> b1.get2().compareTo(b2.get2()))
            .get();
        System.out.println(preference.get1().multiply(preference.get2()));
    }

    private static Tuple2<BigInteger, BigInteger> computeBusWaitTime(BigInteger timestamp, BigInteger bus) {
        final BigInteger waitTime = timestamp.mod(bus).equals(BigInteger.ZERO) 
            ? BigInteger.ZERO 
            : BigInteger.ONE.add(timestamp.divide(bus)).multiply(bus).subtract(timestamp);
        return new Tuple2<BigInteger,BigInteger>(bus, waitTime);
    }
}
