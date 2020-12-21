package da.klnq.advent;

import java.math.BigInteger;
import java.util.List;
import java.util.Stack;

import da.klnq.util.IOUtils;

public class Puzzle18 {
    private static final String RESOURCE = "/18-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static BigInteger solvePart1(List<String> input) {
        return input.stream()
            .map(Puzzle18::evaluatePart1)
            .reduce(BigInteger.ZERO, (v1, v2) -> v1.add(v2));
    }

    public static BigInteger solvePart2(List<String> input) {
        return input.stream()
            .map(Puzzle18::evaluatePart2)
            .reduce(BigInteger.ZERO, (v1, v2) -> v1.add(v2));
    }

    private static BigInteger evaluatePart1(String expression) {
        final Stack<Object> parts = decomposeExpression(expression.replaceAll("\\s+", ""));
        return evaluatePart1(parts);
    }

    private static BigInteger evaluatePart1(Stack<Object> parts) {
        BigInteger result = BigInteger.ZERO;
        boolean add = true;
        while (!parts.isEmpty()) {
            final Object part = parts.pop();
            if (part instanceof Character) {
                add = ((char)part) == '+';
            }
            else {
                BigInteger operand = part instanceof String ? evaluatePart1((String)part) : (BigInteger)part;
                result = add ? result.add(operand) : result.multiply(operand);
            }
        }

        return result;
    }

    private static BigInteger evaluatePart2(String expression) {
        final Stack<Object> parts = decomposeExpression(expression);
        return evaluatePart2(parts);
    }

    private static BigInteger evaluatePart2(Stack<Object> parts1) {
        final Stack<Object> parts2 = new Stack<>();  
        
        while (!parts1.isEmpty()) {
            final Object part = parts1.pop();
            if (part instanceof Character) {
                if ((char)part == '+') {
                    final BigInteger op1 = parts1.peek() instanceof String
                        ? evaluatePart2((String)parts1.pop())
                        : (BigInteger)parts1.pop();
                    final BigInteger op2 = (BigInteger)parts2.pop();
                    parts2.push(op1.add(op2));
                }
            }
            else {
                final BigInteger op = part instanceof String
                    ? evaluatePart2((String)part)
                    : (BigInteger)part;
                parts2.push(op);
            }
        }

        return parts2.stream()
            .map(obj -> (BigInteger)obj)
            .reduce(BigInteger.ONE, (v1, v2) -> v1.multiply(v2));
    }

    private static final Stack<Object> decomposeExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");
        final Stack<Object> parts = new Stack<>();
        int i = expression.length() - 1;
        while (i >= 0) {
            if ('0' <= expression.charAt(i) && expression.charAt(i) <= '9') {
                parts.push(BigInteger.valueOf(expression.charAt(i) - '0'));
                i--; 
            }
            else if (expression.charAt(i) == '+' || expression.charAt(i) == '*') {
                parts.push(expression.charAt(i));
                i--;
            }
            else if (expression.charAt(i) == ')') {
                int start = i;
                int brackets = 0;
                do  {
                    if (expression.charAt(i) == ')') {
                        brackets++;
                    }
                    else if (expression.charAt(i) == '(') {
                        brackets--;
                    }
                    i--;
                } while (0 <= i && brackets != 0);
                parts.push(expression.substring(i + 2, start));
            }
        } 
        return parts;
    }
    
}
