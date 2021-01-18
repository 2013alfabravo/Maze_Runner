package maze;

import java.util.Arrays;
import java.util.Random;

public class Maze {
    private static final char WALL_IMG = '\u2588';
    private static final char PASSAGE_IMG = ' ';
    private static final char ESCAPE_IMG = '/';
    private static final int PASSAGE_ID = 0;
    private static final int WALL_ID = 1;
    private static final int ESCAPE_ID = 2;
    private static final Random rnd = new Random();

    private final int[][] matrix;

    Maze(int height, int width) {
        matrix = new int[height][width];
        fillWithWalls();
        digPassages();
    }

    private Maze(int[][] matrix) {
        this.matrix = matrix;
    }

    public static Maze fromArray(int[][] array) {
        return new Maze(copyOf(array));
    }

    public static Maze fromString(String mazeString) {
        int[][] matrix = stringToMatrix(mazeString);
        if (matrix == null) {
            return null;
        }

        return new Maze(matrix);
    }

    private static int[][] stringToMatrix(String mazeString) {
        if (!validFormat(mazeString)) {
            return null;
        }

        int size = mazeString.split("\n").length;
        int[][] matrix = new int[size][size];
        String stripped = mazeString.replaceAll("\n", "");

        int index = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                matrix[row][column] = stripped.charAt(index) == WALL_IMG ? WALL_ID : PASSAGE_ID;
                index += 2;
            }
        }

        return matrix;
    }

    private static boolean validFormat(String string) {
        return string.matches("[\\r\\n" + WALL_IMG + PASSAGE_IMG + ESCAPE_IMG + "]+");
    }

    private void fillWithWalls() {
        Arrays.stream(matrix).forEach(row -> Arrays.fill(row, WALL_ID));
    }

    private void digPassages() {
        Graph mst = buildMST();
//        long start = System.nanoTime();
        for (int row = 1; row < height() - 1; row += 2) {
            for (int col = 1; col < width() - 1; col += 2) {
                matrix[row][col] = 0;
                if (col + 2 < width() - 1 && mst.isConnected(row + ":" + col, row + ":" + (col + 2))) {
                    matrix[row][col + 1] = 0;
                }
                if (row + 2 < height() - 1 && mst.isConnected(row + ":" + col, row + 2 + ":" + col)) {
                    matrix[row + 1][col] = 0;
                }
            }
        }

        addExits();
//        System.out.println("digPassages time: " + ((System.nanoTime() - start) / 1000_000) + " ms");
    }

    // todo make exits random on opposite sides of the maze (at a distance of perimeter / 2 from each other)
    private void addExits() {
        matrix[0][1] = 0;
        int bottomRow = height() % 2 == 0 ? height() - 3 : height() - 2;
        int rightCol = width() % 2 == 0 ? width() - 2 : width() - 1;

        matrix[bottomRow][rightCol] = 0;
        if (width() % 2 == 0)
            matrix[bottomRow][rightCol + 1] = 0;
    }

    private Graph buildMST() {
        Graph graph = new Graph();
        addNodes(graph);
        linkAllNodes(graph);
//        long start = System.nanoTime();
//        System.out.println("MST time: " + ((System.nanoTime() - start) / 1000_000) + " ms");
        return graph.buildMST();
    }

    // TODO could add nodes and link them in the same loop
    private void addNodes(Graph graph) {
//        long start = System.nanoTime();
        for (int i = 1; i < height() - 1; i += 2) {
            for (int j = 1; j < width() - 1; j += 2) {
                graph.addNode(i + ":" + j);
            }
        }
//        System.out.println("addNodes time: " + ((System.nanoTime() - start) / 1000_000) + " ms");
    }

    private void linkAllNodes(Graph graph) {
//        long start = System.nanoTime();
        for (int row = 1; row < height() - 1; row += 2) {
            for (int column = 1; column < width() - 1; column += 2) {
                if (row + 2 < height() - 1) {
                    graph.addLink(row + ":" + column, (row + 2) + ":" + column, rnd.nextInt(100));
                }

                if (column + 2 < width() - 1) {
                    graph.addLink(row + ":" + column, row + ":" + (column + 2), rnd.nextInt(100));
                }
            }
        }
//        System.out.println("linkAllNodes time: " + ((System.nanoTime() - start) / 1000_000) + " ms");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int cell : row) {
                char block;
                if (cell == WALL_ID)
                    block = WALL_IMG;
                else if (cell == PASSAGE_ID)
                    block = PASSAGE_IMG;
                else
                    block = ESCAPE_IMG;

                sb.append(block).append(block);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int[][] toArray() {
        return copyOf(matrix);
    }

    private static int[][] copyOf(int[][] other) {
        int[][] array = new int[other.length][other[0].length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(other[i], 0, array[i], 0, array[i].length);
        }

        return array;
    }

    public int height() {
        return matrix.length;
    }

    public int width() {
        return matrix[0].length;
    }

    public boolean hasPassageAt(int row, int column) {
        return matrix[row][column] == PASSAGE_ID || matrix[row][column] == ESCAPE_ID;
    }
}
