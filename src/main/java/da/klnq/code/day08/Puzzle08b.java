package da.klnq.code.day08;

import java.util.List;

import da.klnq.util.Tuple2;

public class Puzzle08b {
    
    public static void main(String[] args) {
        final List<Instruction> instructions = Instruction.readInstructions();

        for (int i = 0; i < instructions.size(); i++) {
            final Tuple2<Boolean, Integer> result = executeModification(instructions, i);
            if (result.get1()) {
                System.out.println(result.get2());
                return;
            }
        }

        System.out.println("no solution found");
    }

    private static Tuple2<Boolean, Integer> executeModification(List<Instruction> instructions, int index) {
        final Instruction inst = instructions.get(index);
        if (inst.getCommand().equals(Instruction.ACCUMULATE)) {
            return new Tuple2<>(false, 0);
        }

        final String oldCommand = inst.getCommand();
        inst.setCommand(oldCommand.equals(Instruction.JUMP) ? Instruction.NO_OPERATION : Instruction.JUMP);
        final Tuple2<Boolean, Integer> result = Instruction.execute(instructions);
        inst.setCommand(oldCommand);
        return result;
    }
}
