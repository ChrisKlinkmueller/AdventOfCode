package da.klnq.code.day03;

public class Puzzle03a {
    private static final int MOVE_X = 3;
    private static final int MOVE_Y = 1;

    public static void main(String[] args) { 
        final FlightMap map = FlightMap.readMap();
        System.out.println(map.countTrees(MOVE_X, MOVE_Y));
    }

    
}
