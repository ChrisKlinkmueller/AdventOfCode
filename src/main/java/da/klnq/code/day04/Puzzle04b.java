package da.klnq.code.day04;

import java.util.List;

public class Puzzle04b {
    
    public static void main(String[] args) {
        final List<Passport> passports = Passport.readPassports();
        final Fields fields = new Fields();

        final long invalidPassports = passports.stream()
            .filter(passport -> fields.areFieldsPresent(passport) && fields.areFieldsValid(passport))
            .count();
        System.out.println(invalidPassports);    
    }

}