package da.klnq.code.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public class JoltageAdapters {
    private static final String RESOURCE = "/day10/input1.txt";
    private final List<Integer> adapters;

    private JoltageAdapters(List<Integer> adapters) {
        this.adapters = new ArrayList<>(adapters);
        Collections.sort(this.adapters);        
    }

    public int count() {
        return this.adapters.size();        
    }

    public int get(int index) {
        assert 0 <= index && index < this.count();
        return this.adapters.get(index);
    }

    public static JoltageAdapters readAdapters() {
        final Try<List<Integer>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseInteger);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        return new JoltageAdapters(readResult.get());
    }
}
