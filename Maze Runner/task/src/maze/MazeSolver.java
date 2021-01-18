package maze;

import java.util.List;

public class MazeSolver {
    private final Maze maze;
    private final Graph graph = new Graph();
    private String solution;
    private int[][] solvedMazeArray;

    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }


    public String getMazeSolution() {
        createGraph();
        int exitRow = maze.height() % 2 == 0 ? maze.height() - 3 : maze.height() - 2;
        int exitCol = maze.width() % 2 == 0 ? maze.width() - 3 : maze.width() - 2;
        Graph escapePath = graph.findPath("1:1", exitRow + ":" + exitCol);
        drawEscapePath(escapePath);
        return solution;
    }

    private void drawEscapePath(Graph escapePath) {
        solvedMazeArray = maze.toArray();
        List<String> labels = escapePath.labels();

        for (int i = 0; i < labels.size(); i++) {
            Position currentPos = getPosition(labels.get(i));
            markNode(currentPos);
            if (i < labels.size() - 1) {
                markPassage(currentPos, getPosition(labels.get(i + 1)));
            }
        }

        markExits();
        solution = Maze.fromArray(solvedMazeArray).toString();
    }

    // todo refactor to get coordinates of the exits
    private void markExits() {
        solvedMazeArray[0][1] = 2;
        int bottomRow = maze.height() % 2 == 0 ? maze.height() - 3 : maze.height() - 2;
        int rightCol = maze.width() % 2 == 0 ? maze.width() - 2 : maze.width() - 1;

        solvedMazeArray[bottomRow][rightCol] = 2;
        if (maze.width() % 2 == 0)
            solvedMazeArray[bottomRow][rightCol + 1] = 2;
    }

    private void markPassage(Position from, Position to) {
        if (from.row < to.row) {
            solvedMazeArray[from.row + 1][from.col] = 2;
        }

        if (from.row > to.row) {
            solvedMazeArray[from.row - 1][from.col] = 2;
        }

        if (from.col < to.col) {
            solvedMazeArray[from.row][from.col + 1] = 2;
        }

        if (from.col > to.col) {
            solvedMazeArray[from.row][from.col - 1] = 2;
        }
    }

    private void markNode(Position position) {
        solvedMazeArray[position.row][position.col] = 2;
    }

    private Position getPosition(String nodeLabel) {
        String[] tokens = nodeLabel.split(":");
        int row = Integer.parseInt(tokens[0]);
        int column = Integer.parseInt(tokens[1]);

        return new Position(row, column);
    }

    private void createGraph() {
        generateNodes();
        generateLinks();
    }

    private void generateNodes() {
        for (int row = 1; row < maze.height() - 1; row += 2) {
            for (int column = 1; column < maze.width() - 1; column += 2) {
                graph.addNode(row + ":" + column);
            }
        }
    }

    private void generateLinks() {
        for (int row = 1; row < maze.height() - 1; row += 2) {
            for (int column = 1; column < maze.width() - 1; column += 2) {
                if (maze.hasPassageAt(row + 1, column)) {
                    graph.addLink(row + ":" + column, (row + 2) + ":" + column, 1);
                }

                if (maze.hasPassageAt(row, column + 1) && column + 2 < maze.width() - 1) {
                    graph.addLink(row + ":" + column, row + ":" + (column + 2), 1);
                }
            }
        }
    }
}
