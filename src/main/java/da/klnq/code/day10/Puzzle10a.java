package da.klnq.code.day10;

public class Puzzle10a {
    
    public static void main(String[] args) {
        final JoltageAdapters adapters = JoltageAdapters.readAdapters();

        int difference1 = 0;
        int difference3 = 1;

        for (int index = 0; index < adapters.count(); index++) {
            int difference = index == 0 
                ? adapters.get(index)
                : adapters.get(index) - adapters.get(index - 1);
            if (difference == 1) {
                difference1++;
            }
            else if (difference == 3) {
                difference3++;
            }
        }
        
        System.out.println(difference1);
        System.out.println(difference3);
        System.out.println(difference1 * difference3);
    }

}
