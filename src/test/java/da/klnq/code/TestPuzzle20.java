package da.klnq.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import da.klnq.advent.Puzzle20;
import da.klnq.advent.Puzzle20.Image;
import da.klnq.util.IOUtils;

public class TestPuzzle20 {
    private static final String RESOURCE = "/20-test-input.txt";
    private static final String RESOURCE_COMBINATION = "/20-test-input-comb.txt";
    private static final BigInteger TEST_RESULT_PART_1 = new BigInteger("20899048083289");
    private static final int TEST_RESULT_PART_2 = 273;
 
    @Test
    public void testPart1() {
        final BigInteger result = Puzzle20.solvePart1(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_1, result);
    }
 
    @Test
    public void testPart2() {
        final long result = Puzzle20.solvePart2(IOUtils.readResource(RESOURCE));
        assertEquals(TEST_RESULT_PART_2, result);
    }

    @Test
    public void testImageParsing() {
        final List<String> input = IOUtils.readResource(RESOURCE);
        
        for (int i = 0; i < input.size(); i += 12) {
            final Image image = Puzzle20.parseImage(input.subList(i, i + 11));
            assertEquals(input.get(i), String.format("Tile %s:", image.id));
            for (int r = 0; r < 10; r++) {
                for (int c = 0; c < 10; c++) {
                    assertEquals(image.tiles[r][c], input.get(i + r + 1).charAt(c));
                }    
            }
        }
    }
    
    @Test
    public void testConnectImages() {
        final List<Image> images = Puzzle20.parseImages(IOUtils.readResource(RESOURCE));
        Puzzle20.connectImages(images);
        
        this.hasNeighbors(images, "1951", null, "2311", "2729", null);
        this.hasNeighbors(images, "2311", null, "3079", "1427", "1951");
        this.hasNeighbors(images, "3079", null, null, "2473", "2311");
        this.hasNeighbors(images, "2729", "1951", "1427", "2971", null);
        this.hasNeighbors(images, "1427", "2311", "2473", "1489", "2729");
        this.hasNeighbors(images, "2473", "3079", null, "1171", "1427");
        this.hasNeighbors(images, "2971", "2729", "1489", null, null);
        this.hasNeighbors(images, "1489", "1427", "1171", null, "2971");
        this.hasNeighbors(images, "1171", "2473", null, null, "1489");
        
        images.forEach(this::checkNeightborAlignment);
    }

    private void checkNeightborAlignment(Image image) {
        if (image.top != null) {
            assertTrue(areAligned(image::getTop, image.top::getBottom));
        }
        
        if (image.right != null) {
            assertTrue(areAligned(image::getRight, image.right::getLeft));
        }
        
        if (image.bottom != null) {
            assertTrue(areAligned(image::getBottom, image.bottom::getTop));
        }
        
        if (image.left != null) {
            assertTrue(areAligned(image::getLeft, image.left::getRight));
        }
    }

    private boolean areAligned(
        Function<Integer, Character> accessor1,
        Function<Integer, Character> accessor2
    ) {
        return IntStream.range(0, 10)
            .allMatch(i -> accessor1.apply(i) == accessor2.apply(i));
    }   

