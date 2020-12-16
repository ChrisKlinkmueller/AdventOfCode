package da.klnq.code.day04;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import da.klnq.code.util.Tuple2;

public class Fields {
    private final Map<String, Predicate<String>> fieldRules;
    private final Set<String> optionalFields;

    public Fields() {
        this.optionalFields = Set.of("cid");

        this.fieldRules = Map.of(
            "byr", Fields::isValidBirthYear,
            "iyr", Fields::isValidIssueYear,
            "eyr", Fields::isValidExpirationYear,
            "hgt", Fields::isValidHeight,
            "hcl", Fields::isValidHairColor,
            "ecl", Fields::isValidEyeColor,
            "pid", Fields::isValidPassportID,
            "cid", Fields::isValidCountryID
        );        
    }

    public boolean areFieldsPresent(Passport passport) {
        final Set<String> missingFields = this.fieldRules.keySet().stream()
            .filter(field -> !passport.containsField(field))
            .collect(Collectors.toSet());
        missingFields.removeAll(this.optionalFields);
        return missingFields.isEmpty();
    }

    public boolean areFieldsValid(Passport passport) {
        return passport.fieldStream()
            .allMatch(this::fieldMatches);
    }

    private boolean fieldMatches(Tuple2<String, String> field) {
        return this.fieldRules.getOrDefault(field.getValue1(), t -> false)
            .test(field.getValue2());
    }

    private static boolean isValidBirthYear(String text) {
        return isValidNumber(text, 1920, 2002);
    }

    private static boolean isValidIssueYear(String text) {
        return isValidNumber(text, 2010, 2020);
    }

    private static boolean isValidExpirationYear(String text) {
        return isValidNumber(text, 2020, 2030);
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

    private static boolean isValidCountryID(String text) {
        return true;
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


}
