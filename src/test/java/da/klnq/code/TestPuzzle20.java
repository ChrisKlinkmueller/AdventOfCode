package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle20;
import da.klnq.util.IOUtils;

public class TestPuzzle20 {
    private static final String RESOURCE = "/20-test-input.txt";
    private static final BigInteger TEST_RESULT_PART_1 = new BigInteger("20899048083289");
 
    @Test
    public void testPart1() {
        final BigInteger result = Puzzle20.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, result);
    }
}
