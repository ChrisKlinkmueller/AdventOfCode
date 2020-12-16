package da.klnq.code.day02;

import java.util.List;

import da.klnq.code.util.Try;

public class Puzzle02b {
    public static void main(String[] args) {
        final Try<List<PolicyCheck>> readResult = PolicyCheck.readPolicyChecks();
        assert !readResult.isFailure();

        final List<PolicyCheck> checks = readResult.get();
        final long invalidPasswords = checks.stream()
            .filter(Puzzle02b::isValid)
            .count();
        System.out.println(invalidPasswords);
    }

    private static boolean isValid(PolicyCheck check) {
        return hasCharacterAtIndex(check.getPassword(), check.getIntValue1(), check.getCharacter())
             ^ hasCharacterAtIndex(check.getPassword(), check.getIntValue2(), check.getCharacter());
    }

    private static boolean hasCharacterAtIndex(String text, int index, char c) {
        return index <= text.length() && text.charAt(index - 1) == c;
    }
}
