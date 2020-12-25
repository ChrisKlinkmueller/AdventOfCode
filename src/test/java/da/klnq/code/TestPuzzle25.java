package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle25;
import da.klnq.util.IOUtils;

public class TestPuzzle25 {
    private static final String RESOURCE = "/25-test-input.txt";
    private static final long TEST_RESULT = 14897079;

    @Test
    public void testSolve() {
        final long result = Puzzle25.solve(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT, result);
    }

}
