package da.klnq.code.day01;

import java.util.List;

public class Puzzle01b extends ExpenseLooper {
    private static final int EXPENSE_SUM = 2020;

    public static void main(String[] args) {
        ExpenseLooper.loopThroughExpenses(new Puzzle01b());
    }
    
    @Override
    protected void checkExpenses(List<Integer> expenses, int index1, int index2) {
        final int expense1 = expenses.get(index1);
        final int expense2 = expenses.get(index2);

        for (int index3 = index2 + 1; index3 < expenses.size(); index3++) {
            final int expense3 = expenses.get(index3);
            if (expense1 + expense2 + expense3 == EXPENSE_SUM) {
                System.out.println(String.format(
                    "%s * %s * %s = %s", 
                    expense1, 
                    expense2, 
                    expense3, 
                    expense1 * expense2 * expense3
                ));
            }
        }
    }
    
}
