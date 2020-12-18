package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle08;
import da.klnq.util.IOUtils;

public class TestPuzzle08 {
    private static final String RESOURCE = "/08-test-input.txt";
    private static final int TEST_RESULT_PART_1 = 5;
    private static final int TEST_RESULT_PART_2 = 8;

    @Test
    public void testPart1() {
        final int count = Puzzle08.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, count);
    }

    @Test
    public void testPart2() {
        final int count = Puzzle08.solvePart2(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_2, count);
    }
}
