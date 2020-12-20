package da.klnq.advent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import da.klnq.util.IOUtils;

public final class Puzzle14 {
    private static final String RESOURCE = "/14-task-input.txt";

    public static void main(String[] args) {
        final List<String> commands = IOUtils.readResource(RESOURCE);
        final Puzzle14 puzzle = new Puzzle14();
        System.out.println("Solutionfor part 1: " + puzzle.solvePart1(commands));
        System.out.println("Solutionfor part 2: " + puzzle.solvePart2(commands));
    }

    private final Map<Long, Long> memory;
    private String mask;

    public Puzzle14() {
        this.memory = new HashMap<>();
    }    
    
    public long solvePart1(List<String> commands) {
        return execute(commands, this::storeValuePart1);
    }    
    
    public long solvePart2(List<String> commands) {
        return execute(commands, this::storeValuePart2);
    }

    private long execute(List<String> commands, BiConsumer<Long, Long> storer) {
        commands.forEach(command -> this.execute(command, storer));

        final long value = this.memory.values().stream()
            .mapToLong(v -> v)
            .sum();
        
        this.memory.clear();

        return value;
    }

    private void execute(String command, BiConsumer<Long, Long> storer) {
        if (command.startsWith("mask")) {
            this.mask = command.substring(7);
        }
        else {
            final String[] parts = command.split("=");
            final long address = Long.parseLong(parts[0].trim().replaceAll("[me\\[\\]]", ""));
            final long value = Long.parseLong(parts[1].trim());
            storer.accept(address, value);
        }
    }

    private void storeValuePart1(long address, long value) {
        String bitMap = this.toBitMap(value);

        final StringBuilder maskedBitMap = new StringBuilder();
        for (int i = 0; i < this.mask.length(); i++) {
            maskedBitMap.append(this.mask.charAt(i) == 'X'
                ? bitMap.charAt(i) 
                : this.mask.charAt(i)
            );
        }

        final long maskedValue = this.fromBitMap(maskedBitMap.toString());
        this.memory.put(address, maskedValue);
    }

    private void storeValuePart2(long address, long value) {
        String bitMap = this.toBitMap(address);
        bitMap = this.applyMask(bitMap);
        this.storeValuePart2(bitMap, value);
    }

    private void storeValuePart2(String bitMap, long value) {        
        final int index = bitMap.indexOf("X");
        if (index < 0) {
            final long address = this.fromBitMap(bitMap);
            this.memory.put(address, value);
        }
        else {
            this.storeValuePart2(bitMap.replaceFirst("X", "0"), value);
            this.storeValuePart2(bitMap.replaceFirst("X", "1"), value);
        }
    }

    private String toBitMap(long value) {
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

    private long fromBitMap(String encodedValue) {
        long value = 0;
        for (int i = 0; i < encodedValue.length(); i++) {
            if (encodedValue.charAt(i) == '1') {
                value += (long)Math.pow(2, 35 - i);
            }
        }
        return value;
    }

    private String applyMask(String address) {
        final StringBuilder maskedAddress = new StringBuilder();
        for (int i = 0; i < this.mask.length(); i++) {
            if (this.mask.charAt(i) == '1') {
                maskedAddress.append('1');
            }
            else if (this.mask.charAt(i) == '0') {
                maskedAddress.append(address.charAt(i));
            }
            else if (this.mask.charAt(i) == 'X') {
                maskedAddress.append('X');
            }
        }
        return maskedAddress.toString();
    }
}
