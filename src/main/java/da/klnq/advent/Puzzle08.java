package da.klnq.advent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle08 {
    private static final String RESOURCE = "/08-task-input.txt";
    private static final String ACCUMULATE = "acc";
    private static final String JUMP = "jmp";
    private static final String NO_OPERATION = "nop";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 1: " + solvePart2(input));
    }    

    public static int solvePart1(List<String> input) {
        return execute(readInstructions(input)).get2();
    }

    public static int solvePart2(List<String> input) {
        final List<Tuple2<String, Integer>> instructions = readInstructions(input);

        for (int i = 0; i < instructions.size(); i++) {
            final Tuple2<Boolean, Integer> result = executeModification(instructions, i);
            if (result.get1()) {
                return result.get2();
            }
        }

        throw new IllegalStateException("no solution found");
    }

    private static Tuple2<Boolean, Integer> executeModification(List<Tuple2<String, Integer>> instructions, int index) {
        final Tuple2<String, Integer> inst = instructions.get(index);
        if (inst.get1().equals(ACCUMULATE)) {
            return new Tuple2<>(false, 0);
        }

        instructions.remove(index);
        instructions.add(index, inst.modify(c -> c.equals(JUMP) ? NO_OPERATION : JUMP, i -> i));
        final Tuple2<Boolean, Integer> result = execute(instructions);
        instructions.remove(index);
        instructions.add(index, inst);

        return result;
    }

    private static List<Tuple2<String, Integer>> readInstructions(List<String> input) {
        return input.stream()
           .map(Puzzle08::parseInstruction)
           .collect(Collectors.toList());
    }

    private static Tuple2<String, Integer> parseInstruction(String text) {
        final String command = text.substring(0, 3);
        final int argument = Integer.parseInt(text.substring(4));
        return new Tuple2<>(command, argument);
    }

    private static Tuple2<Boolean, Integer> execute(List<Tuple2<String, Integer>> instructions) {
        int accumulator = 0;
        int position = 0;
        final Set<Integer> visited = new HashSet<>();

        while (position < instructions.size()) {
            if (visited.contains(position)) {
                return new Tuple2<>(false, accumulator);
            }

            visited.add(position);

            final Tuple2<String, Integer> inst = instructions.get(position);
            switch (inst.get1()) {
                case ACCUMULATE : {
                    accumulator += inst.get2();
                    position++;
                } break;
                case JUMP : {
                    position += inst.get2();
                } break;
                case NO_OPERATION : {
                    position++; 
                } break;
                default : throw new IllegalArgumentException("Unknown command: " + inst.get1());
            }
        }

        return new Tuple2<>(true, accumulator);
    }
}
