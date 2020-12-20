package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle14;
import da.klnq.util.IOUtils;

public class TestPuzzle14 {
    private static final String RESOURCE_PART_1 = "/14-test-input-a.txt";
    private static final String RESOURCE_PART_2 = "/14-test-input-b.txt";
    private static final long TEST_RESULT_PART_1 = 165;
    private static final long TEST_RESULT_PART_2 = 208;

    @Test
    public void testPart1() {
        final Puzzle14 puzzle = new Puzzle14();
        final long result = puzzle.solvePart1(IOUtils.readResource(RESOURCE_PART_1));
        assertEquals(TEST_RESULT_PART_1, result);
    }

    @Test
    public void testPart2() {
        final Puzzle14 puzzle = new Puzzle14();
        final long result = puzzle.solvePart2(IOUtils.readResource(RESOURCE_PART_2));
        assertEquals(TEST_RESULT_PART_2, result);
    }

}
