package da.klnq.advent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import da.klnq.util.IOUtils;

public class Puzzle20 {
    private static final String RESOURCE = "/20-task-input.txt";
    private static final int DIMENSION = 10;

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution part 1: " + solvePart1(input));
    }
    
    public static BigInteger solvePart1(List<String> input) {
        final List<Image> images = parseImages(input);        
        connectImages(images);        
        return images.stream()
            .filter(image -> image.neighborCount() == 2)
            .map(image -> image.id)
            .reduce(BigInteger.ONE, (v1, v2) -> v1.multiply(v2));
    }
    
    public static int solvePart2(List<String> input) {
        final List<Image> images = parseImages(input);        
        connectImages(images);        
        return 0;
    }

    private static void connectImages(List<Image> images) {
        final List<Image> unconnectedImages = new ArrayList<>(images);

        while (!unconnectedImages.isEmpty()) {
            final Image image1 = findImageToConnect(unconnectedImages);
            for (Image image2 : unconnectedImages) {
                if (image1.neighborCount() < 4) {
                    connectImagesIfPossible(image1, image2);
                }
            }
        }        
    }

    private static void connectImagesIfPossible(Image image1, Image image2) {
        if (tryCombinations(image1, image2) || image2.isOriented()) {
            return;
        }

        for (int index = 0; index < 7; index++) {
            image2.flip();
            if (index % 2 == 1) {
                image2.rotate();
            }

            if (tryCombinations(image1, image2)) {
                return;
            }
        }
    }

    private static boolean tryCombinations(Image image1, Image image2) {
        if (image1.top == null && image2.bottom == null && areEqual(image1::getTop, image2::getBottom)) {
            image1.top = image2;
            image2.bottom = image1;
        }
        else if (image1.bottom == null && image2.top == null && areEqual(image1::getBottom, image2::getTop)) {
            image1.bottom = image2;
            image2.top = image1;
        }
        else if (image1.left == null && image2.right == null && areEqual(image1::getLeft, image2::getRight)) {
            image1.left = image2;
            image2.right = image1;
        }
        else if (image1.right == null && image2.left == null && areEqual(image1::getRight, image2::getLeft)) {
            image1.right = image2;
            image2.left = image1;
        }
        else {
            return false;
        }
        return true;
    }

    private static boolean areEqual(
        Function<Integer, Character> accessor1,
        Function<Integer, Character> accessor2
    ) {
        return IntStream.range(0, DIMENSION)
            .allMatch(i -> accessor1.apply(i).equals(accessor2.apply(i)));
    }

    private static Image findImageToConnect(List<Image> unconnectedImages) {
        for (int i = 0; i < unconnectedImages.size(); i++) {
            if (unconnectedImages.get(i).neighborCount() != 0) {
                return unconnectedImages.remove(i);
            }
        }
        return unconnectedImages.remove(0);
    }

    private static List<Image> parseImages(List<String> input) {
        final List<Image> images = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 12) {
            final Image image = new Image();
            image.id = new BigInteger(input.get(i).replaceAll("[Tile :]", ""));
            image.tiles = IntStream.range(i + 1, i + 11)
                .mapToObj(r -> input.get(r).toCharArray())
                .toArray(char[][]::new);
            images.add(image);
        }
        return images;
    }

    private static class Image {
        private char[][] tiles;
        private BigInteger id;
        private Image right;
        private Image left;
        private Image top;
        private Image bottom;
        
        private boolean isOriented() {
            return this.neighborCount() != 0;
        }

        private int neighborCount() {
            int count = this.right == null ? 0 : 1;
            count += this.bottom == null ? 0 : 1;
            count += this.left == null ? 0 : 1;
            return count + (this.top == null ? 0 : 1);
        }

        public void rotate() {
            final char[][] newTiles = new char[this.tiles.length][this.tiles.length];
            for (int row = 0; row < this.tiles.length; row++) {
                for (int col = 0; col < this.tiles.length; col++) {
                    newTiles[row][col] = this.tiles[col][this.tiles.length - row - 1];
                }
            }
            this.tiles = newTiles;
        }

        public void flip() {
            final char[][] newTiles = new char[this.tiles.length][this.tiles.length];
            for (int row = 0; row < this.tiles.length; row++) {
                for (int col = 0; col < this.tiles.length; col++) {
                    newTiles[row][col] = this.tiles[row][this.tiles.length - 1 - col];
                }
            }
            this.tiles = newTiles;
        }

        private char getTop(int index) {
            return this.tiles[0][index];
        }

        private char getBottom(int index) {
            return this.tiles[this.tiles.length - 1][index];
        }

        private char getLeft(int index) {
            return this.tiles[index][0];
        }

        private char getRight(int index) {
            return this.tiles[index][this.tiles.length - 1];
        }
    }

}
