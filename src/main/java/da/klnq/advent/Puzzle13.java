package da.klnq.advent;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle13 {
    private static final String RESOURCE = "/13-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static BigInteger solvePart1(List<String> input) {
        final Tuple2<BigInteger, Map<BigInteger, BigInteger>> schedule = parseInput(input);
        final Map<BigInteger, BigInteger> buses = schedule.get2();

        final Tuple2<BigInteger, BigInteger> preference = buses.keySet().stream()
            .map(bus -> computeBusWaitTime(schedule.get1(), bus))
            .min((b1, b2) -> b1.get2().compareTo(b2.get2()))
            .get();
        return preference.get1().multiply(preference.get2());
    }

    public static BigInteger solvePart2(List<String> input) {
        final Tuple2<BigInteger, Map<BigInteger, BigInteger>> schedule = parseInput(input);
        final Map<BigInteger, BigInteger> buses = schedule.get2();
        
        final Entry<BigInteger, BigInteger> firstBus = buses.entrySet().stream().findFirst().get();
        final AtomicReference<BigInteger> timestamp = new AtomicReference<>(firstBus.getKey());
        final AtomicReference<BigInteger> waitTime = new AtomicReference<>(firstBus.getKey());

        buses.entrySet().stream()
            .skip(1)
            .forEach(bus -> {
                while (!timestamp.get().add(bus.getValue()).mod(bus.getKey()).equals(BigInteger.ZERO)) {
                    timestamp.set(timestamp.get().add(waitTime.get()));
                }
                waitTime.set(waitTime.get().multiply(bus.getKey()));
            });

        return timestamp.get();
    }

    private static Tuple2<BigInteger, BigInteger> computeBusWaitTime(BigInteger timestamp, BigInteger bus) {
        final BigInteger waitTime = timestamp.mod(bus).equals(BigInteger.ZERO) 
            ? BigInteger.ZERO 
            : BigInteger.ONE.add(timestamp.divide(bus)).multiply(bus).subtract(timestamp);
        return new Tuple2<BigInteger,BigInteger>(bus, waitTime);
    }

    private static Tuple2<BigInteger, Map<BigInteger, BigInteger>> parseInput(List<String> input) {
        final BigInteger timestamp = new BigInteger(input.get(0));
        final Map<BigInteger, BigInteger> ids = new LinkedHashMap<>();

        final String[] buses = input.get(1).split(",");
        for (int pos = 0; pos < buses.length; pos++) {
            if (!buses[pos].equals("x")) {
                ids.put(
                    new BigInteger(buses[pos]), 
                    BigInteger.valueOf(pos)
                );
            }
        }
        
        return new Tuple2<>(timestamp, ids);
    }
}
