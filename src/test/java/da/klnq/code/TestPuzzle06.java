package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle06;
import da.klnq.util.IOUtils;

public class TestPuzzle06 {
    private static final String RESOURCE = "/06-test-input.txt";
    private static final long TEST_RESULT_PART_1 = 11;
    private static final long TEST_RESULT_PART_2 = 6;

    @Test
    public void testPart1() {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final long count = Puzzle06.solvePart1(input);
        assertEquals(TEST_RESULT_PART_1, count);
    }

    @Test
    public void testPart2() {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final long count = Puzzle06.solvePart2(input);
        assertEquals(TEST_RESULT_PART_2, count);
    }
}
