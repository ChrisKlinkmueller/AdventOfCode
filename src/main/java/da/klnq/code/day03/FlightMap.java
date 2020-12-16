package da.klnq.code.day03;

import java.util.List;

import da.klnq.code.util.IOUtils;
import da.klnq.code.util.Try;
import da.klnq.code.util.Tuple2;

public class FlightMap {
    private static final String RESOURCE = "/day03/input1.txt";
    private final List<String> map;
    private final int width;
    private final int height;

    private FlightMap(List<String> coordinates) {
        this.map = coordinates;
        this.height = this.map.size();
        this.width = this.height == 0 ? 0 : this.map.get(0).length();
    }

    public static FlightMap readMap() {
        final Try<List<String>> readResult = IOUtils.readResource(RESOURCE, IOUtils::parseString);
        assert !readResult.isFailure() : readResult.exception().getMessage();
        return new FlightMap(readResult.get());
    }

    public int countTrees(int moveX, int moveY) {

        Tuple2<Integer, Integer> position = new Tuple2<Integer,Integer>(0, 0);
        int trees = 0;
        while (position.getValue2() < this.height) {
            if (map.get(position.getValue2()).charAt(position.getValue1()) == '#') {
                trees++;
            }

            position = position.modify(
                x -> (x + moveX) % this.width, 
                y -> y + moveY
            );
        }

        return trees;
    }
}
