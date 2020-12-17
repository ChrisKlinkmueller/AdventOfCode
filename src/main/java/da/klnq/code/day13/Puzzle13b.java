package da.klnq.code.day13;

import java.math.BigInteger;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

public class Puzzle13b {
    
    public static void main(String[] args) {
        final BusSchedule schedule = BusSchedule.readSchedule();
        
        final Entry<BigInteger, BigInteger> firstBus = schedule.getBusPositions().stream().findFirst().get();
        final AtomicReference<BigInteger> timestamp = new AtomicReference<>(firstBus.getKey());
        final AtomicReference<BigInteger> waitTime = new AtomicReference<>(firstBus.getKey());

        schedule.getBusPositions().stream()
            .skip(1)
            .forEach(e -> {
                while (!timestamp.get().add(e.getValue()).mod(e.getKey()).equals(BigInteger.ZERO)) {
                    timestamp.set(timestamp.get().add(waitTime.get()));
                }
                waitTime.set(waitTime.get().multiply(e.getKey()));
            });

        System.out.println(timestamp.get());
    }


}
