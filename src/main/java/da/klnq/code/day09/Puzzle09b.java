package da.klnq.code.day09;

public class Puzzle09b {
    private static int INDEX = 665;

    public static void main(String[] args) {
        final Numbers numbers = Numbers.readNumbers();

        int startIndex = INDEX - 1;
        int endIndex = INDEX - 1;
        long sum = 0;

        while (0 < startIndex && sum != numbers.getNumber(INDEX)) {
            sum += numbers.getNumber(startIndex);

            if (numbers.getNumber(INDEX) < sum) {
                sum -= numbers.getNumber(endIndex);
                endIndex--;
            }

            startIndex--;
        }

        startIndex++;
        endIndex++;
        long min = numbers.stream(startIndex, endIndex)
            .min().getAsLong();
        long max = numbers.stream(startIndex, endIndex)
            .max().getAsLong();

        System.out.println(min + max);
    }

}
