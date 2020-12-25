package da.klnq.advent;

import java.util.List;
import java.util.function.BiPredicate;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle25 {
    private static final String RESOURCE = "/25-task-input.txt";
    private static final long DIVISOR = 20201227;
    private static final long INIT_SUBJECT_NUMBER = 7;

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution: " + solve(input));
    }

    public static long solve(List<String> input) {
        final long pubKey1 = Long.parseLong(input.get(0));
        final long loopSize1 = transformSubjectNumber(INIT_SUBJECT_NUMBER, (v, l) -> v != pubKey1).get2();

        final long pubKey2 = Long.parseLong(input.get(1));
        return transformSubjectNumber(pubKey2, (v, l) -> l < loopSize1).get1();
    }

    public static Tuple2<Long,Long> transformSubjectNumber(
        long subjectNumber, 
        BiPredicate<Long,Long> computeStop
    ) {
        long value = 1;
        long loopSize = 0;
        while (computeStop.test(value, loopSize)) {
            value = (value * subjectNumber) % DIVISOR;
            loopSize++;
        }
        return new Tuple2<Long,Long>(value, loopSize);
    }

}
