package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle05;
import da.klnq.util.IOUtils;

public class TestPuzzle05 {
    private static final String RESOURCE_PART_1 = "/05-test-input-a.txt";
    private static final int TEST_RESULT_PART_1 = 820;

    @Test
    public void testPart1() {
        final List<String> input = IOUtils.readResource(RESOURCE_PART_1);
        final int maxSeat = Puzzle05.solvePart1(input);
        assertEquals(TEST_RESULT_PART_1, maxSeat);
    }
}
