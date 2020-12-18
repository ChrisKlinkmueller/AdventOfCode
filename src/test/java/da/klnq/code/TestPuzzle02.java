package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle02;
import da.klnq.util.IOUtils;

public class TestPuzzle02 {
    private static final String TEST_RESOURCE = "/02-test-input.txt";
    private static final long RESULT_PART_1 = 2;
    private static final long RESULT_PART_2 = 1;
    
    @Test
    public void testPart1() {
        final List<String> input = IOUtils.readResource(TEST_RESOURCE);
        final long validPasswords = Puzzle02.solvePart1(input);
        assertEquals(RESULT_PART_1, validPasswords);
    }

    @Test
    public void testPart2() {
        final List<String> input = IOUtils.readResource(TEST_RESOURCE);
        final long validPasswords = Puzzle02.solvePart2(input);
        assertEquals(RESULT_PART_2, validPasswords);
    }
}
