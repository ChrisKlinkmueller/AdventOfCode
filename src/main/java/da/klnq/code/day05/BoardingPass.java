package da.klnq.code.day05;

import java.util.List;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;

public class BoardingPass {
    private static final String RESOURCE = "/day05/input1.txt";
    
    private final int row;
    private final int column;

    private BoardingPass(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public int getSeat() {
        return this.row * 8 + this.column;
    }

    public static List<BoardingPass> readPasses() {
        final Try<List<BoardingPass>> readResult = IOUtils.readResource(RESOURCE, BoardingPass::convert);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        return readResult.get();
    }

    private static Try<BoardingPass> convert(String text) {
        int row = converToNumber(text.substring(0, 7), 127, 'F');
        int col = converToNumber(text.substring(7, 10), 7, 'L');
        return Try.of(new BoardingPass(row, col));
    }

    private static int converToNumber(String code, int max, char lower) {
        int low = 0;
        int high = max;

        final char[] codes = code.toCharArray();
        for (int i = 0; i < codes.length - 1; i++) {
            if (codes[i] == lower) {
                high = low + (high - low) / 2;
            }
            else {
                low = high - ((high - low) / 2);
            }
        }

        return codes[codes.length - 1] == lower ? low : high;
    }

    public static void main(String[] args) {
        final BoardingPass pass = convert("FBFBBFFRLR").get();
        System.out.println(pass.getRow());
        System.out.println(pass.getColumn());
        System.out.println(pass.getSeat());
    }
}
