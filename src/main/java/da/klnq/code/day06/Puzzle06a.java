package da.klnq.code.day06;

public class Puzzle06a {
    
    public static void main(String[] args) {
        final long count = Group.readGroups()
            .stream()
            .mapToLong(group -> countAnswers(group))
            .sum();
        System.out.println(count);
    }

    public static long countAnswers(Group group) {
        return group.getAnswers()
            .stream()
            .flatMapToInt(String::chars)
            .distinct()
            .count();
    }
}
