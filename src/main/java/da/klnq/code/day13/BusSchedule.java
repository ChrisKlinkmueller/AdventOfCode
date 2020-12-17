package da.klnq.code.day13;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public class BusSchedule {
    private static final String RESOURCE = "/day13/input1.txt";

    private final BigInteger timestamp;
    private final Map<BigInteger, BigInteger> ids;

    private BusSchedule(BigInteger timestamp) {
        this.timestamp = timestamp;
        this.ids = new LinkedHashMap<>();
    }

    private void add(BigInteger bus, BigInteger position) {
        this.ids.put(bus, position);
    }

    public Collection<BigInteger> getBusIds() {
        return this.ids.keySet();
    }

    public Set<Entry<BigInteger, BigInteger>> getBusPositions() {
        return this.ids.entrySet();
    }

    public BigInteger getTimestamp() {
        return timestamp;
    }

    public static BusSchedule readSchedule() {
        final Try<List<String>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseString);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        assert readResult.get().size() == 2;

        final BigInteger timestamp = new BigInteger(readResult.get().get(0));
        final BusSchedule schedule = new BusSchedule(timestamp);

        final String[] buses = readResult.get().get(1).split(",");
        for (int pos = 0; pos < buses.length; pos++) {
            if (!buses[pos].equals("x")) {
                schedule.add(
                    new BigInteger(buses[pos]), 
                    BigInteger.valueOf(pos)
                );
            }
        }
        
        return schedule;
    }
}

