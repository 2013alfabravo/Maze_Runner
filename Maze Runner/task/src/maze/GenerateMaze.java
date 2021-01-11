package maze;

import java.util.Random;

public class GenerateMaze {
    public Graph generateMaze(int rows, int cols) {
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

}
