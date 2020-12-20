package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle16;
import da.klnq.util.IOUtils;

public class TestPuzzle16 {
    private static final String RESOURCE = "/16-test-input.txt";
    private static final int TEST_RESULT_PART_1 = 71;

    @Test
    public void testPart1() {
        final Puzzle16 puzzle = new Puzzle16();
        final int result = puzzle.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, result);
    }

}
