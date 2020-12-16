package da.klnq.code.day09;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public class Numbers {
    private static final String RESOURCE = "/day09/input1.txt";
    private final int preambleLength;
    private final List<Long> numbers;

    public Numbers(List<Long> numbers) {
        this.preambleLength = 25;
        this.numbers = new ArrayList<>(numbers);
    }

    public int getPreambleLength() {
        return this.preambleLength;
    }

    public int count() {
        return this.numbers.size();
    }

    public long getNumber(int index) {
        return this.numbers.get(index);
    }

    public LongStream stream(int startIncl, int endExcl) {
        assert 0 <= startIncl && startIncl < this.count();
        assert startIncl < endExcl && endExcl <= this.count();
        return IntStream.range(startIncl, endExcl)
            .mapToLong(this.numbers::get);
    }

    public static Numbers readNumbers() {
        final Try<List<Long>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseLong);
        assert readResult.isFailure() : readResult.exception().getMessage();
        return new Numbers(readResult.get());
    }
}
