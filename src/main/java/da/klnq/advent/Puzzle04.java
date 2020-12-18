package da.klnq.advent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;

public class Puzzle04 {
    private static final String RESOURCE = "/04-task-input.txt";
    private static final Set<String> OPTINAL_FIELDS = Set.of("cid");
    private static final Map<String, Predicate<String>> FIELD_RULES = Map.of(
        "byr", value -> isValidNumber(value, 1920, 2002),
        "iyr", value -> isValidNumber(value, 2010, 2020),
        "eyr", value -> isValidNumber(value, 2020, 2030),
        "hgt", Puzzle04::isValidHeight,
        "hcl", Puzzle04::isValidHairColor,
        "ecl", Puzzle04::isValidEyeColor,
        "pid", Puzzle04::isValidPassportID,
        "cid", text -> true
    );

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        long validPassports = solvePart1(input);
        System.out.println("Solution for part 1: " + validPassports);

        validPassports = solvePart2(input);
        System.out.println("Solution for part 2: " + validPassports);
    }

    public static long solvePart1(List<String> input) {       
        return readPassports(input)
            .stream()
            .filter(passport -> areFieldsPresent(passport))
            .count();
    }

    public static long solvePart2(List<String> input) {
        return readPassports(input)
            .stream()
            .filter(passport -> areFieldsPresent(passport) && areFieldsValid(passport))
            .count();
    }

    public static boolean areFieldsPresent(Map<String, String> passport) {
        final Set<String> missingFields = FIELD_RULES.keySet().stream()
            .filter(field -> !passport.containsKey(field))
            .collect(Collectors.toSet());
        missingFields.removeAll(OPTINAL_FIELDS);
        return missingFields.isEmpty();
    }

    public static boolean areFieldsValid(Map<String, String> passport) {
        return passport.entrySet()
            .stream()
            .allMatch(Puzzle04::fieldMatches);
    }

    private static boolean fieldMatches(Entry<String, String> field) {
        return FIELD_RULES.getOrDefault(field.getKey(), t -> false)
            .test(field.getValue());
    }

    private static boolean isValidHeight(String text) {
        if (text.endsWith("cm")) {
            return isValidNumber(text.substring(0, text.length() - 2), 150, 193);
        }
        else if (text.endsWith("in")) {
            return isValidNumber(text.substring(0, text.length() - 2), 59, 76);
        }
        else {
            return false;
        }
    }

    private static boolean isValidHairColor(String text) {
        return text.matches("^#[0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f]");
    }

    private static final Set<String> HAIR_COLORS = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private static boolean isValidEyeColor(String text) {
        return HAIR_COLORS.contains(text);
    }

    private static boolean isValidPassportID(String text) {
        return text.length() == 9 && text.matches("^[0-9]*");
    }

    private static boolean isValidNumber(String text, int minIncl, int maxIncl) {
        try {
            final int year = Integer.parseInt(text);
            return minIncl <= year && year <= maxIncl;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    private static List<Map<String, String>> readPassports(List<String> input) {
        final LinkedList<Map<String, String>> passports = new LinkedList<>();
        passports.add(new HashMap<>());
        for (String line : input) {
            if (line.isBlank()) {
                passports.addFirst(new HashMap<>());
            }
            else {
                final String[] lineParts = line.split("\\s+");
                for (String linePart : lineParts) {
                    final String[] fieldParts = linePart.split(":");
                    passports.getFirst().put(fieldParts[0], fieldParts[1]);
                }
            }
        }

        return passports;
    }
}
