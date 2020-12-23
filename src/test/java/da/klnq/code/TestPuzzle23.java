package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle23;

public class TestPuzzle23 {
    private static final String INPUT = "389125467";
    private static final String TEST_RESULT_PART_1 = "67384529";
    private static final BigInteger TEST_RESULT_PART_2 = BigInteger.valueOf(149245887792L);

    @Test
    public void testPart1() {
        final String result = Puzzle23.solvePart1(INPUT);
        assertEquals(TEST_RESULT_PART_1, result);
    }

    @Test
    public void testPart2() {
        final BigInteger result = Puzzle23.solvePart2(INPUT);
        assertEquals(TEST_RESULT_PART_2, result);
    }
    
}
