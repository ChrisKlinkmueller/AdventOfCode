package da.klnq.advent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple4;

public class Puzzle02 {
    private static final Pattern POLICY_CHECK_PATTERN = Pattern.compile("^([0-9]+)-([0-9]+)\\s+([a-z]):\\s+([a-z]+)");
    private static final String RESOURCE = "/02-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        
        long validPasswords = solvePart1(input);
        System.out.println(String.format("Solution for part 1: %s", validPasswords));

        validPasswords = solvePart2(input);
        System.out.println(String.format("Solution for part 2: %s", validPasswords));
    }

    public static long solvePart1(List<String> input) {
        return input.stream()
            .map(Puzzle02::parsePolicyCheck)
            .filter(Puzzle02::isValidPart1)
            .count();        
    }

    public static long solvePart2(List<String> input) {
        return input.stream()
            .map(Puzzle02::parsePolicyCheck)
            .filter(Puzzle02::isValidPart2)
            .count();        
    }

    private static boolean isValidPart1(Tuple4<Long, Long, Character, String> check) {
        final int occ = (int)check.get4().chars()
            .filter(c -> c == check.get3())
            .count();
        return check.get1() <= occ && occ <= check.get2();
    }

    private static boolean isValidPart2(Tuple4<Long, Long, Character, String> check) {
        return hasCharacterAtIndex(check.get4(), check.get1(), check.get3())
             ^ hasCharacterAtIndex(check.get4(), check.get2(), check.get3());
    }

    private static boolean hasCharacterAtIndex(String text, long index, char c) {
        return index <= text.length() && text.charAt((int)index - 1) == c;
    }

    private static Tuple4<Long, Long, Character, String> parsePolicyCheck(String text) {
        final Matcher matcher = POLICY_CHECK_PATTERN.matcher(text);
        matcher.find();
        return new Tuple4<>(
            Long.parseLong(matcher.group(1)),   // min value
            Long.parseLong(matcher.group(2)),   // max value
            matcher.group(3).charAt(0),         // character
            matcher.group(4)                    // password                        
        );
    }
}
