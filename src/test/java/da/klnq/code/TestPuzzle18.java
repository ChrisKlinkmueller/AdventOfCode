package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle18;
import da.klnq.util.IOUtils;

public class TestPuzzle18 {
    private static final String RESOURCE = "/18-test-input.txt";
    private static final BigInteger TEST_RESULT_PART_1 = BigInteger.valueOf(26457L);
    private static final BigInteger TEST_RESULT_PART_2 = BigInteger.valueOf(694173L);
    
    @Test
    public void testPart1() {
        final BigInteger result = Puzzle18.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, result);
    }
    
    @Test
    public void testPart2() {
        final BigInteger result = Puzzle18.solvePart2(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_2, result);
    }

}
