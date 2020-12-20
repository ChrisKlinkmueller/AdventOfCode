package da.klnq.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import da.klnq.util.IOUtils;
import da.klnq.util.Tuple4;

public class Puzzle17 {
    private static final String RESOURCE = "/17-task-input.txt";
    private static final int NUM_CYCLES = 6;
    
    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);

        System.out.println("Solution for part 1: " + solvePart1(input, NUM_CYCLES));
        System.out.println("Solution for part 2: " + solvePart2(input, NUM_CYCLES));
    }

    public static int solvePart1(List<String> input, int numCycles) {
        return solve(input, numCycles, false);
    }

    public static int solvePart2(List<String> input, int numCycles) {
        return solve(input, numCycles, true);
    }
    
    public static int solve(List<String> input, int numCycles, boolean is4D) {
        List<Tuple4<Integer, Integer, Integer, Integer>> activeCubes = parseInput(input);

        for (int cycle = 0; cycle < numCycles; cycle++) {
            activeCubes = determineActiveCubes(activeCubes, is4D);
        }

        return activeCubes.size();
    }

    private static List<Tuple4<Integer, Integer, Integer, Integer>> determineActiveCubes(List<Tuple4<Integer, Integer, Integer, Integer>> activeCubes, boolean is4D) {
        final int minxyz = Integer.MAX_VALUE;
        final int maxxyz = Integer.MIN_VALUE;
        final int minw = is4D ? Integer.MAX_VALUE : 0;
        final int maxw = is4D ? Integer.MIN_VALUE : 0;
        final Tuple4<Integer, Integer, Integer, Integer> minimum = findExtremum(activeCubes, Math::min, new Tuple4<>(minxyz, minxyz, minxyz, minw));
        final Tuple4<Integer, Integer, Integer, Integer> maximum = findExtremum(activeCubes, Math::max, new Tuple4<>(maxxyz, maxxyz, maxxyz, maxw));

        List<Tuple4<Integer, Integer, Integer, Integer>> newActiveCubes = new ArrayList<>();
        
        for (int x = minimum.get1() - 1; x <= maximum.get1() + 1; x++) {
            for (int y = minimum.get2() - 1; y <= maximum.get2() + 1; y++) {
                for (int z = minimum.get3() - 1; z <= maximum.get3() + 1; z++) {
                    final int startW = is4D ? minimum.get4() - 1 : 0;
                    final int endW = is4D ? maximum.get4() + 1 : 0;
                    for (int w = startW; w <= endW; w++) {
                        final Tuple4<Integer, Integer, Integer, Integer> cube = new Tuple4<>(x,y,z,w);
                        final boolean state = isActive(activeCubes, cube);
                        final int numActiveNeighbors = countActiveNeighbors(activeCubes, cube, is4D);
                        if (state && 2 <= numActiveNeighbors && numActiveNeighbors <= 3) {
                            newActiveCubes.add(cube);
                        }
                        if (!state && numActiveNeighbors == 3) {
                            newActiveCubes.add(cube);
                        }
                    }                    
                }
            }
        }

        return newActiveCubes;
    }

    private static int countActiveNeighbors(
        List<Tuple4<Integer, Integer, Integer, Integer>> activeCubes,
        Tuple4<Integer, Integer, Integer, Integer> cube,
        boolean is4D
    ) {
        int count = 0;
        for (int x = cube.get1() - 1; x <= cube.get1() + 1; x++) {
            for (int y = cube.get2() - 1; y <= cube.get2() + 1; y++) {
                for (int z = cube.get3() - 1; z <= cube.get3() + 1; z++) {
                    final int startW = is4D ? cube.get4() - 1 : 0;
                    final int endW = is4D ? cube.get4() + 1 : 0;
                    for (int w = startW; w <= endW; w++) {
                        final Tuple4<Integer, Integer, Integer, Integer> neighbor = new Tuple4<>(x, y, z, w);
                        if (!areEqual(neighbor, cube) && isActive(activeCubes, neighbor)) {
                            count++;
                        }
                    }

                    
                }
            }
        }
        return count;
    }

    private static boolean isActive(
        List<Tuple4<Integer, Integer, Integer, Integer>> activeCubes,
        Tuple4<Integer, Integer, Integer, Integer> cube
    ) {
        return activeCubes.stream()
            .anyMatch(c -> areEqual(c, cube));
    }

    private static boolean areEqual(
        Tuple4<Integer, Integer, Integer, Integer> cube1,
        Tuple4<Integer, Integer, Integer, Integer> cube2
    ) {
        return cube1.get1() == cube2.get1() 
            && cube1.get2() == cube2.get2() 
            && cube1.get3() == cube2.get3() 
            && cube1.get4() == cube2.get4();
    }

    private static Tuple4<Integer, Integer, Integer, Integer> findExtremum(
        List<Tuple4<Integer, Integer, Integer, Integer>> activeCubes, 
        BiFunction<Integer, Integer, Integer> comparison,
        Tuple4<Integer, Integer, Integer, Integer> initValue
    ) {
        Tuple4<Integer, Integer, Integer, Integer> extremum = initValue;
        for (Tuple4<Integer, Integer, Integer, Integer> cube : activeCubes) {
            if (extremum.get1() != comparison.apply(extremum.get1(), cube.get1())) {
                extremum = new Tuple4<>(cube.get1(), extremum.get2(), extremum.get3(), extremum.get4());
            }
            if (extremum.get2() != comparison.apply(extremum.get2(), cube.get2())) {
                extremum = new Tuple4<>(extremum.get1(), cube.get2(), extremum.get3(), extremum.get4());
            }
            if (extremum.get3() != comparison.apply(extremum.get3(), cube.get3())) {
                extremum = new Tuple4<>(extremum.get1(), extremum.get2(), cube.get3(), extremum.get4());
            }
            if (extremum.get4() != comparison.apply(extremum.get4(), cube.get4())) {
                extremum = new Tuple4<>(extremum.get1(), extremum.get2(), extremum.get3(), cube.get4());
            }
        }
        return extremum;
    }

    private static List<Tuple4<Integer, Integer, Integer, Integer>> parseInput(List<String> input) {
        final List<Tuple4<Integer, Integer, Integer, Integer>> activeCubes = new ArrayList<>();

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                if (input.get(y).charAt(x) == '#') {
                    activeCubes.add(new Tuple4<>(x, y, 0, 0));
                }
            }
        }

        return activeCubes;
    }
}
