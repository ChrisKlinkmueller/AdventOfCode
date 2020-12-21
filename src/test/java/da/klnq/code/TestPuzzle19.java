package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle19;
import da.klnq.util.IOUtils;

public class TestPuzzle19 {
    private static final String RESOURCE_PART_1 = "/19-test-input-a.txt";
    private static final String RESOURCE_PART_2 = "/19-test-input-b.txt";
    private static final long TEST_RESULT_PART_1 = 2;
    private static final long TEST_RESULT_PART_2 = 12;

    @Test
    public void testPart1() {
        final long result = new Puzzle19().solvePart1(IOUtils.readResource(RESOURCE_PART_1));
        assertEquals(TEST_RESULT_PART_1, result);
    }

    @Test
    public void testPart2() {
        final long result = new Puzzle19().solvePart2(IOUtils.readResource(RESOURCE_PART_2));
        assertEquals(TEST_RESULT_PART_2, result);
    }

}
