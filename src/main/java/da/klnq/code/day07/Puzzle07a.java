package da.klnq.code.day07;

import java.util.HashSet;
import java.util.Set;

public class Puzzle07a {
    private static final String BAG = "shiny gold";

    public static void main(String[] args) {
        final BagRules rules = BagRules.readRules();
        final Set<String> containers = new HashSet<>();
        findContainers(BAG, rules, containers);
        System.out.println(containers.size());
    }

    private static void findContainers(String bag, BagRules rules, Set<String> containers) {
        for (String container : rules.getContainers(bag).keySet()) {
            containers.add(container);
            findContainers(container, rules, containers);
        }
    }

}
