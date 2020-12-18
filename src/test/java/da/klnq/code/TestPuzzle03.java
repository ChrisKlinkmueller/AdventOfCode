package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle03;
import da.klnq.code.util.IOUtils;

public class TestPuzzle03 {
    private static final String RESOURCE = "/03-test-input.txt";
    private static final long TEST_RESULT_1 = 7;
    private static final long TEST_RESULT_2 = 336;

    @Test
    public void testPart1() {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final long trees = Puzzle03.solvePart1(input);
        assertEquals(TEST_RESULT_1, trees);
    }

    @Test
    public void testPart2() {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final long trees = Puzzle03.solvePart2(input);
        assertEquals(TEST_RESULT_2, trees);
    }
}
