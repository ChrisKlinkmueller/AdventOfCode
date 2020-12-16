package da.klnq.code.day12;

import da.klnq.code.util.Tuple2;

public class Puzzle12a extends MoveInstruction {
    
    public static void main(String[] args) {
        final Puzzle12a puzzle = new Puzzle12a();
        puzzle.solve();
    }

    private int direction;

    private Puzzle12a() {
        this.direction = 0;
        this.position = new Tuple2<>(0, 0);
    }

    @Override
    protected void moveNorth(int value) {
        this.position = this.position.modify(lon -> lon, lat -> lat - value);
    }

    @Override
    protected void moveSouth(int value) {
        this.position = this.position.modify(lon -> lon, lat -> lat + value);
    }

    @Override
    protected void moveEast(int value) {
        this.position = this.position.modify(lon -> lon + value, lat -> lat);
    }

    @Override
    protected void moveWest(int value) {
        this.position = this.position.modify(lon -> lon - value, lat -> lat);
    }

    @Override
    protected void rotateLeft(int value) {
        this.direction = (360 + this.direction - value) % 360;
    }

    @Override
    protected void rotateRight(int value) {
        this.direction = (this.direction + value) % 360;
    }
    
    @Override
    protected void moveForward(int value) {
        switch (this.direction) {
            case 0 : this.moveEast(value); return;
            case 90 : this.moveSouth(value); return;
            case 180 : this.moveWest(value); return;
            case 270 : this.moveNorth(value); return;
            default : throw new IllegalStateException("Unknown direction: " + this.direction);
        }
    }

}
