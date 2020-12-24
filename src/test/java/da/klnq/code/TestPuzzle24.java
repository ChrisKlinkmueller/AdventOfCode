package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import da.klnq.advent.Puzzle24;
import da.klnq.util.IOUtils;

public class TestPuzzle24 {
    private static final String RESOURCE = "/24-test-input.txt";
    
    @ParameterizedTest
    @MethodSource("solveTestCases")
    public void test(int days, int expectedResults) {
        final int result = Puzzle24.solve(IOUtils.readResource(RESOURCE), days);
        assertEquals(expectedResults, result, String.format("Failed test case, % days", days));
    }

    private static Stream<Arguments> solveTestCases() {
        return Stream.of(
            Arguments.of(0, 10),
            Arguments.of(1, 15),
            Arguments.of(2, 12),
            Arguments.of(3, 25),
            Arguments.of(4, 14),
            Arguments.of(5, 23),
            Arguments.of(6, 28),
            Arguments.of(7, 41),
            Arguments.of(8, 37),
            Arguments.of(9, 49),
            Arguments.of(10, 37),
            Arguments.of(20, 132),
            Arguments.of(30, 259),
            Arguments.of(40, 406),
            Arguments.of(50, 566),
            Arguments.of(60, 788),
            Arguments.of(70, 1106),
            Arguments.of(80, 1373),
            Arguments.of(90, 1844),
            Arguments.of(100, 2208)
        );
    }
    
}
