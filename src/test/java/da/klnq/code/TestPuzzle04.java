package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle04;
import da.klnq.util.IOUtils;

public class TestPuzzle04 {
    private static final String RESOURCE_PART_1 = "/04-test-input-a.txt";
    private static final String RESOURCE_PART_2 = "/04-test-input-b.txt";
    private static final long TEST_RESULT_PART_1 = 2;
    private static final long TEST_RESULT_PART_2 = 4;

    @Test
    public void testPart1() {
        final List<String> input = IOUtils.readResource(RESOURCE_PART_1);
        final long validPassports = Puzzle04.solvePart1(input);
        assertEquals(TEST_RESULT_PART_1, validPassports);
    }

    @Test
    public void testPart2() {
        final List<String> input = IOUtils.readResource(RESOURCE_PART_2);
        final long validPassports = Puzzle04.solvePart2(input);
        assertEquals(TEST_RESULT_PART_2, validPassports);
    }
}
