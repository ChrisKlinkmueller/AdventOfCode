package da.klnq.code.day06;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import da.klnq.util.IOUtils;

public class Group {
    private static final String RESOURCE = "/day06/input1.txt";
    private final Map<Integer, String> peopleAnswers;

    private Group() {
        this.peopleAnswers = new HashMap<>();
    }

    public Collection<String> getAnswers() {
        return this.peopleAnswers.values();
    }

    public int peopleCount() {
        return this.peopleAnswers.size();
    }

    private void addPerson(String answers) {
        this.peopleAnswers.put(this.peopleAnswers.size(), answers);
    }

    public static List<Group> readGroups() {    
        final LinkedList<Group> groups = new LinkedList<>();
        groups.add(new Group());
        for (String answers : IOUtils.readResource(RESOURCE)) {
            if (answers.isBlank()) {
                groups.addFirst(new Group());
            }
            else {
                groups.getFirst().addPerson(answers);
            }
        }

        return groups;
    }

    
}
