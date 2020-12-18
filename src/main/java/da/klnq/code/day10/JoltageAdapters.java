package da.klnq.code.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;

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
        return new JoltageAdapters(
            IOUtils.readResource(RESOURCE)
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList())
        );
    }
}
