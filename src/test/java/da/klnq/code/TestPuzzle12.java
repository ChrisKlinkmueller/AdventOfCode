package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle12;
import da.klnq.util.IOUtils;

public class TestPuzzle12 {
    private static final String RESOURCE = "/12-test-input.txt";
    private static final int TEST_RESULT_PART_1 = 25;
    private static final int TEST_RESULT_PART_2 = 286;

    @Test
    public void testPart1() {
        final int count = Puzzle12.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, count);
    }

    @Test
    public void testPart2() {
        final int count = Puzzle12.solvePart2(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_2, count);
    }

}
