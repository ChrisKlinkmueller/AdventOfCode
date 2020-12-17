package da.klnq.code.day16;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle16b {
    
    public static void main(String[] args) {
        final TicketDataset dataset = TicketDataset.readDataset();

        final List<List<Integer>> validTickets = dataset.getOtherTickets().stream()
            .filter(value -> matchesRules(value, dataset))
            .collect(Collectors.toList());

        final Map<Integer, List<TicketRule>> rules = new HashMap<>();
        IntStream.range(0, dataset.getRules().size())
            .forEach(i -> rules.put(i, new ArrayList<>(dataset.getRules())));

        for (List<Integer> ticket : validTickets) {
            for (int i = 0; i < rules.size(); i++) {
                final List<TicketRule> posRule = rules.get(i);
                int r = 0;
                while (r < posRule.size()) {
                    if (posRule.get(r).isValidValue(ticket.get(i))) {
                        r++;
                    }
                    else {
                        posRule.remove(r);
                    }
                }
            }
        }

        BigInteger values = BigInteger.ONE;
        while (true) {
            if (rules.isEmpty()) {
                break;
            }

            Optional<Entry<Integer, List<TicketRule>>> entry = rules.entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .findFirst();

            if (!entry.isPresent()) {
                return;
            }

            final TicketRule rule = entry.get().getValue().get(0);
            final int pos = entry.get().getKey();
            System.out.println(pos + ": " + rule.getName());

            rules.remove(pos);
            rules.values().forEach(posRules -> posRules.remove(rule));

            if (rule.getName().startsWith("departure")) {
                values = values.multiply(BigInteger.valueOf(dataset.getMyTicket().get(pos)));
            }
        }

        System.out.println(values);
    }

    private static boolean matchesRules(List<Integer> ticket, TicketDataset dataset) {
        return ticket.stream()
            .allMatch(value -> matchesSomeRule(value, dataset));
    }

    private static boolean matchesSomeRule(int value, TicketDataset dataset) {
        return dataset.getRules().stream()
            .anyMatch(rule -> rule.isValidValue(value));
    }
}
