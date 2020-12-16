package da.klnq.code.day09;

public class Puzzle09a {
    
    public static void main(String[] args) {
        final Numbers numbers = Numbers.readNumbers();
        
        int index = numbers.getPreambleLength();
        while (index < numbers.count()) {
            if (!isValid(numbers, index)) {
                System.out.println(String.format("Error at index %s: number %s invalid", index, numbers.getNumber(index)));
                return;
            }

            index++;
        }        
    }

    private static boolean isValid(Numbers numbers, int index) {
        for (int i = index - numbers.getPreambleLength(); i < index - 1; i++) {
            for (int j = i + 1; j < index; j++) {
                if (numbers.getNumber(i) + numbers.getNumber(j) == numbers.getNumber(index)) {
                    return true;
                }
            }
        }

        return false;
    }
}
