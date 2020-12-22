package da.klnq.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple2;

public class Puzzle21 {
    private final static String RESOURCE = "/21-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static long solvePart1(List<String> input) {
        final List<Tuple2<Set<String>, Set<String>>> foods = parseFoods(input);
        final List<Tuple2<String, String>> rules = extractRules(foods);
        final Map<String, List<String>> filteredRules = filterRules(foods, rules);
        return foods.stream()
            .flatMap(food -> food.get1().stream())
            .distinct()
            .filter(ing -> !filteredRules.containsKey(ing))
            .mapToLong(ing -> foods.stream().filter(food -> food.get1().contains(ing)).count())
            .sum();
    }

    public static String solvePart2(List<String> input) {
        final List<Tuple2<Set<String>, Set<String>>> foods = parseFoods(input);
        final List<Tuple2<String, String>> rules = extractRules(foods);
        final Map<String, List<String>> filteredRules = filterRules(foods, rules);        
        final List<Tuple2<String, String>> selectedRules = selectRules(filteredRules);
        return selectedRules.stream()
            .sorted((r1, r2) -> r1.get2().compareTo(r2.get2()))
            .map(Tuple2::get1)
            .collect(Collectors.joining(","));
    }

    private static List<Tuple2<String, String>> selectRules(Map<String, List<String>> rules) {
        final List<Tuple2<String, String>> selectedRules = new ArrayList<>();

        while (!rules.isEmpty()) {
            final Optional<Entry<String, List<String>>> rule = rules.entrySet()
                .stream()
                .filter(e -> e.getValue().size() == 1)
                .findAny();

            if (!rule.isPresent()) {
                break;
            }

            final String ing = rule.get().getKey();
            final String all = rule.get().getValue().get(0);
            selectedRules.add(new Tuple2<>(ing, all));
            rules.remove(ing);
            rules.entrySet().forEach(e -> e.getValue().remove(all));
        }

        return selectedRules;
    }

    private static Map<String, List<String>> filterRules(List<Tuple2<Set<String>, Set<String>>> foods, List<Tuple2<String, String>> rules) {
        int i = 0;
        while (i < rules.size()) {
            if (appliesToAllFoods(foods, rules.get(i))) {
                i++;
            }
            else {
                rules.remove(i);
            }
        }

        return rules.stream()
            .collect(
                HashMap::new, 
                (m, r) -> {
                    m.putIfAbsent(r.get1(), new ArrayList<>());
                    m.get(r.get1()).add(r.get2());
                },
                HashMap::putAll
            );
    }

    private static boolean appliesToAllFoods(
        List<Tuple2<Set<String>, Set<String>>> foods,
        Tuple2<String, String> rule
    ) {
        return foods.stream()
            .allMatch(food -> {
                if (food.get2().contains(rule.get2())) {
                    return food.get1().contains(rule.get1());
                }
                return true;
            });
    }

    public static List<Tuple2<String, String>> extractRules(List<Tuple2<Set<String>, Set<String>>> foods) {
        final List<Tuple2<String, String>> rules = new LinkedList<>();
        foods.stream()
            .flatMap(food -> food.get1().stream())
            .flatMap(ing -> foods.stream().flatMap(food -> food.get2().stream()).map(all -> new Tuple2<>(ing, all)))
            .filter(rule -> !containsRule(rules, rule))
            .forEach(rules::add);
        return rules;
    }

    public static boolean containsRule(List<Tuple2<String, String>> rules, Tuple2<String, String> rule) {
        return rules.stream()
            .anyMatch(r -> r.get1().equals(rule.get1()) && r.get2().equals(rule.get2()));
    }

    public static List<Tuple2<Set<String>, Set<String>>> parseFoods(List<String> input) {
        return input.stream()
            .map(Puzzle21::parseFood)
            .collect(Collectors.toList());
    }

    private static Tuple2<Set<String>, Set<String>> parseFood(String input) {
        final String[] parts = input.replaceAll("[\\(\\)]", "").split(" contains ");
        return new Tuple2<>(
            Arrays.stream(parts[0].split(" ")).collect(Collectors.toSet()), // ingredient
            Arrays.stream(parts[1].split(", ")).collect(Collectors.toSet())  // allergen
        );
    }

}
