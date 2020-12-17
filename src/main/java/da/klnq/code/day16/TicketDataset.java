package da.klnq.code.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;
import da.klnq.code.util.Tuple2;

public final class TicketDataset {
    private static final String RESOURCE = "/day16/input.txt";

    private final List<TicketRule> rules;
    private final List<List<Integer>> otherTickets;
    private final List<Integer> myTicket;

    private TicketDataset() {
        this.rules = new ArrayList<>();
        this.myTicket = new ArrayList<>();
        this.otherTickets = new ArrayList<>();
    }

    public List<TicketRule> getRules() {
        return rules;
    }

    public List<Integer> getMyTicket() {
        return myTicket;
    }

    public List<List<Integer>> getOtherTickets() {
        return otherTickets;
    }

    public static TicketDataset readDataset() {
        final Try<List<String>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseString);
        assert !readResult.isFailure() : readResult.exception().getMessage();

        final TicketDataset dataset = new TicketDataset();
        BiConsumer<String, TicketDataset> parser = TicketDataset::parseRule;
        for (String line : readResult.get()) {
            if (line.equals("your ticket:")) {
                parser = TicketDataset::parseMyTicket;
            }
            else if (line.equals("nearby tickets:")) {
                parser = TicketDataset::parseOtherTickets;
            }
            else if (!line.isBlank()) {
                parser.accept(line, dataset);
            }
        }

        return dataset;
    }

    private static final Pattern RULE_REGEX = Pattern.compile("^([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");
    private static void parseRule(String line, TicketDataset dataset) {
        final Matcher matcher = RULE_REGEX.matcher(line);
        matcher.find();
        final TicketRule rule = new TicketRule(
            matcher.group(1), 
            new Tuple2<>(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))),
            new Tuple2<>(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)))
        );
        dataset.rules.add(rule);
    }


    private static void parseMyTicket(String line, TicketDataset dataset) {
        dataset.myTicket.addAll(parseTicket(line));
    }

    private static void parseOtherTickets(String line, TicketDataset dataset) {
        dataset.otherTickets.add(parseTicket(line));
    }

    private static List<Integer> parseTicket(String line) {
        return Arrays.stream(line.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        TicketDataset.readDataset();
    }

}
