package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle09;
import da.klnq.util.IOUtils;

public class TestPuzzle09 {
    private static final String RESOURCE = "/09-test-input.txt";
    private static final int PREAMBLE_LENGTH = 5;
    private static final long TEST_RESULT_PART_1 = 127;
    private static final long TEST_RESULT_PART_2 = 62;

    @Test
    public void testPart1() {
        final long number = Puzzle09.solvePart1(IOUtils.readResource(RESOURCE), PREAMBLE_LENGTH);
        assertEquals(TEST_RESULT_PART_1, number);
    }

    @Test
    public void testPart2() {
        final long number = Puzzle09.solvePart2(IOUtils.readResource(RESOURCE), PREAMBLE_LENGTH);
        assertEquals(TEST_RESULT_PART_2, number);
    }

}
