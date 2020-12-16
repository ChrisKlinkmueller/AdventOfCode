package da.klnq.code.day10;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Puzzle10b {
    
    public static void main(String[] args) {
        final JoltageAdapters adapters = JoltageAdapters.readAdapters();

        final Map<Integer, BigInteger> pathCounts = new HashMap<>();
        pathCounts.put(0, BigInteger.ONE);

        for (int index = 0; index < adapters.count(); index++) {
            addPathCount(adapters.get(index), pathCounts);
        }

        System.out.println(pathCounts.get(adapters.get(adapters.count() - 1)));
    }

    private static void addPathCount(int number, Map<Integer, BigInteger> pathCounts) {
        final BigInteger paths1 = pathCounts.getOrDefault(number - 1, BigInteger.ZERO);
        final BigInteger paths2 = pathCounts.getOrDefault(number - 2, BigInteger.ZERO);
        final BigInteger paths3 = pathCounts.getOrDefault(number - 3, BigInteger.ZERO);
        final BigInteger sum = paths1.add(paths2).add(paths3);
        pathCounts.put(number, sum);
    }

}
