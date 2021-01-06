package maze;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        int[][] maze = new int[rows][cols];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (i == 0 || i == maze.length - 1 || j == 0 || j == maze[i].length - 1) {
                    maze[i][j] = 1;
                } else {
                    maze[i][j] = 0;
                }
            }
        }

//        for (int[] row : maze) {
//            System.out.println(Arrays.toString(row));
//        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] == 1 ? "\u2588\u2588" : "  ");
            }
            System.out.print("\n");
        }
    }

    public void method() {

    }
}
