package da.klnq.code.day09;

import java.util.stream.IntStream;

public class Puzzle09b {
    private static int INDEX = 665;
    private static long NUMBER = 1930745883;

    public static void main(String[] args) {
        final Numbers numbers = Numbers.readNumbers();

        int startIndex;
        int endIndex = INDEX -1;
        long sum = 0;

        for (startIndex = endIndex; startIndex > 0; startIndex--) {
            sum += numbers.getNumber(startIndex);
            if (sum == NUMBER) {
                break;
            }

            if (NUMBER < sum) {
                sum -= numbers.getNumber(endIndex);
                endIndex--;
            }
        }

        long min = IntStream.range(startIndex, endIndex + 1)
            .mapToLong(numbers::getNumber)
            .min().getAsLong();
        long max = IntStream.range(startIndex, endIndex + 1)
            .mapToLong(numbers::getNumber)
            .max().getAsLong();

        System.out.println(min + max);
    }

}
