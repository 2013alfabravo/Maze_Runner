package maze;

public class Main {
    public static void main(String[] args) {
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

    public void method() {

    }
}
