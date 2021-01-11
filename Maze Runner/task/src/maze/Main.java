package maze;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rnd = new Random();
        int[][] maze = new int[8][8];
        Graph graph = new Graph();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                graph.addNode(i + ":" + j);
            }
        }

        // coordinates are bound to nodes as labels in "row:col" format
        // and links between nodes are made using a grid pattern, see GraphTest for reference

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (i != maze.length - 1) {
                    graph.addLink(i + ":" + j, (i + 1) + ":" + j, rnd.nextInt(100));
                }

                if (i != 0) {
                    graph.addLink(i + ":" + j, (i - 1) + ":" + j, rnd.nextInt(100));
                }

                if (j != maze[i].length - 1) {
                    graph.addLink(i + ":" + j, i + ":" + (j + 1), rnd.nextInt(100));
                }

                if (j != 0) {
                    graph.addLink(i + ":" + j, i + ":" + (j - 1), rnd.nextInt(100));
                }
            }
        }

        Graph mst = graph.buildMST();

        // we render our maze as follows: nodes and links between nodes are free cells and other cells are walls
        // plus we surround our maze with walls on all four sides
        // we do not have an entry and an exit yet and what is more important we yet do not adjust the size of our
        // maze according to user's input as required by the project's stage.

        // to avoid this mess we can first make a matrix of zeroes and ones and we used on the previous stage
        // and then print it on the screen
        System.out.println("\u2588\u2588".repeat(maze.length * 2 + 2));     // top wall
        for (int i = 0; i < maze.length * 2; i += 2) {
            System.out.print("\u2588\u2588");                               // left wall
            for (int j = 0; j < maze[i / 2].length * 2; j += 2) {
                System.out.print("  ");
                if (mst.isConnected((i / 2) + ":" + (j / 2), (i / 2) + ":" + (j / 2 + 1))) {
                    System.out.print("  ");
                } else {
                    System.out.print("\u2588\u2588");
                }
            }
            System.out.println("\u2588\u2588");                             // right wall

            System.out.print("\u2588\u2588");
            for (int j = 0; j < maze[i / 2].length * 2; j += 2) {
                if (mst.isConnected((i / 2) + ":" + (j / 2), (i / 2 + 1) + ":" + (j / 2))) {
                    System.out.print("  ");
                } else {
                    System.out.print("\u2588\u2588");
                }
                System.out.print("\u2588\u2588");
            }
            System.out.println("\u2588\u2588");
        }
        System.out.println("\u2588\u2588".repeat(maze.length * 2 + 2));     // bottom wall

    }
//        Scanner scanner = new Scanner(System.in);
//        int rows = scanner.nextInt();
//        int cols = scanner.nextInt();
//
//        int[][] maze = new int[rows][cols];
//
//        for (int i = 0; i < maze.length; i++) {
//            for (int j = 0; j < maze[i].length; j++) {
//                if (i == 0 || i == maze.length - 1 || j == 0 || j == maze[i].length - 1) {
//                    maze[i][j] = 1;
//                } else {
//                    maze[i][j] = 0;
//                }
//            }
//        }
//
//        for (int[] row : maze) {
//            System.out.println(Arrays.toString(row));
//        }
//
//        for (int i = 0; i < maze.length; i++) {
//            for (int j = 0; j < maze[i].length; j++) {
//                System.out.print(maze[i][j] == 1 ? "\u2588\u2588" : "  ");
//            }
//            System.out.print("\n");
//        }
//    }

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
                System.out.print(maze[i][j] == 1 ? "\u2588\u2588" : "  ");
            }
            System.out.print("\n");
        }
    }
}
