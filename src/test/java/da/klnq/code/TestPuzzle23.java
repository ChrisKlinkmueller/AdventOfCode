package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle23;
import da.klnq.util.IOUtils;

public class TestPuzzle23 {
    private static final String RESOURCE = "/23-test-input.txt";
    private static final String TEST_RESULT_PART_1 = "67384529";
    private static final BigInteger TEST_RESULT_PART_2 = BigInteger.valueOf(149245887792L);

    @Test
    public void testPart1() {
        final String result = Puzzle23.solvePart1(IOUtils.readResource(RESOURCE).get(0));
        assertEquals(TEST_RESULT_PART_1, result);
    }

    @Test
    public void testPart2() {
        final BigInteger result = Puzzle23.solvePart2(IOUtils.readResource(RESOURCE).get(0));
        assertEquals(TEST_RESULT_PART_2, result);
    }
    
}
