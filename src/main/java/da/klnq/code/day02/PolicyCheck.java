package da.klnq.code.day02;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public class PolicyCheck {
    private final int intValue1;
    private final int intValue2;
    private final char character;
    private final String password;

    public PolicyCheck(int intValue1, int intValue2, char character, String password) {
        this.intValue1 = intValue1;
        this.intValue2 = intValue2;
        this.character = character;
        this.password = password;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getIntValue1() {
        return this.intValue1;
    }

    public int getIntValue2() {
        return this.intValue2;
    }

    public String getPassword() {
        return this.password;
    }

    private static final String RESOURCE = "/day02/input1.txt";
    public static Try<List<PolicyCheck>> readPolicyChecks() {
        return IOUtils.readResource(RESOURCE, PolicyCheck::parse);
    }

    private static final Pattern PATTERN = Pattern.compile("^([0-9]+)-([0-9]+)\\s+([a-z]):\\s+([a-z]+)");
    public static Try<PolicyCheck> parse(String text) {
        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.matches()) {
            return Try.failure("Text '%s' doesn't have a valid format.", text);
        }

        final int min = Integer.parseInt(matcher.group(1));
        final int max = Integer.parseInt(matcher.group(2));
        final char character = matcher.group(3).charAt(0);
        final String password = matcher.group(4);
        return Try.of(new PolicyCheck(min, max, character, password));
    }
}
