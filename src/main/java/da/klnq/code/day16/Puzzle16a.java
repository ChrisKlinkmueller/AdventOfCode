package da.klnq.code.day16;

public class Puzzle16a {
    
    public static void main(String[] args) {
        final TicketDataset dataset = TicketDataset.readDataset();

        final int sum = dataset.getOtherTickets().stream()
            .flatMap(ticket -> ticket.stream())
            .filter(value -> !matchesSomeRule(value, dataset))
            .mapToInt(value -> value)
            .sum();
        
        System.out.println(sum);
    }

    private static boolean matchesSomeRule(int value, TicketDataset dataset) {
        return dataset.getRules().stream()
            .anyMatch(rule -> rule.isValidValue(value));
    }
}
