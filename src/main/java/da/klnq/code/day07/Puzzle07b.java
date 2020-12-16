package da.klnq.code.day07;

import java.util.Map.Entry;

public class Puzzle07b {
    private static final String BAG = "shiny gold";

    public static void main(String[] args) {
        final BagRules rules = BagRules.readRules();
        final int count = count(BAG, rules);
        System.out.println(count - 1);
    }

    private static int count(String bag, BagRules rules) {
        int count = 1;

        for (Entry<String, Integer> content : rules.getContents(bag).entrySet()) {
            count += content.getValue() * count(content.getKey(), rules);
        }

        return count;

    }
}