    private void hasNeighbors(
        List<Image> images, 
        String id, 
        String topNeigh, 
        String rightNeigh, 
        String bottomNeigh,
        String leftNeigh
    ) {
        boolean correct = false;
        final Image image = images.stream().filter(img -> img.id.toString().equals(id)).findFirst().get();
        if (hasIds(image.top, topNeigh) && hasIds(image.right, rightNeigh) && hasIds(image.bottom, bottomNeigh) && hasIds(image.left, leftNeigh)) {
            correct = true;
        }
        else if (hasIds(image.right, topNeigh) && hasIds(image.bottom, rightNeigh) && hasIds(image.left, bottomNeigh) && hasIds(image.top, leftNeigh)) {
            correct = true;
        }
        else if (hasIds(image.bottom, topNeigh) && hasIds(image.left, rightNeigh) && hasIds(image.top, bottomNeigh) && hasIds(image.right, leftNeigh)) {
            correct = true;
        }
        else if (hasIds(image.left, topNeigh) && hasIds(image.top, rightNeigh) && hasIds(image.right, bottomNeigh) && hasIds(image.bottom, leftNeigh)) {
            correct = true;
        }
        else if (hasIds(image.top, topNeigh) && hasIds(image.left, rightNeigh) && hasIds(image.bottom, bottomNeigh) && hasIds(image.right, leftNeigh)) {
            correct = true;
        }
        else if (hasIds(image.right, topNeigh) && hasIds(image.top, rightNeigh) && hasIds(image.left, bottomNeigh) && hasIds(image.bottom, leftNeigh)) {
            correct = true;
        }
        else if (hasIds(image.bottom, topNeigh) && hasIds(image.right, rightNeigh) && hasIds(image.top, bottomNeigh) && hasIds(image.left, leftNeigh)) {
            correct = true;
        }
        else if (hasIds(image.left, topNeigh) && hasIds(image.bottom, rightNeigh) && hasIds(image.right, bottomNeigh) && hasIds(image.top, leftNeigh)) {
            correct = true;
        }

        assertTrue(
            correct, 
            String.format(
                "%s: %s %s %s %s", 
                image.id, 
                image.top == null ? "null" : image.top.id.toString(),
                image.right == null ? "null" : image.right.id.toString(), 
                image.bottom == null ? "null" : image.bottom.id.toString(), 
                image.left == null ? "null" : image.left.id.toString()
            )
        );
    }

    private boolean hasIds(Image image, String id) {
        return image == null ? id == null : image.id.toString().equals(id);
    }

    @Test
    public void testCombination() {
        final List<Image> images = Puzzle20.parseImages(IOUtils.readResource(RESOURCE));
        Puzzle20.connectImages(images);
        final Image combinedImage = Puzzle20.combine(images);
        final char[][] result = IOUtils.readResource(RESOURCE_COMBINATION)
            .stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        boolean identical = false;
        for (int i = 0; i < 8; i++) {
            combinedImage.flip();
            if (i % 2 == 1) {
                combinedImage.rotate();
            }
            if (areTilesAreEqual(combinedImage.tiles, result)) {
                identical = true;
                break;
            }
        }

        assertTrue(identical);
    }

    @Test
    public void testFlippingAndRotating() {
        final Image image = new Image();
        
        image.tiles = new char[][]{{'1','2','3'}, {'4','5','6'}, {'7','8','9'}};
        image.flip();
        final Image flippedImage = new Image();
        flippedImage.tiles = new char[][]{{'3','2','1'}, {'6','5','4'}, {'9','8','7'}};
        assertTilesAreEqual(flippedImage, image);

        image.tiles = new char[][]{{'1','2','3','4'}, {'5','6','7','8'}, {'9','a','b','c'}};
        image.flip();
        flippedImage.tiles = new char[][]{{'4','3','2','1'}, {'8','7','6','5'}, {'c','b','a','9'}};
        assertTilesAreEqual(flippedImage, image);

        image.tiles = new char[][]{{'1','2','3'}, {'4','5','6'}, {'7','8','9'}};
        image.rotate();
        final Image rotatedImage = new Image();
        rotatedImage.tiles = new char[][]{{'3','6','9'}, {'2','5','8'}, {'1','4','7'}};
        assertTilesAreEqual(rotatedImage, image);

        image.tiles = new char[][]{{'1','2','3','4'}, {'5','6','7','8'}, {'9','a','b','c'}};
        image.rotate();
        rotatedImage.tiles = new char[][]{{'4','8','c'}, {'3','7','b'}, {'2','6','a'}, {'1','5','9'}};
        assertTilesAreEqual(rotatedImage, image);
    }

    private void assertTilesAreEqual(Image image1, Image image2) {
        assertTrue(areTilesAreEqual(image1.tiles, image2.tiles));
    }

    private boolean areTilesAreEqual(char[][] tiles1, char[][] tiles2) {
        for (int r = 0; r < tiles1.length; r++) {
            for (int c = 0; c < tiles1[0].length; c++) {
                if (tiles1[r][c] != tiles2[r][c]) {
                    return false;
                }                
            }
        }
        return true;
    }
}
