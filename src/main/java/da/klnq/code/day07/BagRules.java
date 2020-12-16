package da.klnq.code.day07;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public class BagRules {
    private static final String RESOURCE = "/day07/input1.txt";
    private final Map<String, Map<String, Integer>> contents;
    private final Map<String, Map<String, Integer>> containers;

    public BagRules() {
        this.contents = new HashMap<>();
        this.containers = new HashMap<>();
    }

    private void addContent(String containerBag, String containedBag, int amount) {
        this.contents.putIfAbsent(containerBag, new HashMap<>());
        this.contents.get(containerBag).put(containedBag, amount);

        this.containers.putIfAbsent(containedBag, new HashMap<>());
        this.containers.get(containedBag).put(containerBag, amount);
    }

    public Map<String, Integer> getContainers(String bag) {
        return this.containers.getOrDefault(bag, Collections.emptyMap());
    }

    public Map<String, Integer> getContents(String bag) {
        return this.contents.getOrDefault(bag, Collections.emptyMap());
    }

    private Try<Void> parseBagRule(String text) {
        if (text.contains("contain no other")) {
            return Try.SUCCESS;
        }

        final String[] parts = text.split("\\s+");
        final String containerBag = getBagName(parts, 0, 1);    
        
        for (int i = 4; i + 2 < parts.length; i += 4) {
            final String containedBag = getBagName(parts, i + 1, i + 2);
            final int amount = Integer.parseInt(parts[i]);
            this.addContent(containerBag, containedBag, amount);
        }

        return Try.SUCCESS;
    }

    private String getBagName(String[] parts, int index1, int index2) {
        return String.format("%s %s", parts[index1], parts[index2]);
    }

    public static BagRules readRules() {
        final BagRules rules = new BagRules();
        final Try<List<Void>> readResult = IOUtils.readResource(RESOURCE, rules::parseBagRule);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        return rules;
    }
}
