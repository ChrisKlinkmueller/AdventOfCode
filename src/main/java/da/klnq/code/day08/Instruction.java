package da.klnq.code.day08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;
import da.klnq.code.util.Tuple2;

public class Instruction {
    private static final String RESOURCE = "/day08/input1.txt";
    public static final String ACCUMULATE = "acc";
    public static final String JUMP = "jmp";
    public static final String NO_OPERATION = "nop";

    private String command;
    private final int argument;

    private Instruction(String command, int argument) {
        this.command = command;
        this.argument = argument;
    }

    public int getArgument() {
        return this.argument;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.command, this.argument);
    }

    public static List<Instruction> readInstructions() {
        final Try<List<Instruction>> readResult = IOUtils.readResource(RESOURCE, Instruction::parseInstruction);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        return readResult.get();
    }

    private static Try<Instruction> parseInstruction(String text) {
        final String command = text.substring(0, 3);
        final int argument = Integer.parseInt(text.substring(4));
        return Try.of(new Instruction(command, argument));
    }

    public static Tuple2<Boolean, Integer> execute(List<Instruction> instructions) {
        int accumulator = 0;
        int position = 0;
        final Set<Integer> visited = new HashSet<>();

        while (position < instructions.size()) {
            if (visited.contains(position)) {
                return new Tuple2<>(false, accumulator);
            }

            visited.add(position);

            final Instruction inst = instructions.get(position);
            switch (inst.getCommand()) {
                case Instruction.ACCUMULATE : {
                    accumulator += inst.getArgument();
                    position++;
                } break;
                case Instruction.JUMP : {
                    position += inst.getArgument();
                } break;
                case Instruction.NO_OPERATION : {
                    position++; 
                } break;
                default : throw new IllegalArgumentException("Unknown command: " + inst.getCommand());
            }
        }

        return new Tuple2<>(true, accumulator);
    }
    
}
