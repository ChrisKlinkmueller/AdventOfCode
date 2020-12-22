package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle22;
import da.klnq.util.IOUtils;

public class TestPuzzle22 {
    private static final String RESOURCE = "/22-test-input.txt";
    private static final long TEST_RESULT_PART_1 = 306;

    @Test
    public void test() {
        final long result = Puzzle22.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, result);
    }
}
