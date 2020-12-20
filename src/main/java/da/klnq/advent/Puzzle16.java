package da.klnq.advent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple5;

public class Puzzle16 {
    private static final String RESOURCE = "/16-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        final Puzzle16 puzzle = new Puzzle16();

        System.out.println("Solution for part 1: " + puzzle.solvePart1(input));
        System.out.println("Solution for part 2: " + puzzle.solvePart2(input));
    }

    private final List<Tuple5<String, Integer, Integer, Integer, Integer>> rules;
    private final List<List<Integer>> otherTickets;
    private final List<Integer> myTicket;

    public Puzzle16() {
        this.rules = new ArrayList<>();
        this.otherTickets = new ArrayList<>();
        this.myTicket = new ArrayList<>();
    }

    public int solvePart1(List<String> input) {
        this.parseInput(input);

        return this.otherTickets.stream()
            .flatMap(ticket -> ticket.stream())
            .filter(value -> !this.matchesSomeRule(value))
            .mapToInt(value -> value)
            .sum();
    }

    public BigInteger solvePart2(List<String> input) {
        this.parseInput(input);

        final List<List<Integer>> validTickets = this.otherTickets.stream()
            .filter(value -> matchesRules(value))
            .collect(Collectors.toList());

        final Map<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>> fieldRules = initFieldRules();
        this.removeInvalidRules(validTickets, fieldRules);
        this.removeUniqueFields(fieldRules);

        return fieldRules.entrySet().stream()
            .filter(e -> e.getValue().get(0).get1().startsWith("departure"))
            .map(e -> BigInteger.valueOf(this.myTicket.get(e.getKey())))
            .reduce(BigInteger.ONE, (v1, v2) -> v1.multiply(v2));
    }

    private void removeInvalidRules(
        List<List<Integer>> tickets, 
        Map<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>> fieldRules
    ) {
        for (List<Integer> ticket : tickets) {
            for (Entry<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>> field : fieldRules.entrySet()) {
                int rule = 0;
                while (rule < field.getValue().size()) {
                    if (this.isValidValue(field.getValue().get(rule), ticket.get(field.getKey()))) {
                        rule++;
                    }
                    else {
                        field.getValue().remove(rule);
                    }
                }
            }
        }
    }

    private Map<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>> initFieldRules() {
        final Map<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>> fieldRules = new HashMap<>();
        IntStream.range(0, this.rules.size())
            .forEach(i -> fieldRules.put(i, new ArrayList<>(this.rules)));
        return fieldRules;
    }

    private void removeUniqueFields(
        Map<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>> fieldRules
    ) {
        final Map<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>> uniqueFieldRules = new HashMap<>();
        while (true) {
            if (fieldRules.isEmpty()) {
                break;
            }

            Optional<Entry<Integer, List<Tuple5<String, Integer, Integer, Integer, Integer>>>> entry = 
                fieldRules.entrySet().stream()
                    .filter(e -> e.getValue().size() == 1)
                    .findFirst();

            if (!entry.isPresent()) {
                throw new IllegalArgumentException("Cannot determine a unique solution.");
            }

            fieldRules.remove(entry.get().getKey());
            fieldRules.values().forEach(posRules -> posRules.remove(entry.get().getValue().get(0)));
            uniqueFieldRules.put(entry.get().getKey(), entry.get().getValue());
        }

        fieldRules.clear();
        uniqueFieldRules.entrySet().forEach(e -> fieldRules.put(e.getKey(), e.getValue()));
    }

    private boolean matchesRules(List<Integer> ticket) {
        return ticket.stream()
            .allMatch(value -> this.matchesSomeRule(value));
    }

    private boolean matchesSomeRule(int value) {
        return this.rules.stream()
            .anyMatch(rule -> isValidValue(rule, value));
    }

    private boolean isValidValue(Tuple5<String, Integer, Integer, Integer, Integer> rule, int value) {
        return (rule.get2() <= value && value <= rule.get3())
            || (rule.get4() <= value && value <= rule.get5());
    }

    private void parseInput(List<String> input) {
        this.myTicket.clear();
        this.otherTickets.clear();
        this.rules.clear();

        Consumer<String> parser = this::parseRule;
        for (String line : input) {
            if ("your ticket:".equals(line)) {
                parser = this::parseMyTicket;
            }
            else if ("nearby tickets:".equals(line)) {
                parser = this::parseOtherTickets;
            }
            else if (!line.isBlank()) {
                parser.accept(line);
            }
        }
    }

    private static final Pattern RULE_REGEX = Pattern.compile("^([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");
    private void parseRule(String line) {
        final Matcher matcher = RULE_REGEX.matcher(line);
        matcher.find();
        this.rules.add(
            new Tuple5<>(
                matcher.group(1), 
                Integer.parseInt(matcher.group(2)), 
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4)), 
                Integer.parseInt(matcher.group(5))
            )
        );
    }


    private void parseMyTicket(String line) {
        this.myTicket.addAll(parseTicket(line));
    }

    private void parseOtherTickets(String line) {
        this.otherTickets.add(parseTicket(line));
    }

    private List<Integer> parseTicket(String line) {
        return Arrays.stream(line.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }
}
