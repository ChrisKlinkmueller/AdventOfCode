package da.klnq.code.day04;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Passport {
    private static final String RESOURCE = "/day04/input1.txt";
    private final Map<String, String> fields;

    private Passport() {
        this.fields = new LinkedHashMap<>();
    }

    private void addField(String key, String value) {
        this.fields.put(key, value);
    }

    public boolean containsField(String field) {
        return this.fields.containsKey(field);
    }

    public Set<String> getFields() {
        return this.fields.keySet();
    }

    public Stream<Tuple2<String, String>> fieldStream() {
        return this.fields.entrySet().stream()
            .map(e -> new Tuple2<>(e.getKey(), e.getValue()));
    }

    @Override
    public String toString() {
        return fields.keySet().stream().collect(Collectors.joining(", "));
    }

    public static List<Passport> readPassports() {
        final LinkedList<Passport> passports = new LinkedList<>();
        passports.add(new Passport());
        for (String line : IOUtils.readResource(RESOURCE)) {
            if (line.isBlank()) {
                passports.addFirst(new Passport());
            }
            else {
                final String[] lineParts = line.split("\\s+");
                for (String linePart : lineParts) {
                    final String[] fieldParts = linePart.split(":");
                    passports.getFirst().addField(fieldParts[0], fieldParts[1]);
                }
            }
        }

        return passports;
    }

    

}
