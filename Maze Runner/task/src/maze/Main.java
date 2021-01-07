package maze;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addNode("A");     // "A" --6-- "B" --4-- "C"
        graph.addNode("B");     //  |         |         |
        graph.addNode("C");     //  3         2         12
        graph.addNode("D");     //  |         |         |
        graph.addNode("E");     // "D" --1-- "E" --7-- "F"
        graph.addNode("F");     //  |         |         |
        graph.addNode("G");     //  8         9         10
        graph.addNode("H");     //  |         |         |
        graph.addNode("I");     // "G" --11- "H" --5-- "I"

        graph.addLink("A", "B", 6);
        graph.addLink("A", "D", 3);
        graph.addLink("B", "C", 4);
        graph.addLink("B", "E", 2);
        graph.addLink("C", "F", 12);
        graph.addLink("D", "E", 1);
        graph.addLink("D", "G", 8);
        graph.addLink("E", "F", 7);
        graph.addLink("E", "H", 9);
        graph.addLink("F", "I", 10);
        graph.addLink("G", "H", 11);
        graph.addLink("H", "I", 5);

        Graph mst = graph.buildMST();

        System.out.println(mst);

        System.out.println("MST weight: " + mst.getMSTTotalWeight()); // 39
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

    }
}
