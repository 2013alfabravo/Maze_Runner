package maze;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int height = Integer.parseInt(scanner.nextLine());
        int width = Integer.parseInt(scanner.nextLine());

        Maze maze = new Maze(height, width);
        maze.digPassages();

        new ConsolePrinter().print(maze);
    }
}
