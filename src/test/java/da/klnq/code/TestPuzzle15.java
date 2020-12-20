package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import da.klnq.advent.Puzzle15;

public class TestPuzzle15 {
    
    @ParameterizedTest
    @MethodSource("testCases")
    public void test(String input, int lastRound, int expectedResult) {
        assertEquals(expectedResult, Puzzle15.solve(input, lastRound));
    }

    public static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of("0,3,6", 10, 0),
            Arguments.of("0,3,6", 2020, 436),
            Arguments.of("1,3,2", 2020, 1),
            Arguments.of("2,1,3", 2020, 10),
            Arguments.of("1,2,3", 2020, 27),
            Arguments.of("2,3,1", 2020, 78),
            Arguments.of("3,2,1", 2020, 438),
            Arguments.of("3,1,2", 2020, 1836),
            Arguments.of("0,3,6", 30000000, 175594),
            Arguments.of("1,3,2", 30000000, 2578),
            Arguments.of("2,1,3", 30000000, 3544142),
            Arguments.of("1,2,3", 30000000, 261214),
            Arguments.of("2,3,1", 30000000, 6895259),
            Arguments.of("3,2,1", 30000000, 18),
            Arguments.of("3,1,2", 30000000, 362)
        );
    }
}
