package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle11;
import da.klnq.util.IOUtils;

public class TestPuzzle11 {
    private static final String RESOURCE = "/11-test-input.txt";
    private static final long TEST_RESULT_PART_1 = 37;
    private static final long TEST_RESULT_PART_2 = 26;

    @Test
    public void testPart1() {
        final Puzzle11 puzzle = new Puzzle11();
        final long count = puzzle.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, count);
    }

    @Test
    public void testPart2() {
        final Puzzle11 puzzle = new Puzzle11();
        final long count = puzzle.solvePart2(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_2, count);
    }

}
