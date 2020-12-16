package da.klnq.code.day08;

import java.util.List;

import da.klnq.code.util.Tuple2;

public class Puzzle08a {
    
    public static void main(String[] args) {
        final List<Instruction> instructions = Instruction.readInstructions();
        final Tuple2<Boolean, Integer> result = Instruction.execute(instructions);
        System.out.println(result.getValue2());
    }
}
