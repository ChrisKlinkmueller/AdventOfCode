package da.klnq.code.day01;

import java.util.List;

public class Puzzle01a extends ExpenseLooper {
    private static final int EXPENSE_SUM = 2020;
    
    public static void main(String[] args) {
        ExpenseLooper.loopThroughExpenses(new Puzzle01a());
    }

    @Override
    protected void checkExpenses(List<Integer> expenses, int index1, int index2) {
        final int expense1 = expenses.get(index1);
        final int expense2 = expenses.get(index2);
        if (expense1 + expense2 == EXPENSE_SUM) {
            System.out.println(String.format("%s * %s = %s", expense1, expense2, expense1 * expense2));
        }
    }    

}
