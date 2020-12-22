package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle21;
import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class TestPuzzle21 {
    private static final String RESOURCE = "/21-test-input.txt";
    private static final long TEST_RESULT_PART_1 = 5;
    private static final String TEST_RESULT_PART_2 = "mxmxvkd,sqjhc,fvjkl";

    @Test
    public void testPart1() {
        final long result = Puzzle21.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, result);
    }

    @Test
    public void testPart2() {
        final String result = Puzzle21.solvePart2(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_2, result);
    }

    @Test
    public void testParseFoods() {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final List<Tuple2<Set<String>, Set<String>>> foods = Puzzle21.parseFoods(input);
        assertEquals(input.size(), foods.size());

        final List<Tuple2<Set<String>, Set<String>>> expectedFoods = List.of(
            new Tuple2<>(Set.of("mxmxvkd", "kfcds", "sqjhc", "nhms"), Set.of("dairy", "fish")),
            new Tuple2<>(Set.of("trh", "fvjkl", "sbzzf", "mxmxvkd"), Set.of("dairy")),
            new Tuple2<>(Set.of("sqjhc", "fvjkl"), Set.of("soy")),
            new Tuple2<>(Set.of("sqjhc", "mxmxvkd", "sbzzf"), Set.of("fish"))
        );

        for (int i = 0; i < input.size(); i++) {
            final Tuple2<Set<String>, Set<String>> food = foods.get(i);
            final Tuple2<Set<String>, Set<String>> expected = expectedFoods.get(i);
            assertTrue(food.get1().containsAll(expected.get1()));
            assertTrue(food.get2().containsAll(expected.get2()));
            assertTrue(expected.get1().containsAll(food.get1()));
            assertTrue(expected.get2().containsAll(food.get2()));
        }
    }

    @Test
    public void testExtractRules() {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final List<Tuple2<Set<String>, Set<String>>> foods = Puzzle21.parseFoods(input);
        final List<Tuple2<String, String>> rules = Puzzle21.extractRules(foods);

        Stream.of(
            new Tuple2<String,String>("fvjkl", "dairy"),
            new Tuple2<String,String>("fvjkl", "soy"),
            new Tuple2<String,String>("kfcds", "dairy"),
            new Tuple2<String,String>("kfcds", "fish"),
            new Tuple2<String,String>("mxmxvkd", "dairy"),
            new Tuple2<String,String>("mxmxvkd", "fish"),
            new Tuple2<String,String>("nhms", "dairy"),
            new Tuple2<String,String>("nhms", "fish"),
            new Tuple2<String,String>("sbzzf", "dairy"),
            new Tuple2<String,String>("sbzzf", "fish"),
            new Tuple2<String,String>("sqjhc", "dairy"),
            new Tuple2<String,String>("sqjhc", "fish"),
            new Tuple2<String,String>("sqjhc", "soy"),
            new Tuple2<String,String>("trh", "dairy")
        )
        .forEach(rule -> assertTrue(Puzzle21.containsRule(rules, rule), String.format("%s -> %s", rule.get1(), rule.get2())));
    }
}
