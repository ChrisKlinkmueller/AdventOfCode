package da.klnq.advent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import da.klnq.util.IOUtils;

public class Puzzle20 {
    private static final String RESOURCE = "/20-task-input.txt";
    private static final int DIMENSION = 10;

    public static void main(String[] args) {
        final List<String> input = IOUtils.readResource(RESOURCE);
        System.out.println("Solution part 1: " + solvePart1(input));
        System.out.println("Solution part 2: " + solvePart2(input));
    }
    
    public static BigInteger solvePart1(List<String> input) {
        final List<Image> images = parseImages(input);        
        connectImages(images);        
        return images.stream()
            .filter(image -> image.neighborCount() == 2)
            .map(image -> image.id)
            .reduce(BigInteger.ONE, (v1, v2) -> v1.multiply(v2));
    }
    
    public static long solvePart2(List<String> input) {
        final List<Image> images = parseImages(input);        
        connectImages(images);         
        final Image image = combine(images);
        final Image monster = createMonster();
        findMonsters(image, monster);
        return computeRoughness(image);
    }

    private static long computeRoughness(Image image) {
        return Arrays.stream(image.tiles)
            .flatMap(row -> IntStream.range(0, row.length).mapToObj(i -> row[i]))
            .filter(field -> field.equals('#'))
            .count();
    }

    private static void findMonsters(Image image, Image monster) {
        for (int i = 0; i < 8; i++) {
            monster.flip();
            if (i % 2 == 1) {
                monster.rotate();
            }

            markMonsters(image, monster);
        }
    }

    private static void markMonsters(Image image, Image monster) {
        for (int row = 0; row < image.tiles.length - monster.tiles.length; row++) {
            for (int col = 0; col < image.tiles[0].length - monster.tiles[0].length; col++) {
                if (isMonster(image, monster, row, col)) {
                    highlightMonster(image, monster, row, col);
                }
            }
        }
    }

    private static void highlightMonster(Image image, Image monster, int row, int col) {
        for (int r = 0; r < monster.tiles.length; r++) {
            for (int c = 0; c < monster.tiles[0].length; c++) {
                if (monster.tiles[r][c] == '#') {
                    image.tiles[row + r][col + c] = 'O';
                }
            }
        }
    }

    private static boolean isMonster(Image image, Image monster, int row, int col) {
        for (int r = 0; r < monster.tiles.length; r++) {
            for (int c = 0; c < monster.tiles[0].length; c++) {
                if (Character.valueOf(monster.tiles[r][c]).equals('#')) {
                    if (image.tiles[row + r][col + c] == '.') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static Image combine(List<Image> images) {
        final int dim = DIMENSION - 2;
        final int width = dim * (images.stream().mapToInt(Image::getColumn).max().getAsInt() + 1);
        final int height = dim * (images.stream().mapToInt(Image::getRow).max().getAsInt() + 1);
        
        Image combinedImage = new Image();
        combinedImage.tiles = new char[height][width];
        for (Image image : images) {
            int row = image.getRow();
            int col = image.getColumn();
            for (int r = 0; r < dim; r++) {
                for (int c = 0; c < dim; c++) {
                    combinedImage.tiles[dim * row + r][dim * col + c] = image.tiles[r + 1][c + 1];
                }
            }
        }

        return combinedImage;
    }

    public static void connectImages(List<Image> images) {
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

    public static List<Image> parseImages(List<String> input) {
        final List<Image> images = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 12) {
            images.add(parseImage(input.subList(i, i + 11)));
        }
        return images;
    }

    public static Image parseImage(List<String> input) {
        final Image image = new Image();
        image.id = new BigInteger(input.get(0).replaceAll("[Tile :]", ""));
        image.tiles = IntStream.range(1, 11)
            .mapToObj(r -> input.get(r).toCharArray())
            .toArray(char[][]::new);
        return image;
    }

    private static Image createMonster() {
        final Image monster = new Image();
        monster.tiles = Stream.of(
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "
            )
            .map(String::toCharArray)
            .toArray(char[][]::new);
        return monster;
    }

    public static class Image {
        public char[][] tiles;
        public BigInteger id;
        public Image right;
        public Image left;
        public Image top;
        public Image bottom;
        
        public boolean isOriented() {
            return this.neighborCount() != 0;
        }

        public int neighborCount() {
            int count = this.right == null ? 0 : 1;
            count += this.bottom == null ? 0 : 1;
            count += this.left == null ? 0 : 1;
            return count + (this.top == null ? 0 : 1);
        }

        public void rotate() {
            final int rowCount = this.tiles[0].length;
            final int colCount = this.tiles.length;
            final char[][] newTiles = new char[rowCount][colCount];
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    newTiles[row][col] = this.tiles[col][rowCount - row - 1];
                }
            }
            this.tiles = newTiles;
        }

        public void flip() {
            final char[][] newTiles = new char[this.tiles.length][this.tiles[0].length];
            for (int row = 0; row < this.tiles.length; row++) {
                for (int col = 0; col < this.tiles[0].length; col++) {
                    newTiles[row][col] = this.tiles[row][this.tiles[0].length - 1 - col];
                }
            }
            this.tiles = newTiles;
        }

        public char getTop(int index) {
            return this.tiles[0][index];
        }

        public char getBottom(int index) {
            return this.tiles[this.tiles.length - 1][index];
        }

        public char getLeft(int index) {
            return this.tiles[index][0];
        }

        public char getRight(int index) {
            return this.tiles[index][this.tiles.length - 1];
        }

        public int getRow() {
            int row = 0;
            Image img = this;
            while ((img = img.top) != null) {
                row++;
            }
            return row;
        }

        public int getColumn() {
            int col = 0;
            Image img = this;
            while ((img = img.left) != null) {
                col++;
            }
            return col;
        }
    }

}
