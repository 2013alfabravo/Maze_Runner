package maze;

import java.util.Arrays;
import java.util.Random;

public class Maze {
    private static final String WALL = "\u2588\u2588";
    private static final String PASSAGE = "  ";
    private static final Random rnd = new Random();

    private final int[][] matrix;
    private final int heightLimit;
    private final int widthLimit;

    Maze(int height, int width) {
        matrix = new int[height][width];
        fillWithOnes();
        heightLimit = getHeightLimit(height);
        widthLimit = getWidthLimit(width);
        digPassages();
    }

    Maze(String rawMazeFormat) {
        matrix = readFromString(rawMazeFormat);
        heightLimit = getHeightLimit(matrix.length);
        widthLimit = getWidthLimit(matrix[0].length);
    }

    private int getHeightLimit(int height) {
        return isHeightEven() ? height - 1 : height;
    }

    private int getWidthLimit(int width) {
        return isWidthEven() ? width - 1 : width;
    }

    private int[][] readFromString(String rawMazeFormat) {
        String[] tokens = rawMazeFormat.split(" ");
        try {
            int[][] matrix = new int[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])];
            int index = 2;
            for (int row = 0; row < matrix.length; row++) {
                for (int column = 0; column < matrix[row].length; column++) {
                    int next = Integer.parseInt(tokens[index++]);
                    if (next != 0 && next != 1) {
                        throw new RuntimeException();
                    }
                    matrix[row][column] = next;
                }
            }

            return matrix;
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    private void fillWithOnes() {
        Arrays.stream(matrix).forEach(row -> Arrays.fill(row, 1));
    }

    private boolean isHeightEven() {
        return matrix.length % 2 == 0;
    }

    private boolean isWidthEven() {
        return matrix[0].length % 2 == 0;
    }

    private void digPassages() {
        Graph mst = buildMST();
        for (int i = 1; i < heightLimit; i += 2) {
            for (int j = 1; j < widthLimit; j += 2) {
                matrix[i][j] = 0;
                if (mst.isConnected(i + ":" + j, i + ":" + (j + 2))) {
                    matrix[i][j + 1] = 0;
                }
                if (mst.isConnected(i + ":" + j, i + 2 + ":" + j)) {
                    matrix[i + 1][j] = 0;
                }
            }
        }

        addExits();
    }

    private void addExits() {
        matrix[0][1] = 0;
        int bottomRow = isHeightEven() ? matrix.length - 3 : matrix.length - 2;
        int rightCol = isWidthEven() ? matrix[0].length - 2 : matrix[0].length - 1;

        matrix[bottomRow][rightCol] = 0;
        if (isWidthEven())
            matrix[bottomRow][rightCol + 1] = 0;
    }

    private Graph buildMST() {
        Graph graph = new Graph();
        addNodes(graph);
        linkAllNodes(graph);
        return graph.buildMST();
    }

    private void addNodes(Graph graph) {
        for (int i = 1; i < heightLimit; i += 2) {
            for (int j = 1; j < widthLimit; j += 2) {
                graph.addNode(i + ":" + j);
            }
        }
    }

    private void linkAllNodes(Graph graph) {
        for (int i = 1; i < heightLimit; i += 2) {
            for (int j = 1; j < widthLimit; j += 2) {
                if (i + 2 <= heightLimit - 1) {
                    graph.addLink(i + ":" + j, (i + 2) + ":" + j, rnd.nextInt(100));
                }

                if (j + 2 <= widthLimit - 1) {
                    graph.addLink(i + ":" + j, i + ":" + (j + 2), rnd.nextInt(100));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int cell : row) {
                String block = cell == 1 ? WALL : PASSAGE;
                sb.append(block);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String toRawFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(matrix.length).append(' ');
        sb.append(matrix[0].length).append(' ');
        for (int[] row : matrix) {
            for (int cell : row) {
                sb.append(cell).append(' ');
            }
        }

        return sb.toString();
    }
}
