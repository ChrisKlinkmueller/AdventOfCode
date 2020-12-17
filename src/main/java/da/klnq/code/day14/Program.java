package da.klnq.code.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public abstract class Program {
    private static final String RESOURCE = "/day14/input1.txt";
    private final List<String> commands;
    protected final Map<Long, Long> memory;
    protected String mask;

    protected Program() {
        final Try<List<String>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseString);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        this.commands = readResult.get();
        this.memory = new HashMap<>();
    }

    public void execute() {
        this.commands.forEach(this::execute);

        final long value = this.memory.values().stream()
            .mapToLong(v -> v)
            .sum();
        System.out.println(value);
    }

    private void execute(String command) {
        if (command.startsWith("mask")) {
            this.updateMask(command.substring(7));
        }
        else {
            final String[] parts = command.split("=");
            final long address = Long.parseLong(parts[0].trim().replaceAll("[me\\[\\]]", ""));
            final long value = Long.parseLong(parts[1].trim());
            this.storeValue(address, value);
        }
    }

    protected String toBitMap(long value) {
        final StringBuilder builder = new StringBuilder();
        while (value != 0) {
            builder.insert(0, value % 2);
            value /= 2;
        }

        while (builder.length() < 36) {
            builder.insert(0, value);
        }       

        return builder.toString();
    }

    protected long fromBitMap(String encodedValue) {
        long value = 0;
        for (int i = 0; i < encodedValue.length(); i++) {
            if (encodedValue.charAt(i) == '1') {
                value += (long)Math.pow(2, 35 - i);
            }
        }
        return value;
    }

    private void updateMask(String mask) {
        this.mask = mask;
    }

    protected abstract void storeValue(long address, long value);

}
