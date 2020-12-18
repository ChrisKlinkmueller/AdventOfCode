package da.klnq.advent;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import da.klnq.util.IOUtils;

public final class Puzzle07 {
    private static final String RESOURCE = "/07-task-input.txt";
    private static final String BAG = "shiny gold";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        System.out.println("Solution for part 1: " + solvePart1(input, BAG));
        System.out.println("Solution for part 2: " + solvePart2(input, BAG));
    }

    private final Map<String, Map<String, Integer>> contents;
    private final Map<String, Map<String, Integer>> containers;

    private Puzzle07(List<String> input) {
        this.containers = new HashMap<>();
        this.contents = new HashMap<>();
        input.forEach(this::parseBagRule);
    }
    
    public static int solvePart1(List<String> input, String bag) {
        final Set<String> ancestors = new HashSet<>();
        new Puzzle07(input).findContainers(bag, ancestors);
        return ancestors.size();
    }

    public static int solvePart2(List<String> input, String bag) {
        return new Puzzle07(input).count(bag) - 1;
    }

    public int count(String bag) {
        int count = 1;

        for (Entry<String, Integer> content : this.contents.getOrDefault(bag, Collections.emptyMap()).entrySet()) {
            count += content.getValue() * count(content.getKey());
        }

        return count;

    }

    private void findContainers(String bag, Set<String> ancestors) {
        for (String container : this.containers.getOrDefault(bag, Collections.emptyMap()).keySet()) {
            ancestors.add(container);
            findContainers(container, ancestors);
        }
    }

    private void parseBagRule(String line) {
        if (line.contains("contain no other")) {
            return;
        }

        final String[] parts = line.split("\\s+");
        final String containerBag = this.getBagName(parts, 0, 1);    
        
        for (int i = 4; i + 2 < parts.length; i += 4) {
            final String containedBag = getBagName(parts, i + 1, i + 2);
            final int amount = Integer.parseInt(parts[i]);
            this.addContent(containerBag, containedBag, amount);
        }
    }

    private String getBagName(String[] parts, int index1, int index2) {
        return String.format("%s %s", parts[index1], parts[index2]);
    }

    private void addContent(String containerBag, String containedBag, int amount) {
        this.contents.putIfAbsent(containerBag, new HashMap<>());
        this.contents.get(containerBag).put(containedBag, amount);

        this.containers.putIfAbsent(containedBag, new HashMap<>());
        this.containers.get(containedBag).put(containerBag, amount);
    }

}
