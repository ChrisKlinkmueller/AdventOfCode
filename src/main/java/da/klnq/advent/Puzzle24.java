package da.klnq.advent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import da.klnq.util.IOUtils;

public class Puzzle24 {
    private static final String RESOURCE = "/24-task-input.txt";

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution for part 1: " + solvePart1(input));
        System.out.println("Solution for part 2: " + solvePart2(input));
    }

    public static int solvePart1(List<String> input) {        
        return solve(input, 0);
    }    

    public static int solvePart2(List<String> input) {
        return solve(input, 100);
    }

    public static int solve(List<String> input, int days) {
        final Set<Tile> blackTiles = initBlackTiles(input);

        IntStream.range(0, days)
            .forEach(i -> flipTilesOnDay(blackTiles));
        
        return blackTiles.size();
    }

    private static Set<Tile> initBlackTiles(List<String> input) {
        final List<List<String>> allMoves = parseMoves(input);
        final Set<Tile> blackTiles = new HashSet<>();
        allMoves.forEach(moves -> flipTileOnInit(blackTiles, moves));
        return blackTiles;
    }

    private static List<List<String>> parseMoves(List<String> input) {
        return input.stream()
            .map(Puzzle24::parseMoves)
            .collect(Collectors.toList());
    }

    private static List<String> parseMoves(String line) {
        final List<String> coordinates = new ArrayList<>();
        int i = 0;
        while (i < line.length()) {
            final String move = line.charAt(i) == 's' || line.charAt(i) == 'n'
                ? line.substring(i, ++i+1)
                : line.substring(i, i+1);
            coordinates.add(move);
            i++;
        }
        return coordinates;
    }

    private static void flipTileOnInit(Set<Tile> tiles, List<String> moves) {
        Tile tile = new Tile(0, 0);
        for (String move : moves) {
            tile = tile.move(move);
        }

        if (tiles.contains(tile)) {
            tiles.remove(tile);
        }
        else {
            tiles.add(tile);
        }
    }

    private static void flipTilesOnDay(Set<Tile> blackTiles) {
        final Set<Tile> removeTiles = blackTiles
            .stream()
            .filter(tile -> {
                int n = countNeighbors(blackTiles, tile);
                return 0 == n || 2 < n;
            })
            .collect(Collectors.toSet());

        final Set<Tile> addTiles = blackTiles
            .stream()
            .flatMap(tile -> tile.getNeighbors().stream())
            .distinct()
            .filter(tile -> !blackTiles.contains(tile))
            .filter(tile -> countNeighbors(blackTiles, tile) == 2)
            .collect(Collectors.toSet());
        
        blackTiles.removeAll(removeTiles);
        blackTiles.addAll(addTiles);
    }

    private static int countNeighbors(Set<Tile> blackTiles, Tile tile) {
        Set<Tile> neighbors = tile.getNeighbors();
        neighbors.retainAll(blackTiles);
        return neighbors.size();
    }
    
    private static class Tile {
        private final int x;
        private final int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Tile move(String move) {
            switch (move) {
                case "e" : return this.moveEast();
                case "w" : return this.moveWest();
                case "se" : return this.moveSouthEast();
                case "sw" : return this.moveSouthWest();
                case "ne" : return this.moveNorthEast();
                case "nw" : return this.moveNorthWest();
                default : throw new IllegalArgumentException("Unknown move: " + move);
            }
        }

        private Tile moveEast() {
            return new Tile(this.x + 1, this.y);
        }

        private Tile moveSouthWest() {
            return  new Tile(this.x + this.moveCorrect(), this.y + 1);
        }

        private Tile moveSouthEast() {
            return new Tile(this.x + this.moveCorrect() + 1, this.y + 1);
        }

        private Tile moveWest() {
            return new Tile(this.x - 1, this.y);
        }

        private Tile moveNorthWest() {
            return new Tile(this.x + this.moveCorrect(), this.y - 1);
        }

        private Tile moveNorthEast() {
            return new Tile(this.x + this.moveCorrect() + 1, this.y - 1);
        }

        private int moveCorrect() {
            return this.y % 2 == 0 ? -1 : 0;
        }

        public Set<Tile> getNeighbors() {            
            return new HashSet<>(Set.of(
                this.moveEast(),
                this.moveWest(),
                this.moveNorthEast(),
                this.moveNorthWest(),
                this.moveSouthEast(),
                this.moveSouthWest()
            ));
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Tile)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            final Tile tile = (Tile)obj;
            return tile.x == this.x && tile.y == this.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Tile.class, this.x, this.y);
        }
    }

}
