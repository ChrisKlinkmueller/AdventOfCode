package da.klnq.code.day02;

import java.util.List;

import da.klnq.code.util.Try;

public class Puzzle02a {
    
    public static void main(String[] args) {
        final Try<List<PolicyCheck>> readResult = PolicyCheck.readPolicyChecks();
        assert !readResult.isFailure();

        final List<PolicyCheck> checks = readResult.get();
        final long invalidPasswords = checks.stream()
            .filter(Puzzle02a::isValid)
            .count();
        System.out.println(invalidPasswords);
    }

    private static boolean isValid(PolicyCheck check) {
        int occ = 0;
        
        for (int i = 0; i < check.getPassword().length(); i++) {
            if (check.getCharacter() == check.getPassword().charAt(i)) {
                occ++;
            }
        }

        return check.getIntValue1() <= occ && occ <= check.getIntValue2();
    }

}
