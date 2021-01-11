package maze;

public class PrintMaze implements IPrint {
    private static final String WALL = "\u2588\u2588";
    private static final String PASSAGE = "  ";

    @Override
    public void print(Graph mst, int rows, int cols) {
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
}
