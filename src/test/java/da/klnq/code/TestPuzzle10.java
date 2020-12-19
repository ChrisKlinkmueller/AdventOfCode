package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle10;
import da.klnq.util.IOUtils;

public class TestPuzzle10 {
    private static final String RESOURCE = "/10-test-input.txt";
    private static final int TEST_RESULT_PART_1 = 220;
    private static final BigInteger TEST_RESULT_PART_2 = BigInteger.valueOf(19208L);

    @Test
    public void testPart1() {
        final int number = Puzzle10.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, number);
    }

    @Test
    public void testPart2() {
        final BigInteger number = Puzzle10.solvePart2(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_2, number);
    }

}
