package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import da.klnq.advent.Puzzle13;
import da.klnq.util.IOUtils;

public class TestPuzzle13 {
    private static final String RESOURCE_PART_1 = "/13-test-input.txt";
    private static final BigInteger TEST_RESULT_PART_1 = BigInteger.valueOf(295L);

    @Test
    public void testPart1() {
        final BigInteger result = Puzzle13.solvePart1(IOUtils.readResource(RESOURCE_PART_1));
        assertEquals(TEST_RESULT_PART_1, result);
    }

    @ParameterizedTest
    @MethodSource("testCasesPart2")
    public void testPart2(List<String> input, BigInteger expectedResult) {
        final BigInteger result = Puzzle13.solvePart2(input);
        assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> testCasesPart2() {
        return Stream.of(
            Arguments.of(List.of("0", "17,x,13,19"), BigInteger.valueOf(3417L)),
            Arguments.of(List.of("0", "67,7,59,61"), BigInteger.valueOf(754018L)),
            Arguments.of(List.of("0", "67,x,7,59,61"), BigInteger.valueOf(779210L)),
            Arguments.of(List.of("0", "67,7,x,59,61"), BigInteger.valueOf(1261476L)),
            Arguments.of(List.of("0", "1789,37,47,1889"), BigInteger.valueOf(1202161486L))
        );
    }

}
