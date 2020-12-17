package da.klnq.code.day12;

import java.util.List;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;
import da.klnq.code.util.Tuple2;

public abstract class MoveInstruction {
    private static final String RESOURCE = "/day12/input1.txt";

    protected Tuple2<Integer, Integer> position;
    
    public void solve() {
        final Try<List<String>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseString);
        assert !readResult.isFailure() : readResult.exception().getMessage();

        for (String line : readResult.get()) {
            this.execute(line);
        }

        System.out.println(Math.abs(this.position.getValue1()) + Math.abs(this.position.getValue2()));
    }
    
    private void execute(String text) {
        final int value = Integer.parseInt(text.substring(1));
        final char inst = text.charAt(0);
        switch (inst) {
            case 'N' : this.moveNorth(value); break;
            case 'S' : this.moveSouth(value); break;
            case 'E' : this.moveEast(value); break;
            case 'W' : this.moveWest(value); break;
            case 'L' : this.rotateLeft(value); break;
            case 'R' : this.rotateRight(value); break;
            case 'F' : this.moveForward(value); break;
            default : throw new IllegalArgumentException("Unsupported instruction type: " + inst);
        }
    }    

    protected abstract void rotateLeft(int value);
    protected abstract void rotateRight(int value);
    protected abstract void moveNorth(int value);
    protected abstract void moveSouth(int value);
    protected abstract void moveEast(int value);
    protected abstract void moveWest(int value);
    protected abstract void moveForward(int value);

}