package da.klnq.code.day01;

import java.util.List;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public abstract class ExpenseLooper {
    private static final String INPUT = "/day01/input1.txt";
    
    public static void loopThroughExpenses(ExpenseLooper looper) {
        final Try<List<Integer>> readResult = IOUtils.readResource(INPUT, IOUtils::parseInteger);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        
        final List<Integer> expenses = readResult.get();
        nestedLoop(expenses, looper);
    }

    public static void nestedLoop(List<Integer> expenses, ExpenseLooper looper) {
        for (int i = 0; i < expenses.size() - 1; i++) {
            for (int j = i + 1; j < expenses.size(); j++) {
                looper.checkExpenses(expenses, i, j);
            }
        }
    }

    protected abstract void checkExpenses(List<Integer> expenses, int index1, int index2);
}
