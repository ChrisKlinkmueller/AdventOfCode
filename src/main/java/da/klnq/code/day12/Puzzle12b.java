package da.klnq.code.day12;

import da.klnq.util.Tuple2;

public final class Puzzle12b extends MoveInstruction {

    public static void main(String[] args) {
        final Puzzle12b puzzle = new Puzzle12b();
        puzzle.solve();
    }

    private Tuple2<Integer, Integer> wayPoint;

    private Puzzle12b() {
        this.wayPoint = new Tuple2<>(10, -1);
        this.position = new Tuple2<>(0, 0);
    } 

    @Override
    protected void moveNorth(int value) {
        this.wayPoint = this.wayPoint.modify(lon -> lon, lat -> lat - value);
    }    

    @Override
    protected void moveSouth(int value) {
        this.wayPoint = this.wayPoint.modify(lon -> lon, lat -> lat + value);
    }

    @Override
    protected void moveEast(int value) {
        this.wayPoint = this.wayPoint.modify(lon -> lon + value, lat -> lat);
    }

    @Override
    protected void moveWest(int value) {
        this.wayPoint = this.wayPoint.modify(lon -> lon - value, lat -> lat);
    }  

    @Override
    protected void rotateLeft(int value) {
        if (value == 180) {
            this.flipDirection();
        }
        else if (value == 90) {
            this.turnLeft();
        }
        else if (value == 270) {
            this.turnRight();
        }
    }

    @Override
    protected void rotateRight(int value) {
        if (value == 180) {
            this.flipDirection();
        }
        else if (value == 90) {
            this.turnRight();
        }
        else if (value == 270) {
            this.turnLeft();
        }
    }

    private void flipDirection() {
        this.wayPoint = this.wayPoint.modify(lon -> -lon, lat -> -lat);
    }

    private void turnLeft() {
        this.wayPoint = new Tuple2<>(this.wayPoint.get2(), -this.wayPoint.get1());
    }

    private void turnRight() {
        this.wayPoint = new Tuple2<>(-this.wayPoint.get2(), this.wayPoint.get1());
    }

    @Override
    protected void moveForward(int value) {
        this.position = this.position.modify(
            lon -> lon + this.wayPoint.get1() * value, 
            lat -> lat + this.wayPoint.get2() * value
        );
    }

}
