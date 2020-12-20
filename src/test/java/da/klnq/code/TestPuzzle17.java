package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle17;
import da.klnq.util.IOUtils;

public class TestPuzzle17 {
    private static final String RESOURCE = "/17-test-input.txt";
    private static final int CYCLES = 6;
    private static final long TEST_RESULT_PART_1 = 112L;
    private static final long TEST_RESULT_PART_2 = 848L;

    @Test
    public void testPart1() {
        final int activeCubes = Puzzle17.solvePart1(IOUtils.readResource(RESOURCE), CYCLES);
        assertEquals(TEST_RESULT_PART_1, activeCubes);
    }

    @Test
    public void testPart2() {
        final int activeCubes = Puzzle17.solvePart2(IOUtils.readResource(RESOURCE), CYCLES);
        assertEquals(TEST_RESULT_PART_2, activeCubes);
    }
}
