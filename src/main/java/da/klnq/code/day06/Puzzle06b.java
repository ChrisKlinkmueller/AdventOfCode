package da.klnq.code.day06;

import java.util.HashMap;
import java.util.Map;

public class Puzzle06b {

    public static void main(String[] args) {
        final long count = Group.readGroups()
            .stream()
            .mapToLong(group -> countAnswers(group))
            .sum();
        System.out.println(count);
    }

    public static long countAnswers(Group group) {
        Map<Integer, Integer> answers = new HashMap<>();
        group.getAnswers().stream()
            .flatMapToInt(String::chars)
            .forEach(i -> answers.compute(i, (key, val) -> val == null ? 1 : val + 1));
        return answers.values().stream()
            .filter(val -> val == group.peopleCount())
            .count();
    }
}
