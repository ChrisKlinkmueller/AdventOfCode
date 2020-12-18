package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import da.klnq.advent.Puzzle07;
import da.klnq.util.IOUtils;

public class TestPuzzle07 {
    private static final String RESOURCE_A = "/07-test-input-a.txt";
    private static final String RESOURCE_B = "/07-test-input-b.txt";
    private static final String BAG = "shiny gold";

    @ParameterizedTest
    @MethodSource("testCasesPart1")
    public void testPart1(String resource, String bag, int expectedCount) {
        int count = Puzzle07.solvePart1(IOUtils.readResource(resource), bag);
        assertEquals(expectedCount, count);
    }

    @ParameterizedTest
    @MethodSource("testCasesPart2")
    public void testPart2(String resource, String bag, int expectedCount) {
        int count = Puzzle07.solvePart2(IOUtils.readResource(resource), bag);
        assertEquals(expectedCount, count);
    }

    public static Stream<Arguments> testCasesPart1() {
        return Stream.of(
            Arguments.of(RESOURCE_A, BAG, 4),
            Arguments.of(RESOURCE_B, BAG, 0)
        );
    }

    public static Stream<Arguments> testCasesPart2() {
        return Stream.of(
            Arguments.of(RESOURCE_A, BAG, 32),
            Arguments.of(RESOURCE_B, BAG, 126)
        );
    }
}
