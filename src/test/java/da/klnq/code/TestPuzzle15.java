package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import da.klnq.advent.Puzzle15;
import da.klnq.util.IOUtils;

public class TestPuzzle15 {
    private static final String RESOURCE = "/15-test-input.txt";

    @ParameterizedTest
    @MethodSource("testCases")
    public void test(String input, int lastRound, int expectedResult) {
        assertEquals(expectedResult, Puzzle15.solve(input, lastRound));
    }

    public static Stream<Arguments> testCases() {
        return IOUtils.readResource(RESOURCE)
            .stream()
            .map(line -> line.split(" "))
            .map(parts -> Arguments.of(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
    }
}
