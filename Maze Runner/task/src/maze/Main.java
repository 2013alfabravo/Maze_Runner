package maze;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String WALL = "\u2588\u2588";
    private static final String PASSAGE = "  ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int height = scanner.nextInt();
        int width = scanner.nextInt();

        // FIXME for evens numbers
        int rows = height / 2;
        int cols = width / 2;

        Graph mst = generateMaze(rows, cols);
        printMaze(mst, rows, cols);

    }

    // coordinates are bound to nodes as labels in "row:col" format
    // and links between nodes are made using a grid pattern, see GraphTest for reference
    public static Graph generateMaze(int rows, int cols) {
        Random rnd = new Random();
        Graph graph = new Graph();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                graph.addNode(i + ":" + j);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i != rows - 1) {
                    graph.addLink(i + ":" + j, (i + 1) + ":" + j, rnd.nextInt(100));
                }

                if (j != cols - 1) {
                    graph.addLink(i + ":" + j, i + ":" + (j + 1), rnd.nextInt(100));
                }
            }
        }

        return graph.buildMST();
    }

    // we render our maze as follows: nodes and links between nodes are free cells and other cells are walls
    // plus we surround our maze with walls on all four sides
    // we do not have an entry and an exit yet and what is more important we yet do not adjust the size of our
    // maze according to user's input as required by the project's stage.

    // to avoid this mess we can first make a matrix of zeroes and ones and we used on the previous stage
    // and then print it on the screen
    public static void printMaze(Graph mst, int rows, int cols) {
        System.out.print(WALL + PASSAGE);
        System.out.println(WALL.repeat(cols * 2 - 1));     // top wall
        for (int i = 0; i < rows * 2; i += 2) {
            System.out.print(WALL);                               // left wall
            for (int j = 0; j < cols * 2; j += 2) {
                System.out.print(PASSAGE);
                if (mst.isConnected((i / 2) + ":" + (j / 2), (i / 2) + ":" + (j / 2 + 1))) {
                    System.out.print(PASSAGE);
                } else {
                    if (i == rows * 2 - 2 && j == cols * 2 - 2) {
                        System.out.print(PASSAGE);
                    } else {
                        System.out.print(WALL);
                    }
                }
            }
            System.out.println(/*WALL*/);                             // right wall

            System.out.print(WALL);
            for (int j = 0; j < cols * 2; j += 2) {
                if (mst.isConnected((i / 2) + ":" + (j / 2), (i / 2 + 1) + ":" + (j / 2))) {
                    System.out.print(PASSAGE);
                } else {
                    System.out.print(WALL);
                }
                System.out.print(WALL);
            }
            System.out.println(/*WALL*/);
        }
    }


    public void method() {

        int[][] maze = {    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                            {0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
                            {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
                            {1, 0, 0, 0, 1, 1, 1, 0, 0, 1},
                            {1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                            {1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                            {1, 0, 1, 0, 1, 1, 1, 0, 0, 1},
                            {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
                            {1, 0, 1, 1, 0, 1, 1, 0, 0, 0},
                            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}  };

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] == 1 ? WALL : PASSAGE);
            }
            System.out.print("\n");
        }
    }
}
