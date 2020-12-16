package da.klnq.code.day05;

public class Puzzle05a {
    
    public static void main(String[] args) {
        final int max = BoardingPass.readPasses().stream()
            .mapToInt(BoardingPass::getSeat)
            .max()
            .getAsInt();
        System.out.println(max);
    }
}
