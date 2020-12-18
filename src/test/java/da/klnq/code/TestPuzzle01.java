package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.OptionalInt;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle01;
import da.klnq.code.util.IOUtils;

public class TestPuzzle01 {
    private static final String TEST_RESOURCE = "/01-test-input.txt";
    private static final int SUM_PARAMETER = 2020;
    private static final int TEST_RESULT_PART_1 = 514579;
    private static final int TEST_RESULT_PART_2 = 241861950;
    
    @Test
    public void testPart1() {
        final OptionalInt answer = executePart1(TEST_RESOURCE, SUM_PARAMETER);
        assertTrue(answer.isPresent());
        assertEquals(TEST_RESULT_PART_1, answer.getAsInt());
    }

    @Test
    public void testPart2() {
        final OptionalInt answer = executePart2(TEST_RESOURCE, SUM_PARAMETER);
        assertTrue(answer.isPresent());
        assertEquals(TEST_RESULT_PART_2, answer.getAsInt());
    }

    private static OptionalInt executePart1(String resource, int sum) {
        final List<String> expenses = readExpenses(resource);
        return Puzzle01.solvePart1(expenses, sum);
    }

    private static OptionalInt executePart2(String resource, int sum) {
        final List<String> expenses = readExpenses(resource);
        return Puzzle01.solvePart2(expenses, sum);
    }

    private static List<String> readExpenses(String resource) {
        return IOUtils.readResource(resource, IOUtils::parseString)
            .handleException(ex -> assertTrue(false, ex.getMessage()))
            .get();
    }

}
